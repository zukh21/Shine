package kg.ticode.shine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kg.ticode.shine.screens.AuthorizationScreen
import kg.ticode.shine.screens.RegistrationScreen
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.ScreensViewModel

@Composable
fun AuthNavigation(navController: NavHostController) {
    val screensViewModel = hiltViewModel<ScreensViewModel>()
    val authViewModel = hiltViewModel<AuthViewModel>()
    val lifecycleOwner = LocalLifecycleOwner.current
    NavHost(
        navController = navController,
        startDestination = ScreensRoute.AuthorizationScreen.route
    ) {
        composable(ScreensRoute.AuthorizationScreen.route) { AuthorizationScreen(navController, authViewModel, lifecycleOwner) }
        composable(ScreensRoute.RegistrationScreen.route) { RegistrationScreen(navController, screensViewModel, authViewModel, lifecycleOwner) }
    }
}