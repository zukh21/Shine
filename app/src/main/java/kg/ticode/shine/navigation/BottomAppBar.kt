package kg.ticode.shine.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.model.BottomAppBarButtonsModel

@Composable
fun BottomAppBar(
    buttons: List<BottomAppBarButtonsModel>,
    navController: NavHostController,
    currentRoute: String?
) {
    var activeBtn by rememberSaveable {
        mutableStateOf(0)
    }
    println("currentRoute: $currentRoute")
    println("activeBtn: $activeBtn")
    buttons.forEachIndexed { index, bottomAppBarButtonModel ->
        if (currentRoute == bottomAppBarButtonModel.route.route && activeBtn != index){
            activeBtn = index
        }
        Button(onClick = {
            if (activeBtn != index) {
                navController.navigate(bottomAppBarButtonModel.route.route) {
                    popUpTo(0)
                }
            }
            println("index: $index")
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