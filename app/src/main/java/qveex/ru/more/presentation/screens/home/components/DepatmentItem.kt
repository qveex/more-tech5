package qveex.ru.more.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentItem(

    onDepartmentClick: () -> Unit
) {
    Card(
        onClick = onDepartmentClick,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val icon = Icons.Outlined.AccountBalance
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Department icon"
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "", style = MaterialTheme.typography.bodyLarge)
                Row {
                    Icon(imageVector = Icons.Outlined.TravelExplore, contentDescription = "Nearest metro")
                    Text(text = "", style = MaterialTheme.typography.bodyMedium)
                }

                val color = Color.Green
                Text(
                    text = "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color
                )
            }
            Text(text = "", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Preview
@Composable
fun DepartmentItemPreview() {
    DepartmentItem(
        onDepartmentClick= {}
    )
}