package qveex.ru.more.presentation.screens.department_info

import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import qveex.ru.more.R
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.InfrastructureType
import qveex.ru.more.data.models.Location
import qveex.ru.more.domain.interactor.DepartmentInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.presentation.screens.home.AtmDepartment
import qveex.ru.more.presentation.screens.home.HomeViewModel
import qveex.ru.more.utils.Constants.INFO_ARGUMENT
import qveex.ru.more.utils.ResourceProvider
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

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

    companion object {
        private const val TAG = "DepartmentInfoViewModel"
    }

    private lateinit var mapView: MapView
    private lateinit var mapObjectCollection: MapObjectCollection
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
            is DepartmentInfoContract.Event.OnStart -> onStart()
            is DepartmentInfoContract.Event.OnStop -> onStop()
            is DepartmentInfoContract.Event.SetMapView -> setMapView(event.mapView)
        }
    }

    private fun setMapView(mapView: MapView) {
        this.mapView = mapView
        mapObjectCollection = mapView.mapWindow.map.mapObjects.addCollection()

    }

    private fun onStart() {
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
        viewModelScope.launch {
            runCatching {
                withTimeout(10.seconds) {
                    val state = viewState.value
                    while (state.department == null) {}
                    initMarks()
                }
            }.onFailure { Log.e(TAG, "can't init deps info for mark") }
        }
    }

    private fun onStop() {
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    private fun initMarks() {
        viewState.value.department?.coordinates?.let {
            addPlace(it)
        }
    }

    private fun addPlace(place: Location) {
        Log.i(TAG, "addPlace place = $place")
        val point = Point(place.latitude, place.longitude)
        val title = "Отделение\nВТБ"
        val icon = R.drawable.ic_place
        mapObjectCollection.addPlacemark(
            point,
            ImageProvider.fromBitmap(
                (ResourcesCompat.getDrawable(
                    mapView.context.resources,
                    icon,
                    null
                ) as VectorDrawable).toBitmap()
            )
        ).apply {
            zIndex = 100.0f
            setText(
                title,
                TextStyle()
                    .setPlacement(TextStyle.Placement.BOTTOM)
                    .setSize(12.0f)
                    .setOutlineColor(Color.WHITE)
            )
        }
        mapView.mapWindow.map.move(
            CameraPosition(point, 15.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.3f),
        ) {}
    }

}