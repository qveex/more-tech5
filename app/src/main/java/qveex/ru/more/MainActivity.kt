package qveex.ru.more

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import qveex.ru.more.presentation.screens.MainScreen
import qveex.ru.more.ui.theme.Moretech5Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Moretech5Theme {
                MainScreen()
            }
        }
    }
}