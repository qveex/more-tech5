package qveex.ru.more.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.mapview.MapView
import dagger.Component


@Composable
fun Map() {
    AndroidView(factory = {
        val mapView = MapView(it)

        mapView
    }, modifier = Modifier.fillMaxSize())
}