package kg.ticode.shine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kg.ticode.shine.screens.AuthorizationScreen
import kg.ticode.shine.screens.MainScreen
import kg.ticode.shine.screens.NotificationsScreen
import kg.ticode.shine.screens.SplashScreen
import kg.ticode.shine.screens.PhoneNumberVerifyScreen
import kg.ticode.shine.screens.ProfileScreen
import kg.ticode.shine.screens.RegistrationScreen

sealed class ScreensRoute(val route: String) {
    object SplashScreen : ScreensRoute("splash_screen")
    object MainScreen : ScreensRoute("main_screen")
    object PhoneNumberVerifyScreen : ScreensRoute("phone_number_verify_screen")
    object AuthorizationScreen : ScreensRoute("authorization_screen")
    object RegistrationScreen : ScreensRoute("registration_screen")
    object NotificationsScreen : ScreensRoute("notifications_screen")
    object ProfileScreen : ScreensRoute("profile_screen")
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ScreensRoute.MainScreen.route) {
        composable(ScreensRoute.SplashScreen.route) { SplashScreen() }
        composable(ScreensRoute.MainScreen.route) { MainScreen(navController) }
        composable(ScreensRoute.PhoneNumberVerifyScreen.route){ PhoneNumberVerifyScreen()}
        composable(ScreensRoute.AuthorizationScreen.route){ AuthorizationScreen(navController)}
        composable(ScreensRoute.RegistrationScreen.route){ RegistrationScreen(navController)}
        composable(ScreensRoute.NotificationsScreen.route){ NotificationsScreen()}
        composable(ScreensRoute.ProfileScreen.route){ ProfileScreen()}
    }
}