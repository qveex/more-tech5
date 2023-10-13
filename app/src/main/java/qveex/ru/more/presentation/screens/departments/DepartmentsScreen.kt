package qveex.ru.more.presentation.screens.departments

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import qveex.ru.more.presentation.screens.snack

private const val TAG = "DepartmentsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentsScreen(
    state: DepartmentsContract.State,
    effectFlow: Flow<DepartmentsContract.Effect>?,
    onEventSent: (event: DepartmentsContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: DepartmentsContract.Effect.Navigation) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.i(TAG, "Departments Screen Launched")
        effectFlow?.onEach { effect ->
            when (effect) {
                is DepartmentsContract.Effect.Error -> snack(coroutineScope, snackbarHostState, effect.error)
                is DepartmentsContract.Effect.Success -> snack(coroutineScope, snackbarHostState, effect.success)
                is DepartmentsContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable { onEventSent(DepartmentsContract.Event.Click) },
            contentAlignment = Alignment.Center
        ) {
            Text("Departments")
        }
    }
}