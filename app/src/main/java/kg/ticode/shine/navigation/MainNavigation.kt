package kg.ticode.shine.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kg.ticode.shine.screens.EditProfileScreen
import kg.ticode.shine.screens.MainScreen
import kg.ticode.shine.screens.NotificationsScreen
import kg.ticode.shine.screens.ProfileScreen
import kg.ticode.shine.viewmodel.AuthViewModel

@Composable
fun MainNavigation(navController: NavHostController) {
    val authViewModel = hiltViewModel<AuthViewModel>()
    NavHost(navController = navController, startDestination = ScreensRoute.MainScreen.route) {
        composable(ScreensRoute.MainScreen.route) { MainScreen(navController) }
        composable(ScreensRoute.NotificationsScreen.route) { NotificationsScreen() }
        composable(ScreensRoute.ProfileScreen.route) { ProfileScreen(navController) }
        composable(ScreensRoute.EditProfileScreen.route) {
            EditProfileScreen(
                navController,
                authViewModel = authViewModel
            )
        }
    }
}