package kg.ticode.shine.navigation

sealed class ScreensRoute(val route: String) {
    object MainScreen : ScreensRoute("main_screen")
    object AuthorizationScreen : ScreensRoute("authorization_screen")
    object RegistrationScreen : ScreensRoute("registration_screen")
    object NotificationsScreen : ScreensRoute("notifications_screen")
    object ProfileScreen : ScreensRoute("profile_screen")
    object EditProfileScreen : ScreensRoute("edit_profile_screen")
    object CarDetailScreen : ScreensRoute("car_detail_screen")
    object AboutDeveloperScreen : ScreensRoute("about_developer_screen")
    object CarFavoriteScreen : ScreensRoute("car_favorite_screen")
    object AddNewCarScreen : ScreensRoute("add_new_car_screen")
    object AssignManagerRoleScreen : ScreensRoute("assign_manager_role_screen")
    object SplashScreen : ScreensRoute("splash_screen")
}