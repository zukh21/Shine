package kg.ticode.shine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.shine.navigation.BottomAppBar
import kg.ticode.shine.navigation.MainNavigation
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.ShineTheme
import kg.ticode.shine.utils.BottomAppBarButtonsModel
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.utils.CustomConstants.BOTTOM_APP_BAR_HEIGHT

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomConstants.AUTH = FirebaseAuth.getInstance()
        setContent {
            ShineTheme {
                Surface {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    Scaffold(bottomBar = {
                            if (currentRoute != ScreensRoute.EditProfileScreen.route){
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(BOTTOM_APP_BAR_HEIGHT.dp), contentAlignment = Alignment.Center){
                                    Card(Modifier.fillMaxWidth(.8f), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(6.dp)) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            val buttons = listOf(
                                                BottomAppBarButtonsModel.Home,
                                                BottomAppBarButtonsModel.Notifications,
                                                BottomAppBarButtonsModel.Profile,
                                            )
                                            BottomAppBar(
                                                buttons = buttons,
                                                navController
                                            )


                                        }}
                                }
                            }
                    }) {
                        MainNavigation(navController)
                    }
                }
            }
        }
    }
}