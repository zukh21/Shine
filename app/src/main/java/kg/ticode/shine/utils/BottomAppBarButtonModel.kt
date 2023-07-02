package kg.ticode.shine.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.utils.CustomConstants.BOTTOM_APP_BAR_ICONS_SIZE

data class BottomAppBarButtonModel(val title: String, val icon: ImageVector, val iconSize: Int, val route: ScreensRoute)
sealed class BottomAppBarButtonSealedClass(val title: String, val icon: ImageVector, val iconSize: Int, val route: ScreensRoute){
    object Home: BottomAppBarButtonSealedClass("Home", Icons.Filled.Home, BOTTOM_APP_BAR_ICONS_SIZE, ScreensRoute.MainScreen)
    object Notifications: BottomAppBarButtonSealedClass("Notifications", Icons.Filled.Notifications, BOTTOM_APP_BAR_ICONS_SIZE, ScreensRoute.NotificationsScreen)
    object Profile: BottomAppBarButtonSealedClass("Profile", Icons.Filled.AccountCircle, BOTTOM_APP_BAR_ICONS_SIZE, ScreensRoute.ProfileScreen)
}
