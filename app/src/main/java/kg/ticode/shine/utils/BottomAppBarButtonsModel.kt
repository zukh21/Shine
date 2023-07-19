package kg.ticode.shine.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.utils.CustomConstants.BOTTOM_APP_BAR_ICONS_SIZE

sealed class BottomAppBarButtonsModel(
    val title: String,
    val activeIcon: ImageVector,
    val icon: ImageVector,
    val iconSize: Int,
    val route: ScreensRoute,
    iconImage: String? = null
) {
    object Home : BottomAppBarButtonsModel(
        "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home,
        BOTTOM_APP_BAR_ICONS_SIZE,
        ScreensRoute.MainScreen
    )

    object Notifications : BottomAppBarButtonsModel(
        "Notifications",
        Icons.Filled.Notifications,
        Icons.Outlined.Notifications,
        BOTTOM_APP_BAR_ICONS_SIZE,
        ScreensRoute.NotificationsScreen
    )

    object Profile : BottomAppBarButtonsModel(
        "Profile",
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle,
        BOTTOM_APP_BAR_ICONS_SIZE,
        ScreensRoute.ProfileScreen,
        "https://cdn.dribbble.com/users/2530857/screenshots/5448238/immamul-day-2.png"
    )
}
