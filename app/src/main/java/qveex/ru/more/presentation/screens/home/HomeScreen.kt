package qveex.ru.more.presentation.screens.home

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import qveex.ru.more.R
import qveex.ru.more.presentation.components.Map
import qveex.ru.more.presentation.components.MapZoomButtons
import qveex.ru.more.presentation.screens.home.components.AtmDepartmentList
import qveex.ru.more.presentation.screens.snack

private const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEventSent: (event: HomeContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: HomeContract.Effect.Navigation) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        Log.i(TAG, "Home Screen Launched")
        effectFlow?.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.Error -> snack(
                    coroutineScope, snackbarHostState, effect.error
                )

                is HomeContract.Effect.Success -> snack(
                    coroutineScope, snackbarHostState, effect.success
                )

                is HomeContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            val size = 64.dp
            val radius = 16.dp.takeUnless { state.showBottomSheet } ?: (size / 2f)
            val cornerRadius =
                animateDpAsState(targetValue = radius, label = "cornerRadiusAnimation")
            ExtendedFloatingActionButton(modifier = Modifier.size(size),
                shape = RoundedCornerShape(cornerRadius.value),
                onClick = { onEventSent(HomeContract.Event.ShowBottomSheet(true)) }) {
                Icon(
                    imageVector = Icons.Outlined.List, contentDescription = "List icon"
                )
            }
        }
    ) {

        if (state.showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { onEventSent(HomeContract.Event.ShowBottomSheet(false)) },
                sheetState = sheetState
            ) {
                AtmDepartmentList(state.atmsAndDepartments) {
                    onEventSent(HomeContract.Event.ShowBottomSheet(false))
                    onNavigationRequested(HomeContract.Effect.Navigation.ToDepartmentInfoScreen(it.id))
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Map(onEventSent)
            MapZoomButtons(
                plusZoomOnClickListener = { onEventSent(HomeContract.Event.PlusZoom) },
                minusZoomOnClickListener = { onEventSent(HomeContract.Event.MinusZoom) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to MaterialTheme.colorScheme.primaryContainer,
                            1f to Color.Transparent
                        )
                    )
                )
        ) {
            MapTopBar()
        }
    }
}

@Composable
private fun MapTopBar() {
    Row(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(72.dp),
            painter = painterResource(id = R.drawable.vtb_logo_add_ru_rgb),
            contentDescription = "VTB logo"
        )
    }
}