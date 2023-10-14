package qveex.ru.more.presentation.screens.start

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import qveex.ru.more.R
import qveex.ru.more.presentation.components.AppLoading
import qveex.ru.more.presentation.screens.snack

private const val TAG = "StartScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    state: StartContract.State,
    effectFlow: Flow<StartContract.Effect>?,
    onEventSent: (event: StartContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: StartContract.Effect.Navigation) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val services =
        listOf(
            ServiceFilter(
                image = painterResource(id = R.drawable.ic_credit_card),
                text = stringResource(id = R.string.title_credit),
                backgroundColor = Color(0x99FDE35A),
                onClickListener = { onEventSent(StartContract.Event.CheckServiceFilter(0, listOf(4, 5))) }
            ),
            ServiceFilter(
                image = painterResource(id = R.drawable.ic_loan),
                text = stringResource(id = R.string.title_find_atm),
                backgroundColor = Color(0x9958FF6D),
                onClickListener = { onEventSent(StartContract.Event.CheckServiceFilter(1, listOf(1, 2))) }
            ),
            ServiceFilter(
                image = painterResource(id = R.drawable.ic_show_all),
                text = stringResource(id = R.string.title_find_departments),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = .65f),
                onClickListener = { onEventSent(StartContract.Event.CheckServiceFilter(2, listOf(1, 2, 3, 4, 5))) }
            ),
            ServiceFilter(
                image = painterResource(id = R.drawable.ic_atm),
                text = stringResource(id = R.string.title_open_deposit),
                backgroundColor = Color(0x99F254FF),
                onClickListener = { onEventSent(StartContract.Event.CheckServiceFilter(3, listOf(3))) }
            ),
        )

    BackHandler { }

    LaunchedEffect(Unit) {
        Log.i(TAG, "Start Info Screen Launched")
        effectFlow?.onEach { effect ->
            when (effect) {
                is StartContract.Effect.Error -> snack(
                    coroutineScope,
                    snackbarHostState,
                    effect.error
                )

                is StartContract.Effect.Success -> snack(
                    coroutineScope,
                    snackbarHostState,
                    effect.success
                )

                is StartContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    if (state.isLoading) AppLoading()
    else Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_what_you_want)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClick = { onEventSent(StartContract.Event.FindAtmsAndDepartments) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(id = R.string.title_find),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StartItemList(services = services, selectedService = state.selectedService)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.title_additional_filters),
                style = MaterialTheme.typography.titleLarge
            )
            Card(modifier = Modifier.padding(bottom = 72.dp)) {
                if (state.isFiltersLoading) {
                    AppLoading()
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.clientFilters) { filter ->
                            FilterRow(filter = filter, onClickListener = { onEventSent(StartContract.Event.SelectClientFilter(it)) })
                        }
                        items(state.departmentFilters) { filter ->
                            FilterRow(filter = filter, onClickListener = { onEventSent(StartContract.Event.SelectDepartmentFilter(it)) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterRow(
    filter: StartFilter,
    onClickListener: (id: Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickListener(filter.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = filter.checked,
            onCheckedChange = { onClickListener(filter.id) }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = filter.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun StartItemList(
    modifier: Modifier = Modifier,
    services: List<ServiceFilter>,
    selectedService: Long
) {
    LazyVerticalGrid(
        modifier= modifier,
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(services) { index, it ->
            StartItem(filter = it, selected = index.toLong() == selectedService)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StartItem(filter: ServiceFilter, selected: Boolean) {
    Card(
        onClick = filter.onClickListener,
        modifier = Modifier.padding(8.dp),
        border = BorderStroke(2.dp, filter.backgroundColor.copy(alpha = 1f)).takeIf { selected },
        colors = CardDefaults.cardColors(containerColor = filter.backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = filter.image,
                modifier = Modifier,
                contentDescription = "Service icon"
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                filter.text, style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class ServiceFilter(
    val image: Painter,
    val text: String,
    val backgroundColor: Color,
    val onClickListener: () -> Unit
)