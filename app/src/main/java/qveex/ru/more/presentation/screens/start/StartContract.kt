package qveex.ru.more.presentation.screens.start

import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState

class StartContract {

    sealed class Event : ViewEvent {
        data object FindAtmsAndDepartments : Event()

        data class SelectClientFilter(val id: Long) : Event()
        data class SelectDepartmentFilter(val id: Long) : Event()
        data class SelectServiceFilter(val buttonId: Long, val ids: List<Long>) : Event()
        data object SelectRamp : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isFiltersLoading: Boolean = false,
        val selectedService: Long = -1,
        val departmentFilters: List<StartFilter> = emptyList(),
        val clientFilters: List<StartFilter> = emptyList(),
        val needRamp: StartFilter? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String) : Effect()
        data class Success(val success: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToInfoScreen(val id: Long) : Navigation()
            data object ToHomeScreen : Navigation()
        }
    }
}