package qveex.ru.more.presentation.navigation.paths

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import qveex.ru.more.presentation.navigation.Screen
import qveex.ru.more.presentation.navigation.defaultEnter
import qveex.ru.more.presentation.navigation.defaultExit
import qveex.ru.more.presentation.screens.department_info.DepartmentInfoContract
import qveex.ru.more.presentation.screens.department_info.DepartmentInfoViewModel
import qveex.ru.more.presentation.screens.department_info.DepartmentInfoScreen
import qveex.ru.more.utils.Constants

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.departmentInfoNav(navController: NavController) {
    composable(
        route = Screen.DepartmentInfo.route,
        enterTransition = { defaultEnter },
        exitTransition = { defaultExit }
    ) {
        val id = it.arguments?.getLong(Constants.INFO_ARGUMENT) ?: -1L
        val viewModel = hiltViewModel<DepartmentInfoViewModel>()
        DepartmentInfoScreen(
            state = viewModel.viewState.value,
            effectFlow = viewModel.effect,
            onEventSent = viewModel::setEvent,
            onNavigationRequested = {
                when (it) {
                    is DepartmentInfoContract.Effect.Navigation.PopBackStack -> navController.popBackStack()
                }
            }
        )
    }
}