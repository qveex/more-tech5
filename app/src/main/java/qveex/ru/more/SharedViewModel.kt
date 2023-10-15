package qveex.ru.more

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import qveex.ru.more.data.models.Info
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(

) : ViewModel() {

    var infoParams: InfoParams? = null

}

data class InfoParams(
    val serviceFilters: List<Long>,
    val departmentFilters: List<Long>,
    val clientFilters: List<Long>,
    val hasRamp: Boolean
)