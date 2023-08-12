package kg.ticode.shine.screens

import android.app.Activity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.ticode.shine.R
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.networkAvailable
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.CarsViewModel
import kg.ticode.shine.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    carsViewModel: CarsViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    var networkAvailability by remember {
        mutableStateOf(true)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var timeout by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        networkAvailability = networkAvailable(context)
        if (networkAvailability) {
            carsViewModel.getAllCarsEventChannel.collectLatest { event ->
                when (event) {
                    ApiResult.Error -> {
                        isError = true
                        timeout = false
                        isLoading = false
                    }

                    ApiResult.Loading -> {
                        isError = false
                        timeout = false
                        isLoading = true
                    }

                    is ApiResult.Success -> {
                        navController.navigate(ScreensRoute.MainScreen.route) {
                            popUpTo(0)
                        }
                        isError = false
                        timeout = false
                        isLoading = false
                    }

                    ApiResult.Timeout -> {
                        isError = false
                        timeout = true
                        isLoading = false
                    }
                }
            }
        }
    }
    val infiniteTransition = rememberInfiniteTransition()
    val angle: Float by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Reverse)
    )
    if (networkAvailability) {
        if (!timeout) {
            if (!isError) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.White
                        ), contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(164.dp), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.shine_circle),
                            contentDescription = "shine logo",
                            modifier = Modifier
                                .fillMaxSize()
                                .rotate(angle)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.shine_car_with_sh),
                            contentDescription = "shine logo",
                            modifier = Modifier.size((164 / 2).dp)
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(horizontal = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Ошибка".uppercase(), fontWeight = FontWeight.Bold, color = Color.White)
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                            Text(
                                text = "Что-то пошло не так, повторите попытку позже.",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp, color = Color.White
                            )
                            Button(
                                onClick = {
                                    val activity = context as Activity
                                    activity.finish()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = PrimaryColor
                                ),
                                shape = RoundedCornerShape(8),
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Text(text = "ОК".uppercase())
                            }
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White
                    ), contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Ошибка соединения".uppercase(), fontWeight = FontWeight.Bold, color = Color.White)
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.White)
                        Text(
                            text = "Время ожидания подключения к СЕРВЕРУ истекло.",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp, color = Color.White
                        )
                        Text(
                            text = "Пожалуйста, проверьте ИНТЕРНЕТ и повторите попытку.",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp, color = Color.White
                        )
                        Button(
                            onClick = { carsViewModel.getAllCars() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = PrimaryColor
                            ),
                            shape = RoundedCornerShape(8),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(text = "Повторить".uppercase())
                        }
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "У вас нет подключения к Интернету, пожалуйста, подключите его и вернитесь, приложение работает только с Интернетом, спасибо за понимание.",
                Modifier.padding(12.dp)
            )
        }
    }
}
