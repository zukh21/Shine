package kg.ticode.shine.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kg.ticode.shine.R
import kg.ticode.shine.MainActivity
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.ScreensViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val activity = context as Activity
    val screensViewModel: ScreensViewModel = viewModel()
    LaunchedEffect(key1 = true) {
        screensViewModel.isLoading.collectLatest {
            if (it) {
                activity.startActivity(Intent(context, MainActivity::class.java))
                activity.finish()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White
            ), contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.shine_logo_svg),
                contentDescription = "shine logo",
                modifier = Modifier.size(136.dp)
            )
        }
    }
}
