package qveex.ru.more.presentation.screens.home

import qveex.ru.more.presentation.base.ViewEvent
import qveex.ru.more.presentation.base.ViewSideEffect
import qveex.ru.more.presentation.base.ViewState

class HomeContract {

    sealed class Event : ViewEvent {
        data class SelectDepartment(val departmentId: Long): Event()
        data class SelectAtm(val atmId: Long): Event()
        data class ShowBottomSheet(val show: Boolean): Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val showBottomSheet: Boolean = false,
        val atmsAndDepartments: List<AtmDepartment> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        data class Error(val error: String): Effect()
        data class Success(val success: String): Effect()

        sealed class Navigation : Effect() {
            data class ToDepartmentInfoScreen(val id: Long) : Navigation()
        }
    }
}