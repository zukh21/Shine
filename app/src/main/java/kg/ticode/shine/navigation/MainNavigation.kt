package kg.ticode.shine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kg.ticode.shine.screens.AboutDeveloperScreen
import kg.ticode.shine.screens.AddNewCarScreen
import kg.ticode.shine.screens.AssignManagerRoleScreen
import kg.ticode.shine.screens.AuthorizationScreen
import kg.ticode.shine.screens.CarDetailScreen
import kg.ticode.shine.screens.CarFavoritesScreen
import kg.ticode.shine.screens.CarFilterScreen
import kg.ticode.shine.screens.EditProfileScreen
import kg.ticode.shine.screens.MainScreen
import kg.ticode.shine.screens.NotificationsScreen
import kg.ticode.shine.screens.ProfileScreen
import kg.ticode.shine.screens.RegistrationScreen
import kg.ticode.shine.screens.SplashScreen
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.CarsViewModel
import kg.ticode.shine.viewmodel.UserViewModel

@Composable
fun MainNavigation(navController: NavHostController) {
    val authViewModel = hiltViewModel<AuthViewModel>()
    val userViewModel = hiltViewModel<UserViewModel>()
    val carsViewModel: CarsViewModel = viewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    NavHost(navController = navController, startDestination = ScreensRoute.SplashScreen.route) {
        composable(ScreensRoute.MainScreen.route) { MainScreen(navController, carsViewModel) }
        composable(ScreensRoute.NotificationsScreen.route) { NotificationsScreen() }
        composable(ScreensRoute.ProfileScreen.route) { ProfileScreen(navController, lifecycleOwner, authViewModel, userViewModel = userViewModel) }
        composable(ScreensRoute.CarDetailScreen.route) { CarDetailScreen(carsViewModel, authViewModel, navController) }
        composable(ScreensRoute.CarFavoriteScreen.route) { CarFavoritesScreen(carsViewModel, navController) }
        composable(ScreensRoute.AboutDeveloperScreen.route) { AboutDeveloperScreen(navController) }
        composable(ScreensRoute.AssignManagerRoleScreen.route) { AssignManagerRoleScreen(navController) }
        composable(ScreensRoute.AddNewCarScreen.route) { AddNewCarScreen(navController) }
        composable(ScreensRoute.SplashScreen.route) { SplashScreen(carsViewModel = carsViewModel,authViewModel, navController) }
        composable(ScreensRoute.AuthorizationScreen.route) { AuthorizationScreen(navController, authViewModel) }
        composable(ScreensRoute.RegistrationScreen.route) { RegistrationScreen(navController, authViewModel, lifecycleOwner) }
        composable(ScreensRoute.EditProfileScreen.route) {
            EditProfileScreen(
                authViewModel = authViewModel,
                navController,
                userViewModel
            )
        }
    }
}