package qveex.ru.more.presentation.screens.home

import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState

class HomeContract {

    sealed class Event : ViewEvent {
        data object SelectDepartment: Event()
    }

    data class State(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String): Effect()
        data class Success(val success: String): Effect()

        sealed class Navigation : Effect() {
            data object ToDepartmentInfoScreen : Navigation()
        }
    }
}