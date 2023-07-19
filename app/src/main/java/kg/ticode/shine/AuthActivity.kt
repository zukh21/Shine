package kg.ticode.shine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.shine.navigation.AuthNavigation
import kg.ticode.shine.ui.theme.ShineTheme
import kg.ticode.shine.utils.CustomConstants.AUTH

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AUTH = FirebaseAuth.getInstance()
        setContent {
            ShineTheme {
                val navController = rememberNavController()
                AuthNavigation(navController = navController)
            }
        }
    }
}