package qveex.ru.more.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import qveex.ru.more.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int? = null,
    val icon: ImageVector? = null
) {
    data object Home : Screen("home", R.string.title_home)
    data object Departments : Screen("departments", R.string.title_departments)
    data object DepartmentInfo : Screen("departmentInfo")
}
