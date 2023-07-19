package kg.ticode.shine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.shine.screens.SplashScreen
import kg.ticode.shine.ui.theme.ShineTheme
import kg.ticode.shine.utils.CustomConstants

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomConstants.AUTH = FirebaseAuth.getInstance()
        setContent {
            ShineTheme {
                SplashScreen()
            }
        }
    }
}