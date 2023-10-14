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
            setState { copy(isFilters = true) }
            val filters = interactor.getFilters()
            setState {
                copy(
                    isFilters = false,
                    filters = filters.map {
                        StartFilter(it.id, it.name, false)
                    }
                )
            }
        }
    }

    override fun setInitialState() = StartContract.State()

    override fun handleEvents(event: StartContract.Event) {
        when (event) {
            is StartContract.Event.FindAtmsAndDepartments -> {}
            is StartContract.Event.CheckFilter -> checkItem(event.id)
            is StartContract.Event.CheckServiceFilter -> checkServiceFilters(event.buttonId, event.ids)
        }
    }

    private fun checkItem(id: Long) {
        setState {
            val filter = filters.firstOrNull { it.id == id } ?: return@setState this
            copy(
                filters = filters.replace(
                    filter.copy(checked = !filter.checked)
                ) {
                    it.id == filter.id
                }
            )
        }
    }

    private fun checkServiceFilters(buttonId: Long, ids: List<Long>) {

    }

    private fun find() {
        val state = viewState.value
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            interactor.findInfo()
            setState { copy(isLoading = false) }
        }
    }

}


data class StartFilter(
    val id: Long,
    val name: String,
    val checked: Boolean
)
