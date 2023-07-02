package kg.ticode.shine.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.profris.shine.R
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.utils.BottomAppBarButtonSealedClass


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity
    val window = activity.window
    showSystemUI(window)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name), fontFamily = FontFamily(
                Font(R.font.kaushan_script_regular)
            ), fontSize = 34.sp) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.secondary,
                navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                titleContentColor = MaterialTheme.colorScheme.secondary
            )
        )
    },) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Button(onClick = { navHostController.navigate(ScreensRoute.AuthorizationScreen.route) }) {
                Text(text = stringResource(id = R.string.authorization))
            }
        }
    }
}

private fun showSystemUI(window: Window) {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}
