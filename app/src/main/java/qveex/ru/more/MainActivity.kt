package qveex.ru.more

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import qveex.ru.more.presentation.screens.MainScreen
import qveex.ru.more.ui.theme.Moretech5Theme

val LocalSharedViewModel = compositionLocalOf<SharedViewModel> { error("No SharedViewModel in this scope") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        setContent {
            Moretech5Theme {
                val sharedViewModel = hiltViewModel<SharedViewModel>()
                CompositionLocalProvider(LocalSharedViewModel provides sharedViewModel) {
                    MainScreen()
                }
            }
        }
    }
}