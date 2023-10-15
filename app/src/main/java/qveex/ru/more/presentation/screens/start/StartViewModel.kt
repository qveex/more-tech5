package qveex.ru.more.presentation.screens.start

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import qveex.ru.more.domain.interactor.StartInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import qveex.ru.more.utils.replace
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val interactor: StartInteractor,
    private val resProvider: ResourceProvider
) : BaseViewModel<
        StartContract.Event,
        StartContract.State,
        StartContract.Effect,
        >() {

    init {
        viewModelScope.launch {
            setState { copy(isFiltersLoading = true) }
            val departmentFilters = interactor.getDepartmentFilters() ?: emptyList()
            val clientFilters = interactor.getClientFilters() ?: emptyList()
            setState {
                copy(
                    isFiltersLoading = false,
                    departmentFilters = departmentFilters.map {
                        StartFilter(it.id, it.name, false)
                    },
                    clientFilters = clientFilters.map {
                        StartFilter(it.id, it.name, false)
                    },
                    needRamp = StartFilter(0, "Для маломобильных", false) // бэк отказывается засылать его в фильтрах
                )
            }
        }
    }

    private var services = emptyList<Long>()

    override fun setInitialState() = StartContract.State()

    override fun handleEvents(event: StartContract.Event) {
        when (event) {
            is StartContract.Event.FindAtmsAndDepartments -> find()
            is StartContract.Event.SelectRamp -> setRamp()
            is StartContract.Event.SelectClientFilter -> selectClientFilter(event.id)
            is StartContract.Event.SelectDepartmentFilter -> selectDepartmentFilter(event.id)
            is StartContract.Event.SelectServiceFilter -> checkServiceFilters(event.buttonId, event.ids)
        }
    }

    private fun setRamp() = setState { copy(needRamp = needRamp?.copy(checked = !needRamp.checked)) }

    private fun selectClientFilter(id: Long) {
        setState {
            val filter = clientFilters.firstOrNull { it.id == id } ?: return@setState this
            copy(
                clientFilters = clientFilters.replace(
                    filter.copy(checked = !filter.checked)
                ) {
                    it.id == filter.id
                }
            )
        }
    }

    private fun selectDepartmentFilter(id: Long) {
        setState {
            val filter = departmentFilters.firstOrNull { it.id == id } ?: return@setState this
            copy(
                clientFilters = departmentFilters.replace(
                    filter.copy(checked = !filter.checked)
                ) {
                    it.id == filter.id
                }
            )
        }
    }

    private fun checkServiceFilters(buttonId: Long, ids: List<Long>) {
        services = ids
        setState { copy(selectedService = buttonId) }
    }

    private fun find() {
        val state = viewState.value
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val info = interactor.findInfo(
                services = services,
                officeTypes = state.departmentFilters.filter { it.checked }.map { it.id },
                clientTypes = state.clientFilters.filter { it.checked }.map { it.id },
                hasRamp = false
            )
            setState { copy(isLoading = false) }
            info?.let { setEffect { StartContract.Effect.Navigation.ToHomeScreen(info)  }  }
        }
    }

}


data class StartFilter(
    val id: Long,
    val name: String,
    val checked: Boolean
)
