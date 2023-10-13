package qveex.ru.more.presentation.screens.departments

import dagger.hilt.android.lifecycle.HiltViewModel
import qveex.ru.more.domain.interactor.DepartmentInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.ResourceProvider
import javax.inject.Inject

@HiltViewModel
class DepartmentsViewModel @Inject constructor(
    private val interactor: DepartmentInteractor,
    private val resProvider: ResourceProvider
) : BaseViewModel<
        DepartmentsContract.Event,
        DepartmentsContract.State,
        DepartmentsContract.Effect,
        >() {
    override fun setInitialState() = DepartmentsContract.State()

    override fun handleEvents(event: DepartmentsContract.Event) {
        when (event) {
            is DepartmentsContract.Event.Click -> {}
            else -> {}
        }
    }

}