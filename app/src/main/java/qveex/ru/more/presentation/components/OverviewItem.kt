package qveex.ru.more.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import qveex.ru.more.ui.theme.Moretech5Theme

@Composable
fun OverViewItem(icon: Painter, text: String) {
    Row {
        Icon(icon, contentDescription = null)
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}