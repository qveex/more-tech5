package qveex.ru.more.presentation.navigation.paths

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import qveex.ru.more.presentation.navigation.Screen
import qveex.ru.more.presentation.navigation.defaultEnter
import qveex.ru.more.presentation.navigation.defaultExit
import qveex.ru.more.presentation.screens.onboarding.OnboardingContract
import qveex.ru.more.presentation.screens.onboarding.OnboardingScreen
import qveex.ru.more.presentation.screens.onboarding.OnboardingViewModel
import qveex.ru.more.utils.safeNavigate

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.onboardingNav(navController: NavController) {
    composable(
        route = Screen.Onboarding.route,
        enterTransition = { defaultEnter },
        exitTransition = { defaultExit }
    ) {
        val viewModel = hiltViewModel<OnboardingViewModel>()
        OnboardingScreen(
            state = viewModel.viewState.value,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onNavigationRequested = {
                navController.safeNavigate(
                    when (it) {
                        is OnboardingContract.Effect.Navigation.ToHomeScreen -> Screen.Home.route
                    }
                )
            }
        )
    }
}