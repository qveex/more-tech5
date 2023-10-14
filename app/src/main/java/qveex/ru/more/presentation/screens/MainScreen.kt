package qveex.ru.more.presentation.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import qveex.ru.more.presentation.components.BottomNav
import qveex.ru.more.presentation.navigation.SetupNavHost

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun MainScreen() {

    val navController = rememberAnimatedNavController()

    Scaffold(
        topBar = { },
        //bottomBar = { BottomNav(navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            SetupNavHost(navController = navController)
        }
    }
}

fun snack(
    scope: CoroutineScope,
    scaffold: SnackbarHostState,
    msg: String,
    actionLabel: String? = null
) {
    scope.launch {
        scaffold.apply {
            showSnackbar(
                message = msg,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
        }
    }
}