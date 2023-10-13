package qveex.ru.more.presentation.navigation.paths

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import qveex.ru.more.presentation.navigation.Screen
import qveex.ru.more.presentation.navigation.defaultEnter
import qveex.ru.more.presentation.navigation.defaultExit

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.onboardingNav(navController: NavController) {
    composable(
        route = Screen.Onboarding.route,
        enterTransition = { defaultEnter },
        exitTransition = { defaultExit }
    ) {
        // vm
        // screen
    }
}