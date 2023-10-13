package qveex.ru.more.presentation.screens.department_info

import dagger.hilt.android.lifecycle.HiltViewModel
import qveex.ru.more.domain.interactor.DepartmentInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import javax.inject.Inject

@HiltViewModel
class DepartmentInfoViewModel @Inject constructor(
    private val interactor: DepartmentInteractor,
    private val resProvider: ResourceProvider
) : BaseViewModel<
        DepartmentInfoContract.Event,
        DepartmentInfoContract.State,
        DepartmentInfoContract.Effect,
        >() {
    override fun setInitialState() = DepartmentInfoContract.State()

    override fun handleEvents(event: DepartmentInfoContract.Event) {
        when (event) {
            is DepartmentInfoContract.Event.Click -> {}
            else -> {}
        }
    }

}