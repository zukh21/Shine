package kg.ticode.shine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.shine.navigation.BottomAppBar
import kg.ticode.shine.navigation.Navigation
import kg.ticode.shine.ui.theme.ShineTheme
import kg.ticode.shine.utils.BottomAppBarButtonSealedClass

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShineTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = {
                    BottomAppBar {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val buttons = listOf(
                                BottomAppBarButtonSealedClass.Home,
                                BottomAppBarButtonSealedClass.Notifications,
                                BottomAppBarButtonSealedClass.Profile,
                            )
                            BottomAppBar(
                                buttons = buttons,
                                navController
                            )

                        }
                    }
                }) {
                    Navigation(navController)
                }
            }
        }
    }
}