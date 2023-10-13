package qveex.ru.more.presentation.screens.onboarding

import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState
import qveex.ru.more.presentation.screens.home.HomeContract

class OnboardingContract {

    sealed class Event : ViewEvent {
        data object Click: Event()
    }

    data class State(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String): Effect()
        data class Success(val success: String): Effect()

        sealed class Navigation : Effect() {
            data object ToHomeScreen : Navigation()
        }
    }
}