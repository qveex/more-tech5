package qveex.ru.more.presentation.screens.department_info.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Entity
import qveex.ru.more.data.models.OpenHours

@Composable
fun DayInWeekItem(
    days: Entity
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val isWeekend = days.day in listOf(Days.SATURDAY, Days.SUNDAY)
        val cardColor =
            if (isWeekend) Color.Red.copy(alpha = .25f)
            else MaterialTheme.colorScheme.surfaceVariant
        Card(
            colors = CardDefaults.cardColors(
                containerColor = cardColor
            ),
            shape = CircleShape
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = days.day.labelId)
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = days.openHours.from)
        Text(text = days.openHours.to)
    }
}

@Preview
@Composable
private fun DayInWeekItemPreview() {
    DayInWeekItem(
        Entity(
            day = Days.MONDAY,
            openHours = OpenHours("9:00", "22:00")
        )
    )
}

@Preview
@Composable
private fun DayInWeekItemPreview2() {
    DayInWeekItem(
        Entity(
            day = Days.SATURDAY,
            openHours = OpenHours("9:00", "22:00")
        )
    )
}

