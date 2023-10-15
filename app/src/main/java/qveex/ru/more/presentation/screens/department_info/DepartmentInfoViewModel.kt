package qveex.ru.more.presentation.screens.department_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.domain.interactor.DepartmentInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.Constants.INFO_ARGUMENT
import qveex.ru.more.utils.ResourceProvider
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DepartmentInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: DepartmentInteractor,
    private val resProvider: ResourceProvider
) : BaseViewModel<
        DepartmentInfoContract.Event,
        DepartmentInfoContract.State,
        DepartmentInfoContract.Effect,
        >() {

    private val id: Long = savedStateHandle.get<String>(INFO_ARGUMENT)?.toLongOrNull() ?: -1L
    private val curDay = when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY    -> Days.MONDAY
        Calendar.TUESDAY   -> Days.TUESDAY
        Calendar.WEDNESDAY -> Days.WEDNESDAY
        Calendar.THURSDAY  -> Days.THURSDAY
        Calendar.FRIDAY    -> Days.FRIDAY
        Calendar.SATURDAY  -> Days.SATURDAY
        Calendar.SUNDAY    -> Days.MONDAY
        else -> Days.MONDAY
    }

    init {
        viewModelScope.launch {
            val info: Department? = interactor.getInfo(id)
            setState {
                copy(department = info)
            }
        }
        setState { copy(curDay = this@DepartmentInfoViewModel.curDay) }
    }

    override fun setInitialState() = DepartmentInfoContract.State()

    override fun handleEvents(event: DepartmentInfoContract.Event) {
        when (event) {
            is DepartmentInfoContract.Event.Click -> {}
            else -> {}
        }
    }

}