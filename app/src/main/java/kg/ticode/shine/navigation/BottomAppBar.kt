package kg.ticode.shine.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kg.ticode.shine.utils.BottomAppBarButtonSealedClass

@Composable
fun BottomAppBar(
    buttons: List<BottomAppBarButtonSealedClass>,
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = bottomAppBarButtonModel.icon,
                    contentDescription = bottomAppBarButtonModel.title,
                    tint = if (activeBtn == index) MaterialTheme.colorScheme.primary else Color.Gray,
                    modifier = Modifier.size(if (activeBtn == index) (bottomAppBarButtonModel.iconSize + (bottomAppBarButtonModel.iconSize / 2.5)).dp else (bottomAppBarButtonModel.iconSize).dp)
                )
                if (activeBtn == index) {
                    Text(
                        text = bottomAppBarButtonModel.title,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}