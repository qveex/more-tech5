package qveex.ru.more.presentation.screens.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout
import qveex.ru.more.InfoParams
import qveex.ru.more.R
import qveex.ru.more.data.models.Atm
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.InfrastructureType
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.Service
import qveex.ru.more.data.models.Status
import qveex.ru.more.domain.interactor.HomeInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import qveex.ru.more.utils.curDay
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactor: HomeInteractor,
    private val resProvider: ResourceProvider
) : BaseViewModel<
        HomeContract.Event,
        HomeContract.State,
        HomeContract.Effect,
        >() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val curDay = Calendar.getInstance().curDay
    private lateinit var mapView: MapView
    private lateinit var mapObjectCollection: MapObjectCollection
    private var curLocation = Location(.0, .0)
    private var infoParams: InfoParams? = null
    private var leftTopBorder: Location? = null
    private var rightBottomBorder: Location? = null
    private val mutex = Mutex()

    init {
        viewModelScope.launch {
            delay(1000)
            Log.i(TAG, "infoParams = $infoParams")
            Log.i(TAG, "curLoc = $curLocation")
            val objects = interactor.getDepartmentsAndAtmsAround(
                curLocation = curLocation,
                leftTopCoordinate = leftTopBorder,
                rightBottomCoordinate = rightBottomBorder,
                infoParams = infoParams
            )

            setState {
                copy(
                    points = with(objects!!) {
                        atms.map { Point(it.coordinates.latitude, it.coordinates.longitude) } +
                                departments.map {
                                    Point(
                                        it.coordinates.latitude,
                                        it.coordinates.longitude
                                    )
                                }
                    },
                    atmsAndDepartments = with(objects) {
                        atms.map { it.toUi() } + departments.map { it.toUi(curDay) }
                    }.sortedBy { it.distance }
                )

            }
        }
    }

    override fun setInitialState() = HomeContract.State()

    @SuppressLint("MissingPermission")
    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.SelectDepartment -> selectDepartment(event.departmentId)
            is HomeContract.Event.SelectAtm -> selectAtm(event.atmId)
            is HomeContract.Event.ShowBottomSheet -> showBottomSheet(event.show)
            is HomeContract.Event.OnStart -> onStart()
            is HomeContract.Event.OnStop -> onStop()
            is HomeContract.Event.MinusZoom -> setZoom(-1f)
            is HomeContract.Event.PlusZoom -> setZoom(1f)
            is HomeContract.Event.SetInfoParam -> { infoParams = event.info }

            is HomeContract.Event.SetMapView -> {
                Log.i(TAG, "SetMapView")
                mapView = event.mapView
                mapObjectCollection = mapView.mapWindow.map.mapObjects.addCollection()
                initMarks()
                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(mapView.context)
                fusedLocationClient.lastLocation.addOnCompleteListener { location ->
                    curLocation = Location(
                        location.result.latitude,
                        location.result.longitude
                    )
                    mapView.mapWindow.map.move(
                        CameraPosition(
                            Point(location.result.latitude, location.result.longitude),
                            15.0f,
                            0.0f,
                            0.0f
                        )
                    )
                }
            }

            is HomeContract.Event.FindCurrentLocation -> {
                val locationManager = MapKitFactory.getInstance().createLocationManager()
                locationManager.requestSingleUpdate(object :
                    com.yandex.mapkit.location.LocationListener {
                    override fun onLocationStatusUpdated(p0: LocationStatus) {
                        Log.d(TAG, "No status")
                    }

                    override fun onLocationUpdated(loc: com.yandex.mapkit.location.Location) {
                        curLocation = Location(
                            loc.position.latitude,
                            loc.position.longitude
                        )
                        mapView.mapWindow.map.move(
                            CameraPosition(
                                Point(loc.position.latitude, loc.position.longitude),
                                1.0f,
                                0.0f,
                                0.0f
                            ),
                            Animation(Animation.Type.SMOOTH, 0.3f),
                        ) {}

                    }
                })

            }
        }
    }

    private fun setZoom(zoom: Float) = with(mapView.map) {
        if (viewState.value.isAnimation) return@with
        setState { copy(isAnimation = true) }
        move(
            CameraPosition(
                Point(
                    cameraPosition.target.latitude,
                    cameraPosition.target.longitude
                ),
                cameraPosition.zoom.plus(zoom),
                cameraPosition.azimuth,
                cameraPosition.tilt
            ),
            Animation(Animation.Type.SMOOTH, 0.3f)
        ) {
            setState {
                copy(isAnimation = false)
            }
        }

    }

    private fun onZoom() {
        Log.i(TAG, "onZoom")
        getBorders()
        initMarks()
    }

    private fun onStart() {
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
        mapView.mapWindow.map.addCameraListener(cameraListener)
    }

    private fun onStop() {
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    private fun initMarks() {
        if (mutex.isLocked) return
        viewModelScope.launch {
            withTimeout(5.seconds) {
                mutex.withLock {
                    Log.i(TAG, "infoParam = $infoParams")
                    interactor.getDepartmentsAndAtmsAround(
                        curLocation = curLocation,
                        leftTopCoordinate = leftTopBorder,
                        rightBottomCoordinate = rightBottomBorder,
                        infoParams = infoParams
                    )?.let { info ->
                        Log.i(TAG, "info = $info")
                        val atmsAndDepartments = info.atms.map { it.toUi() } + info.departments.map { it.toUi(curDay) }
                        atmsAndDepartments.forEach { addPlace(it) }
                        setState { copy(atmsAndDepartments = atmsAndDepartments) }
                    }
                    delay(1500)
                }
            }
        }
    }

    private fun showBottomSheet(show: Boolean) = setState {
        copy(showBottomSheet = show)
    }

    private fun selectDepartment(departmentId: Long) {
        setEffect {
            HomeContract.Effect.Success("$departmentId")
        }
    }

    private fun selectAtm(atmId: Long) {
        setEffect {
            HomeContract.Effect.Success("$atmId")
        }
    }

    private fun addPlace(atmDepartment: AtmDepartment) {
        Log.i(TAG, "addPlace atmDepartment = $atmDepartment")
        val point = Point(
            atmDepartment.location.latitude,
            atmDepartment.location.longitude
        )
        val isAtm = atmDepartment.type == InfrastructureType.ATM
        val title = if (isAtm) "Банкомат\nВТБ" else "Отделение\nВТБ"
        val icon = if (isAtm) R.drawable.atm_ic else R.drawable.ic_place
        mapObjectCollection.addPlacemark(
            point,
            ImageProvider.fromBitmap(
                (ResourcesCompat.getDrawable(
                    mapView.context.resources,
                    icon,
                    null
                ) as VectorDrawable).toBitmap()
            )
        ).apply {
            zIndex = 100.0f
            setText(
                title,
                TextStyle()
                    .setPlacement(TextStyle.Placement.BOTTOM)
                    .setSize(12.0f)
                    .setOutlineColor(Color.WHITE)
            )
            userData = MarkParams(atmDepartment.id, atmDepartment.type)
            addTapListener(markOnClick)
        }

    }

    private fun getBorders() {
        val height = mapView.mapWindow.height()
        val width = mapView.mapWindow.width()

        val leftTop = ScreenPoint(0f, 0f)
        val rightBottom = ScreenPoint(width.toFloat(), height.toFloat())

        val worldLeftTopBorder = mapView.mapWindow.screenToWorld(leftTop)
        val worldRightBottomBorder = mapView.mapWindow.screenToWorld(rightBottom)

        leftTopBorder = worldLeftTopBorder?.let {
            Location(worldLeftTopBorder.latitude, worldLeftTopBorder.longitude)
        }
        rightBottomBorder = worldRightBottomBorder?.let {
            Location(worldRightBottomBorder.latitude, worldRightBottomBorder.longitude)
        }
    }

    private data class MarkParams(val id: Long, val type: InfrastructureType)

    private val cameraListener = CameraListener { _, _, _, _ -> onZoom() }
    private val markOnClick =
        MapObjectTapListener { mapObject, _ ->
            val userData = mapObject.userData
            if (mapObject !is PlacemarkMapObject) return@MapObjectTapListener false
            if (userData !is MarkParams) return@MapObjectTapListener false
            setEffect { HomeContract.Effect.Navigation.ToInfoScreen(userData.type, userData.id) }
            true
        }
}

data class AtmDepartment(
    val id: Long,
    val address: String,
    val metro: String,
    val distance: Int,
    val location: Location,
    val type: InfrastructureType,
    val status: Status,
    val services: List<Service>,
    val openAt: String? = null,
    val closeAt: String? = null
)

fun Department.toUi(curDay: Days) = AtmDepartment(
    id = id,
    address = address,
    metro = metroStation,
    distance = (0..1000).random(), // todo посчитать расстояние от местонахождения пользователя
    location = coordinates,
    type = InfrastructureType.DEPARTMENT,
    status = status,
    openAt = individual.find { it.day == curDay }?.openHours?.from,
    closeAt = individual.find { it.day == curDay }?.openHours?.to,
    services = services
)

fun Atm.toUi() = AtmDepartment(
    id = id,
    address = address,
    metro = metroStation,
    distance = (0..1000).random(), // todo посчитать расстояние от местонахождения пользователя
    location = coordinates,
    type = InfrastructureType.ATM,
    status = Status.OPEN,
    services = services
)
