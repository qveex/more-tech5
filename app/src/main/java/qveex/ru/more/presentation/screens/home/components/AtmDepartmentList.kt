package qveex.ru.more.presentation.screens.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import qveex.ru.more.R
import qveex.ru.more.presentation.components.EmptyContent
import qveex.ru.more.presentation.screens.home.AtmDepartment

@Composable
fun AtmDepartmentList(
    list: List<AtmDepartment>,
    onDepartmentClick: (AtmDepartment) -> Unit
) {
    AnimatedContent(targetState = list.isEmpty(), label = "emptyAnimation") { isEmpty ->
        if (isEmpty) {
            EmptyContent(message = stringResource(id = R.string.title_empty_departments_atms_list))
        } else {
            LazyColumn(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                items(list) {
                    AtmDepartmentItem(
                        atmDepartment = it,
                        onDepartmentClick = onDepartmentClick
                    )
                }
            }
        }
    }

}