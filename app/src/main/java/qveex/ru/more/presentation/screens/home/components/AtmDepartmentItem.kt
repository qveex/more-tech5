package qveex.ru.more.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.PointOfSale
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qveex.ru.more.R
import qveex.ru.more.data.models.InfrastructureType
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.Status
import qveex.ru.more.presentation.screens.home.AtmDepartment
import qveex.ru.more.ui.theme.Moretech5Theme
import qveex.ru.more.ui.theme.errorColor
import qveex.ru.more.ui.theme.successColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtmDepartmentItem(
    atmDepartment: AtmDepartment,
    onDepartmentClick: (AtmDepartment) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onDepartmentClick(atmDepartment) },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val isAtm = atmDepartment.type == InfrastructureType.ATM
            val icon = if (isAtm) Icons.Outlined.PointOfSale else Icons.Outlined.AccountBalance
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp),
                imageVector = icon,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Department icon"
            )
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = atmDepartment.address, style = MaterialTheme.typography.bodyLarge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.TravelExplore,
                        contentDescription = "Nearest subway",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = atmDepartment.metro, style = MaterialTheme.typography.bodySmall)
                }

                val isOpen = atmDepartment.status == Status.OPEN
                val color = if (isOpen) successColor else errorColor

                val isOpenText = stringResource(
                    id = if (isOpen) R.string.title_open else R.string.title_closed
                )
                val atText = if (isAtm) "" else stringResource(
                    id = R.string.before,
                    if (isOpen) atmDepartment.closeAt!! else atmDepartment.openAt!!
                )
                Text(
                    text = "$isOpenText $atText",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color
                )
            }
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = stringResource(id = R.string.meters, atmDepartment.distance),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Preview
@Composable
fun DepartmentItemPreview() {
    Moretech5Theme {
        AtmDepartmentItem(
            atmDepartment = AtmDepartment(
                id = 1L,
                address = "ул. Пушкина д. 3",
                metro = "Сенная площадь",
                distance = 123,
                location = Location(.0, .0),
                type = InfrastructureType.ATM,
                status = Status.OPEN,
                openAt = "9:00",
                closeAt = "22:00"
            ),
            onDepartmentClick = {}
        )
    }
}