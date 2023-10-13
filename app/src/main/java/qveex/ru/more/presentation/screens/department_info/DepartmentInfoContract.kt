package qveex.ru.more.presentation.screens.department_info

import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState

class DepartmentInfoContract {

    sealed class Event : ViewEvent {
        data object Click: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val department: String = ""
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String): Effect()
        data class Success(val success: String): Effect()

        sealed class Navigation : Effect() {
        }
    }
}