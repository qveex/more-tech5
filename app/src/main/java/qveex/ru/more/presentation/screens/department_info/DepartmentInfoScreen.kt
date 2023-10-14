package qveex.ru.more.presentation.screens.department_info

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessibleForward
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import qveex.ru.more.R
import qveex.ru.more.data.models.Status
import qveex.ru.more.presentation.components.EmptyContent
import qveex.ru.more.presentation.screens.department_info.components.DayInWeekItem
import qveex.ru.more.presentation.screens.snack

private const val TAG = "DepartmentInfoScreen"

@Composable
fun DepartmentInfoScreen(
    state: DepartmentInfoContract.State,
    effectFlow: Flow<DepartmentInfoContract.Effect>?,
    onEventSent: (event: DepartmentInfoContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: DepartmentInfoContract.Effect.Navigation) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.i(TAG, "Department Info Screen Launched")
        effectFlow?.onEach { effect ->
            when (effect) {
                is DepartmentInfoContract.Effect.Error -> snack(coroutineScope, snackbarHostState, effect.error)
                is DepartmentInfoContract.Effect.Success -> snack(coroutineScope, snackbarHostState, effect.success)
                is DepartmentInfoContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        topBar = {
            DepartmentInfoTopBar(
                onBackPressed = { onNavigationRequested(DepartmentInfoContract.Effect.Navigation.PopBackStack) }
            )
        },
        floatingActionButton = {
            BottomDepartmentInfoBar { }
        },
        floatingActionButtonPosition = FabPosition.Center,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to MaterialTheme.colorScheme.primaryContainer,
                            0.5f to MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.department?.let { department ->
                Text(
                    text = stringResource(id = R.string.title_address),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = department.address,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = department.metroStation,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.padding(4.dp))
                val isOpen = department.status == Status.OPEN
                val statusColor = if (isOpen) Color.Green else Color.Red
                val statusText = stringResource(id =
                    if (isOpen) R.string.title_open
                    else R.string.title_closed
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(statusColor.copy(alpha = .175f)),
                ) {
                    Text(
                        modifier = Modifier.padding(22.dp),
                        text = statusText,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    department.individual.forEach {
                        DayInWeekItem(curDay = state.curDay, days = it)
                    }
                }
                // график

                Text(text = "")
                department.hasRamp.takeIf { it }?.let { hasRamp ->
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepartmentInfoTopBar(
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.title_info_department),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackPressed,
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back Arrow icon"
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent//MaterialTheme.colorScheme.background,
            //navigationIconContentColor = MaterialTheme.colorScheme.surface,
            //titleContentColor = MaterialTheme.colorScheme.surface,
        )
    )
}

@Composable
private fun BottomDepartmentInfoBar(
    onCreateRouteClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        onClick = onCreateRouteClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.title_create_route),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun DepartmentInfoTopBarPreview() {
    DepartmentInfoTopBar(
        onBackPressed = { }
    )
}

@Preview
@Composable
private fun BottomDepartmentInfoBarPreview() {
    BottomDepartmentInfoBar(
        onCreateRouteClick = { }
    )
}