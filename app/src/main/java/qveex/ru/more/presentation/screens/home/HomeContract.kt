package qveex.ru.more.presentation.screens.home

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import qveex.ru.more.InfoParams
import qveex.ru.more.data.models.InfrastructureType
import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState

class HomeContract {

    sealed class Event : ViewEvent {
        data class SelectDepartment(val departmentId: Long) : Event()
        data class SelectAtm(val atmId: Long) : Event()
        data class ShowBottomSheet(val show: Boolean) : Event()
        data class SetMapView(val mapView: MapView) : Event()
        data class SetInfoParam(val info: InfoParams) : Event()
        data object OnStart : Event()
        data object OnStop : Event()
        data object PlusZoom : Event()
        data object UpdatePoints: Event()
        data object MinusZoom : Event()
        data object FindCurrentLocation : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isAnimation: Boolean = false,
        val showBottomSheet: Boolean = false,
        val atmsAndDepartments: List<AtmDepartment> = emptyList(),
        val points: List<Point> = emptyList(),
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String) : Effect()
        data class Success(val success: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToInfoScreen(val type: InfrastructureType, val id: Long) : Navigation()
        }
    }
}