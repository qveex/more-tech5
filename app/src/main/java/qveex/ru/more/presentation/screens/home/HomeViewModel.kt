package qveex.ru.more.presentation.screens.home

import dagger.hilt.android.lifecycle.HiltViewModel
import qveex.ru.more.domain.interactor.HomeInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactor: HomeInteractor,
    private val resProvider: ResourceProvider
): BaseViewModel<
        HomeContract.Event,
        HomeContract.State,
        HomeContract.Effect,
        >() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {

    }

    override fun setInitialState() = HomeContract.State()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.SelectDepartment -> selectDepartment()
        }
    }

    private fun selectDepartment() {
        setEffect {
            HomeContract.Effect.Success("Click")
        }
    }
}