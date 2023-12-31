package qveex.ru.more.presentation.base

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel
        <Event : ViewEvent,
        UiState : ViewState,
        Effect : ViewSideEffect>
    : ViewModel() {

    companion object {
        private const val TAG = "BaseViewModel"
    }

    private val initialState: UiState by lazy { setInitialState() }
    abstract fun setInitialState(): UiState

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        Log.d(TAG, "newState = $newState")
        _viewState.value = newState
    }

    private fun subscribeToEvents() {
        _event.onEach {
            Log.d(TAG, "event = $it")
            handleEvents(it)
        }.launchIn(viewModelScope)
    }

    abstract fun handleEvents(event: Event)

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        Log.d(TAG, "effect = $effectValue")
        viewModelScope.launch { _effect.send(effectValue) }
    }
}