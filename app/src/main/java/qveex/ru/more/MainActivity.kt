package qveex.ru.more

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import qveex.ru.more.presentation.screens.MainScreen
import qveex.ru.more.ui.theme.Moretech5Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)

        setContent {
            Moretech5Theme {
                MainScreen()
            }
        }
    }
}