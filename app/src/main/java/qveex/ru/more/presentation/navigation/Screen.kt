package qveex.ru.more.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import qveex.ru.more.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int? = null,
    val icon: ImageVector? = null
) {
    data object Home : Screen("home", R.string.title_home, Icons.Outlined.Home)
    data object Onboarding : Screen("onboarding")
    data object Departments : Screen("departments", R.string.title_departments, Icons.Outlined.List)
    data object DepartmentInfo : Screen("departmentInfo", R.string.title_info, Icons.Outlined.Info)
}
