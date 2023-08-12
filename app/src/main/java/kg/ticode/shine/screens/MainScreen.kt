package kg.ticode.shine.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.ticode.shine.R
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants.BOTTOM_APP_BAR_HEIGHT
import kg.ticode.shine.viewmodel.CarsViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    carsViewModel: CarsViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomTopAppBar()
        CarsScreen(navController, carsViewModel)
    }


}

@Composable
fun CustomTopAppBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(BOTTOM_APP_BAR_HEIGHT.dp)
            .background(PrimaryColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            Modifier.padding(horizontal = 12.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

