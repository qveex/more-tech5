package qveex.ru.more.presentation.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    init {
        viewModelScope.launch {
            val objects = interactor.getDepartmentsAndAtmsAround(
                leftTopCoordinate = Location(.0, .0),
                rightBottomCoordinate = Location(.0, .0)
            )
            setState {
                copy(
                    atmsAndDepartments = with(objects) {
                        atms.map { it.toUi() } +
                                departments.map { it.toUi() }
                    }.sortedBy { it.distance }
                )
            }
        }
    }

    override fun setInitialState() = HomeContract.State()

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
            }

            is HomeContract.Event.FindCurrentLocation -> {
                MapKitFactory.initialize(mapView.context)
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
                            Map.CameraCallback {
                                mapView.
                            }
                        )
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