package qveex.ru.more.presentation.screens.start

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import qveex.ru.more.data.models.StartFilter
import qveex.ru.more.domain.interactor.OnboardingInteractor
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
            setState { copy(true, emptyList()) }
            val filters = interactor.getFilters()
            setState { copy(false, filters = filters) }
        }
    }

    private fun replaceItem(id: Int) {
        setState {
            val filter = filters.first { it.id == id }
            filters.replace(
                StartFilter(filter.id, filter.name, !filter.checked)
            ) { it.id == filter.id }
            copy(false, filters)
        }
    }

    override fun setInitialState() = StartContract.State()

    override fun handleEvents(event: StartContract.Event) {
        when (event) {
            is StartContract.Event.Click -> {}
            is StartContract.Event.CheckFilter -> {
                replaceItem(event.id)
            }

            else -> {}
        }
    }

}