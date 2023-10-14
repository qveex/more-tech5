package qveex.ru.more.presentation.screens.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessibleForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qveex.ru.more.R
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.Entity
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.OpenHours
import qveex.ru.more.data.models.Status
import qveex.ru.more.presentation.components.EmptyContent
import qveex.ru.more.presentation.components.OverViewItem
import qveex.ru.more.presentation.screens.department_info.DepartmentInfoContract
import qveex.ru.more.presentation.screens.department_info.components.DayInWeekItem
import qveex.ru.more.ui.theme.Moretech5Theme

@Composable
fun DepartmentOverviewScreen(state: DepartmentInfoContract.State) {
    Column {
        state.department?.let { department ->
            Text(
                text = "Вам подходит отеделение на ${department.address}",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = department.metroStation,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(4.dp))

            // map
            val isOpen = department.status == Status.OPEN
            val statusColor = if (isOpen) Color.Green else Color.Red
            val statusText = stringResource(
                id =
                if (isOpen) R.string.title_open
                else R.string.title_closed
            )


            Text(text = statusText)
            department.hasRamp.takeIf { it }?.let {
                Spacer(modifier = Modifier.padding(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Outlined.AccessibleForward,
                        contentDescription = "Schumacher icon"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = stringResource(id = R.string.title_has_ramp))
                }
            }




        } ?: EmptyContent(message = "")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DepartmentOverviewPreview() {
    val a = Department(
        departmentId = 1,
        address = "ул. Пушкина д. 3",
        metroStation = "Сенная площадь",
        status = Status.CLOSED,
        distance = 123,
        workload = 50,
        location = Location(.0, .0),
        hasRamp = true,
        legal = listOf(
            Entity(Days.SATURDAY, OpenHours("9:00", "22:00"))
        ),
        individual = listOf(
            Entity(Days.SATURDAY, OpenHours("9:00", "22:00"))
        ),
    )

    Moretech5Theme {
        DepartmentOverviewScreen(state = DepartmentInfoContract.State(false, department = a))
    }
}
