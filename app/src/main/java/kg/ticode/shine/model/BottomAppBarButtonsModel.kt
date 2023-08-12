package kg.ticode.shine.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.utils.CustomConstants.BOTTOM_APP_BAR_ICONS_SIZE

sealed class BottomAppBarButtonsModel(
    val title: String,
    val activeIcon: ImageVector,
    val icon: ImageVector,
    val iconSize: Int,
    val route: ScreensRoute
) {
    object Home : BottomAppBarButtonsModel(
        "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home,
        BOTTOM_APP_BAR_ICONS_SIZE,
        ScreensRoute.MainScreen
    )

    object Favorites : BottomAppBarButtonsModel(
        "Favorites",
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder,
        BOTTOM_APP_BAR_ICONS_SIZE,
        ScreensRoute.CarFavoriteScreen
    )

    object Profile : BottomAppBarButtonsModel(
        "Profile",
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle,
        BOTTOM_APP_BAR_ICONS_SIZE,
        ScreensRoute.ProfileScreen
    )
}
