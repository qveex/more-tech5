package qveex.ru.more.presentation.screens.department_info

import com.yandex.mapkit.mapview.MapView
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState

class DepartmentInfoContract {

    sealed class Event : ViewEvent {
        data object OnStart: Event()
        data object OnStop: Event()
        data class SetMapView(val mapView: MapView): Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val curDay: Days = Days.MONDAY,
        val department: Department? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String): Effect()
        data class Success(val success: String): Effect()

        sealed class Navigation : Effect() {
            data object PopBackStack: Navigation()
        }
    }
}