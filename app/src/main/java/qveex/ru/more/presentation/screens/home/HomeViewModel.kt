package qveex.ru.more.presentation.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import qveex.ru.more.R
import qveex.ru.more.data.models.Atm
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.InfrastructureType
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.Status
import qveex.ru.more.domain.interactor.HomeInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import java.util.Calendar
import javax.inject.Inject

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

    private val curDay = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> Days.MONDAY
        Calendar.TUESDAY -> Days.TUESDAY
        Calendar.WEDNESDAY -> Days.WEDNESDAY
        Calendar.THURSDAY -> Days.THURSDAY
        Calendar.FRIDAY -> Days.FRIDAY
        Calendar.SATURDAY -> Days.SATURDAY
        Calendar.SUNDAY -> Days.SUNDAY
        else -> Days.MONDAY
    }
    private lateinit var mapView: MapView
    private lateinit var mapObjectCollection: MapObjectCollection

    init {
        viewModelScope.launch {
            val objects = interactor.getDepartmentsAndAtmsAround(
                leftTopCoordinate = Location(.0, .0),
                rightBottomCoordinate = Location(.0, .0)
            )

            setState {
                copy(
                    points = with(objects) {
                        atms.map { Point(it.coordinates.latitude, it.coordinates.longitude) } +
                                departments.map {
                                    Point(
                                        it.coordinates.latitude,
                                        it.coordinates.longitude
                                    )
                                }
                    },
                    atmsAndDepartments = with(objects) {
                        atms.map {
                            it.toUi()
                        } + departments.map {
                            it.toUi()
                        }
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
            is HomeContract.Event.SetMapView -> {
                mapView = event.mapView
                mapObjectCollection = mapView.mapWindow.map.mapObjects.addCollection()

                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(mapView.context)
                fusedLocationClient.lastLocation.addOnCompleteListener { location ->
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

            is HomeContract.Event.AddPlace -> {
                addPlace(
                    event.latitude.toDouble(),
                    event.longitude.toDouble()
                )
            }

            is HomeContract.Event.FindCurrentLocation -> {
                val locationManager = MapKitFactory.getInstance().createLocationManager()
                locationManager.requestSingleUpdate(object :
                    com.yandex.mapkit.location.LocationListener {
                    override fun onLocationStatusUpdated(p0: LocationStatus) {
                        Log.d("LocationStatus", "No status")
                    }

                    override fun onLocationUpdated(p0: com.yandex.mapkit.location.Location) {
                        mapView.mapWindow.map.move(
                            CameraPosition(
                                Point(p0.position.latitude, p0.position.longitude),
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
        ) { setState { copy(isAnimation = false) } }

    }

    private fun onStart() {
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
        viewState.value.points.forEach {
            addPlace(it.latitude, it.longitude)
        }
    }

    private fun onStop() {
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
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

    private fun Department.toUi() = AtmDepartment(
        id = departmentId,
        address = address,
        metro = metroStation,
        distance = 123, // todo посчитать расстояние от местонахождения пользователя
        location = coordinates,
        type = InfrastructureType.DEPARTMENT,
        status = status,
        openAt = individual.find { it.day == curDay }?.openHours?.from,
        closeAt = individual.find { it.day == curDay }?.openHours?.to,
    )

    private fun Atm.toUi() = AtmDepartment(
        id = atmId,
        address = address,
        metro = metroStation,
        distance = 123, // todo посчитать расстояние от местонахождения пользователя
        location = coordinates,
        type = InfrastructureType.ATM,
        status = Status.OPEN
    )

    private fun addPlace(latitude: Double, longitude: Double) {
        val point = Point(latitude, longitude)
        val mapObject = mapObjectCollection.addPlacemark().apply {
            geometry = point
            setIcon(ImageProvider.fromResource(mapView.context, R.drawable.ic_atm))
        }

        mapObject.zIndex = 100.0f
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
    val openAt: String? = null,
    val closeAt: String? = null
)