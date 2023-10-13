package qveex.ru.more.presentation.screens.departments

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import qveex.ru.more.presentation.screens.snack

private const val TAG = "DepartmentsScreen"

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
        Log.i(TAG, "Home Screen Launched")
        effectFlow?.onEach { effect ->
            when (effect) {
                is DepartmentsContract.Effect.Error -> snack(coroutineScope, snackbarHostState, effect.error)
                is DepartmentsContract.Effect.Success -> snack(coroutineScope, snackbarHostState, effect.success)
                is DepartmentsContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Box() {

    }
}