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
import qveex.ru.more.presentation.screens.home.HomeContract
import qveex.ru.more.presentation.screens.home.HomeScreen
import qveex.ru.more.presentation.screens.home.HomeViewModel
import qveex.ru.more.utils.safeNavigate

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeNav(navController: NavController) {
    composable(
        route = Screen.Home.route,
        enterTransition = { defaultEnter },
        exitTransition = { defaultExit }
    ) {
        val viewModel = hiltViewModel<HomeViewModel>()
        val sharedViewModel = LocalSharedViewModel.current
        viewModel.setEvent(HomeContract.Event.SetInfoParam(sharedViewModel.infoParam))
        HomeScreen(
            state = viewModel.viewState.value,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onNavigationRequested = {
                navController.safeNavigate(
                    when (it) {
                        is HomeContract.Effect.Navigation.ToDepartmentInfoScreen -> Screen.DepartmentInfo.pasParam(it.id)
                    }
                )
            }
        )
    }
}