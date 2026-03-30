package sky.programs.genderrevealgrid

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import sky.programs.genderrevealgrid.ui.GenderRevealApp
import sky.programs.genderrevealgrid.ui.theme.GenderRevealGridTheme

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppLocaleManager.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GenderRevealGridTheme(dynamicColor = false) {
                GenderRevealApp()
            }
        }
    }
}
