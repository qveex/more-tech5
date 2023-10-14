package qveex.ru.more.presentation.screens.start

import dagger.hilt.android.lifecycle.HiltViewModel
import qveex.ru.more.domain.interactor.OnboardingInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val interactor: OnboardingInteractor,
    private val resProvider: ResourceProvider
) : BaseViewModel<
        StartContract.Event,
        StartContract.State,
        StartContract.Effect,
        >() {
    override fun setInitialState() = StartContract.State()

    override fun handleEvents(event: StartContract.Event) {
        when (event) {
            is StartContract.Event.Click -> {}
            else -> {}
        }
    }

}