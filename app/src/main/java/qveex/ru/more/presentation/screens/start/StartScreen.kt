package qveex.ru.more.presentation.screens.start

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import qveex.ru.more.R
import qveex.ru.more.data.models.StartFilter
import qveex.ru.more.presentation.components.AppLoading
import qveex.ru.more.presentation.screens.department_info.DepartmentInfoContract
import qveex.ru.more.presentation.screens.snack
import qveex.ru.more.ui.theme.Moretech5Theme


private const val TAG = "StartScreen"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartItem(image: Painter, text: String, backgroundColor: Color, onClickListener: () -> Unit) {
    Card(
        onClick = onClickListener,
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                modifier = Modifier,
                contentDescription = "loan"
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(text, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        }
    }

}

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

    val filterList = remember { mutableStateListOf<StartFilter>() }

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
    Scaffold(topBar = {
        LargeTopAppBar(
            title = { Text(text = "Какая услуга Вам нужна?") },
        )
    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(it),
        ) {
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_loan),
                    text = "Кредит или\nИпотека",
                    backgroundColor = Color(0xFFF8EAA2)
                ) {
                    onNavigationRequested(StartContract.Effect.Navigation.ToInfoScreen)
                }
            }
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_atm),
                    text = "Найти\nбанкомат",
                    backgroundColor = Color(0xFFD9FFDE)

                ) {
                    onNavigationRequested(StartContract.Effect.Navigation.ToInfoScreen)
                }
            }
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_show_all),
                    text = "Просмотр\nотделений",
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer

                ) {
                    onNavigationRequested(StartContract.Effect.Navigation.ToInfoScreen)
                }
            }
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_atm),
                    text = "Открыть\nвклад",
                    backgroundColor = Color(0x49F57EFF)
                ) {
                    onNavigationRequested(StartContract.Effect.Navigation.ToInfoScreen)
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text("Дополнительные фильтры", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.size(4.dp))
        Card {
            if (state.isLoading) {
                AppLoading()
            } else {
                LazyColumn {
                    items(filterList) { item ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(horizontal = 16.dp, 8.dp)) {
                            Checkbox(
                                checked = item.checked.value,
                                onCheckedChange = { checked -> item.checked.value = checked })
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(text = item.name, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

