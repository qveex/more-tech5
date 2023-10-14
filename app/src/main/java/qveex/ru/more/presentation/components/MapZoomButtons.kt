package qveex.ru.more.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qveex.ru.more.ui.theme.Moretech5Theme


@Composable
fun MapZoomButton(icon: ImageVector, description: String, onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(icon, description)
    }
}

@Composable
fun MapZoomButtons(
    plusZoomOnClickListener: () -> Unit,
    minusZoomOnClickListener: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        MapZoomButton(icon = Icons.Rounded.Add, description = "+ zoom", plusZoomOnClickListener)
        Spacer(modifier = Modifier.size(16.dp))
        MapZoomButton(icon = Icons.Rounded.Remove, description = "- zoom", minusZoomOnClickListener)
    }
}

@Preview
@Composable
fun PreviewMapZoomButtons() {
    Moretech5Theme {
        MapZoomButtons({}, {})
    }
}