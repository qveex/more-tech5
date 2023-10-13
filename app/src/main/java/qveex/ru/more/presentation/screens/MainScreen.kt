package qveex.ru.more.presentation.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import qveex.ru.more.presentation.navigation.SetupNavHost

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen() {

    val navController = rememberAnimatedNavController()

    Scaffold(
        topBar = { },
        bottomBar = { }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            SetupNavHost(navController = navController)
        }
    }
}