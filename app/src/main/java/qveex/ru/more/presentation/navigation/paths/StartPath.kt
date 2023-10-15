package qveex.ru.more.presentation.navigation.paths

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import qveex.ru.more.LocalSharedViewModel
import qveex.ru.more.presentation.navigation.Screen
import qveex.ru.more.presentation.navigation.defaultEnter
import qveex.ru.more.presentation.navigation.defaultExit
import qveex.ru.more.presentation.screens.start.StartContract
import qveex.ru.more.presentation.screens.start.StartScreen
import qveex.ru.more.presentation.screens.start.StartViewModel
import qveex.ru.more.utils.safeNavigate

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.startNav(navController: NavController) {
    composable(
        route = Screen.Start.route,
        enterTransition = { defaultEnter },
        exitTransition = { defaultExit }
    ) {
        val viewModel = hiltViewModel<StartViewModel>()
        val sharedViewModel = LocalSharedViewModel.current
        StartScreen(
            state = viewModel.viewState.value,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onNavigationRequested = {
                navController.safeNavigate(
                    when (it) {
                        is StartContract.Effect.Navigation.ToHomeScreen -> {
                            sharedViewModel.infoParams = it.infoParams
                            Screen.Home.route
                        }
                        is StartContract.Effect.Navigation.ToInfoScreen -> Screen.DepartmentInfo.pasParams(it.type, it.id)
                    }
                )
            }
        )
    }
}