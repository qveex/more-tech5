package qveex.ru.more.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import qveex.ru.more.presentation.navigation.paths.departmentInfoNav
import qveex.ru.more.presentation.navigation.paths.homeNav
import qveex.ru.more.presentation.navigation.paths.onboardingNav
import qveex.ru.more.presentation.navigation.paths.startNav
import qveex.ru.more.utils.Constants.SCREEN_TRANSIT_DURATION_MILLIS

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalFoundationApi
@Composable
fun SetupNavHost(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        onboardingNav(navController)
        homeNav(navController)
        departmentInfoNav(navController)
        startNav(navController)
    }
}

val defaultEnter = fadeIn(animationSpec = tween(SCREEN_TRANSIT_DURATION_MILLIS))
val defaultExit = fadeOut(animationSpec = tween(SCREEN_TRANSIT_DURATION_MILLIS))