package qveex.ru.more.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import qveex.ru.more.presentation.screens.home.HomeContract

@Composable
fun Map(
    onEventSent: (event: HomeContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onEventSent(HomeContract.Event.OnStart)
                Lifecycle.Event.ON_STOP -> onEventSent(HomeContract.Event.OnStop)
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val isSystemInDarkTheme = isSystemInDarkTheme()
    AndroidView(factory = {
        MapView(it).also {
            it.map.isNightModeEnabled = isSystemInDarkTheme
            onEventSent(HomeContract.Event.SetMapView(it))
        }
    }, modifier = modifier)
}