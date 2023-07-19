package kg.ticode.shine.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.BottomAppBarButtonsModel

@Composable
fun BottomAppBar(
    buttons: List<BottomAppBarButtonsModel>,
    navController: NavHostController
) {
    var activeBtn by remember {
        mutableStateOf(0)
    }
    buttons.forEachIndexed { index, bottomAppBarButtonModel ->
        Button(onClick = {
            if (activeBtn != index) {
                navController.navigate(bottomAppBarButtonModel.route.route) {
                    popUpTo(0)
                }
            }
            activeBtn = index
        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {

                Icon(
                    imageVector = if (activeBtn == index) bottomAppBarButtonModel.activeIcon else bottomAppBarButtonModel.icon,
                    contentDescription = bottomAppBarButtonModel.title,
                    tint = if (activeBtn == index) PrimaryColor else PrimaryColor,
                    modifier = Modifier.size((bottomAppBarButtonModel.iconSize).dp)
                )


        }
    }
}