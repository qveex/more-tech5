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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import qveex.ru.more.R
import qveex.ru.more.data.models.Atm
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.InfrastructureType
import qveex.ru.more.data.models.Location
import qveex.ru.more.domain.interactor.DepartmentInteractor
import qveex.ru.more.presentation.base.BaseViewModel
import qveex.ru.more.utils.Constants.INFO_ID_ARGUMENT
import qveex.ru.more.utils.Constants.INFO_TYPE_ARGUMENT
import qveex.ru.more.utils.ResourceProvider
import qveex.ru.more.utils.curDay
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
    private val type: InfrastructureType = when (savedStateHandle.get<String>(INFO_TYPE_ARGUMENT)) {
        "ATM" -> InfrastructureType.ATM
        else -> InfrastructureType.DEPARTMENT
    }
    private val id: Long = savedStateHandle.get<String>(INFO_ID_ARGUMENT)?.toLongOrNull() ?: -1L
    private val curDay = Calendar.getInstance().curDay

    init {
        Log.i(TAG, "params: type = $type, id = $id")
        setState {
            copy(
                type = this@DepartmentInfoViewModel.type,
                curDay = Days.MONDAY//this@DepartmentInfoViewModel.curDay
            )
        }
        viewModelScope.launch {
            when (type) {
                InfrastructureType.DEPARTMENT -> {
                    val info: Department? = interactor.getDepartmentInfo(id)
                    setState { copy(department = info) }
                }
                InfrastructureType.ATM -> {
                    val info: Atm? = interactor.getAtmInfo(id)
                    setState { copy(atm = info) }
                }
            }
        }
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
            delay(2000)
            initMarks()
        }
    }

    private fun onStop() {
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    private fun initMarks() {
        val state = viewState.value
        (state.department?.coordinates ?: state.atm?.coordinates)?.let {
            addPlace(it)
        }
    }

    private fun addPlace(place: Location) {
        Log.i(TAG, "addPlace place = $place")
        val isAtm = type == InfrastructureType.ATM
        val point = Point(place.latitude, place.longitude)
        val title = if (isAtm) "Банкомат\nВТБ" else "Отделение\nВТБ"
        val icon = if (isAtm) R.drawable.atm_ic else R.drawable.ic_place
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