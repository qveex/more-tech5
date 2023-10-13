package qveex.ru.more.presentation.screens.onboarding

import dagger.hilt.android.lifecycle.HiltViewModel
import qveex.ru.more.domain.interactor.OnboardingInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val interactor: OnboardingInteractor,
    private val resProvider: ResourceProvider
) : BaseViewModel<
        OnboardingContract.Event,
        OnboardingContract.State,
        OnboardingContract.Effect,
        >() {
    override fun setInitialState() = OnboardingContract.State()

    override fun handleEvents(event: OnboardingContract.Event) {
        when (event) {
            is OnboardingContract.Event.Click -> {}
            else -> {}
        }
    }

}