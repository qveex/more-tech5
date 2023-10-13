package qveex.ru.more.presentation.screens.onboarding

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
fun OnboardingScreen(
    state: OnboardingContract.State,
    effectFlow: Flow<OnboardingContract.Effect>?,
    onEventSent: (event: OnboardingContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: OnboardingContract.Effect.Navigation) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.i(TAG, "Home Screen Launched")
        effectFlow?.onEach { effect ->
            when (effect) {
                is OnboardingContract.Effect.Error -> snack(coroutineScope, snackbarHostState, effect.error)
                is OnboardingContract.Effect.Success -> snack(coroutineScope, snackbarHostState, effect.success)
                is OnboardingContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Box() {

    }
}