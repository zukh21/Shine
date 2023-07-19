package kg.ticode.shine.navigation

sealed class ScreensRoute(val route: String) {
    object MainScreen : ScreensRoute("main_screen")
    object AuthorizationScreen : ScreensRoute("authorization_screen")
    object RegistrationScreen : ScreensRoute("registration_screen")
    object NotificationsScreen : ScreensRoute("notifications_screen")
    object ProfileScreen : ScreensRoute("profile_screen")
    object EditProfileScreen : ScreensRoute("edit_profile_screen")
}