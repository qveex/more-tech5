package qveex.ru.more

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import qveex.ru.more.presentation.screens.MainScreen
import qveex.ru.more.ui.theme.Moretech5Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        setContent {
            Moretech5Theme {
                MainScreen()
            }
        }
    }
}