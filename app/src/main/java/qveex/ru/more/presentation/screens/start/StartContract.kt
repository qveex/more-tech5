package qveex.ru.more.presentation.screens.start

import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState

class StartContract {

    sealed class Event : ViewEvent {
        data object FindAtmsAndDepartments : Event()

        data class CheckFilter(val id: Long) : Event()
        data class CheckServiceFilter(val buttonId: Long, val ids: List<Long>) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isFilters: Boolean = false,
        val filters: List<StartFilter> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String) : Effect()
        data class Success(val success: String) : Effect()

        sealed class Navigation : Effect() {
            data object ToInfoScreen : Navigation()
        }
    }
}