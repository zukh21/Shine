package kg.ticode.shine.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import kg.ticode.shine.AuthActivity
import kg.ticode.shine.R
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants.BUTTONS_CORNER_ROUND
import kg.ticode.shine.viewmodel.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity
    val configuration = LocalConfiguration.current
    val displayHeight = configuration.screenHeightDp
    val authViewModel = hiltViewModel<AuthViewModel>()
    val lifecycleOwner = LocalLifecycleOwner.current
    var firstName by remember {
        mutableStateOf("first name")
    }
    authViewModel.authenticatedUserId.observe(lifecycleOwner) {
        if (it != null && it != 0L) {
            authViewModel.getUserById(it)
        }
    }

    authViewModel.user.observe(lifecycleOwner) {
        firstName = it.firstName
    }

    var favoritesIsNotNull by remember {
        mutableStateOf(false)
    }


    var isAuthenticated by remember {
        mutableStateOf(false)
    }

    authViewModel.userIsAuthenticated.observe(lifecycleOwner) {
        println("authenticated: $it")
        isAuthenticated = it
    }
    if (isAuthenticated) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            stickyHeader {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(size = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(24.dp)
                    ) {
                        IconButton(
                            onClick = { navController.navigate(ScreensRoute.EditProfileScreen.route) }, modifier = Modifier.align(
                                Alignment.TopEnd
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.uil_edit),
                                contentDescription = "",
                                tint = PrimaryColor
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter("https://cdn.dribbble.com/users/2530857/screenshots/5448238/immamul-day-2.png"),
                                contentDescription = "avatar",
                                Modifier
                                    .height(88.dp)
                                    .width(88.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Манас Жакып уулу",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "+996-770-##-##-##",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .height((displayHeight / 2).dp)
                        .fillMaxWidth(.9f)
                ) {
                    Column {
                        Box(modifier = Modifier.padding(vertical = 12.dp)) {
                            Card(
                                onClick = { favoritesIsNotNull = !favoritesIsNotNull },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(10.dp),
                                        colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                                        modifier = Modifier
                                            .size(54.dp),
                                    ) {
                                        Box(
                                            Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                if (favoritesIsNotNull) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                                contentDescription = "Favorite",
                                                tint = Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Избранное",
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier.padding(vertical = 12.dp)) {
                            Card(
                                onClick = { },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(10.dp),
                                        colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                                        modifier = Modifier
                                            .size(54.dp),
                                    ) {
                                        Box(
                                            Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_developer_mode_24),
                                                contentDescription = "Developer",
                                                tint = Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Разработчик",
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier.padding(vertical = 12.dp)) {
                            Card(
                                onClick = {
                                    authViewModel.logout()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(10.dp),
                                        colors = CardDefaults.cardColors(containerColor = PrimaryColor),
                                        modifier = Modifier
                                            .size(54.dp),
                                    ) {
                                        Box(
                                            Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Filled.ExitToApp,
                                                contentDescription = "Exit",
                                                tint = Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Выйти из системы",
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        color = Color.Black
                                    )
                                }
                            }
                        }


                    }
                }

            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Вы не вошли в систему! Нажмите кнопку ниже, чтобы войти в систему",
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        activity.startActivity(Intent(context, AuthActivity::class.java))
                        activity.finish()
                    },
                    Modifier
                        .fillMaxWidth(.8f)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(BUTTONS_CORNER_ROUND.dp)
                ) {
                    Text(text = "Войти в систему")
                }
            }
        }
    }


}