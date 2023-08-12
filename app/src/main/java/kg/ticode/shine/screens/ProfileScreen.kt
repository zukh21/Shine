package kg.ticode.shine.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kg.ticode.shine.R
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.enums.UserRole
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.utils.CustomConstants.BUTTONS_CORNER_ROUND
import kg.ticode.shine.utils.CustomConstants.CIRCULAR_PROGRESS_SIZE
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    var isLoadingAvatar by remember {
        mutableStateOf(false)
    }
    var user by remember {
        mutableStateOf<UserDto?>(null)
    }

    var pickedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var avatarUpdated by remember {
        mutableStateOf(false)
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            pickedImageUri = it
            val file = it?.let { it1 -> createTmpFileFromUri(context, it1, "avatar") }
            if (file != null) {
                userViewModel.updateAvatar(file)
            }
        }
    )

    var isAuthenticated by remember {
        mutableStateOf(false)
    }
    var userId by remember {
        mutableStateOf(0L)
    }
    authViewModel.userIsAuthenticatedAndUserId(
        isTrue = {
            isAuthenticated = it
        },
        userId = { userId = it })
    var isLoading by remember {
        mutableStateOf(false)
    }
    var timeout by remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = { ProfileTopAppBar() }) { paddingValues ->
        if (isAuthenticated) {
            var firstName by remember {
                mutableStateOf(user?.firstName)
            }
            var lastName by remember {
                mutableStateOf(user?.lastName)
            }
            var phone by remember {
                mutableStateOf(user?.phoneNumber)
            }
            var image by remember {
                mutableStateOf(user?.avatarUrl)
            }
            LaunchedEffect(key1 = context) {
                userViewModel.getUserById(userId)
                userViewModel.getUserByIdEventChannel.collect { event ->
                    when (event) {
                        ApiResult.Error -> {
                            timeout = false
                            isLoading = false
                            authViewModel.logout()
                            Toast.makeText(
                                context,
                                "getUserByIdEventChannel error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        ApiResult.Loading -> {
                            isLoading = true
                            timeout = false
                            Toast.makeText(
                                context,
                                "getUserByIdEventChannel loading",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        ApiResult.Success -> {
                            userViewModel.user.observe(lifecycleOwner) {
                                if (it != null) {
                                    firstName = it.firstName
                                }
                                if (it != null) {
                                    lastName = it.lastName
                                }
                                if (it != null) {
                                    phone = it.phoneNumber
                                }
                                if (it != null) {
                                    image = it.avatarUrl ?: "image"
                                }
                                user = it
                            }
                            isLoading = false
                            timeout = false
                            Toast.makeText(
                                context,
                                "getUserByIdEventChannel success",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        ApiResult.Timeout -> {
                            timeout = true
                            isLoading = false
                            Toast.makeText(
                                context,
                                "getUserByIdEventChannel timeout",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            LaunchedEffect(key1 = context) {

                userViewModel.uploadAvatarEventChannel.collect { result ->
                    println("result profile: $result")
                    when (result) {
                        ApiResult.Error -> {
                            isLoadingAvatar = false
                            avatarUpdated = false
                            Toast.makeText(
                                context,
                                "Что-то пошло не так, попробуйте ещё раз!",
                                Toast.LENGTH_SHORT
                            ).show()
                            timeout = false
                        }

                        ApiResult.Loading -> {
                            isLoadingAvatar = true
                            avatarUpdated = false
                            timeout = false
                        }

                        ApiResult.Success -> {
                            isLoadingAvatar = false
                            avatarUpdated = true
                            timeout = false
                            Toast.makeText(
                                context,
                                "Аватар успешно обновлен!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        ApiResult.Timeout -> {
                            isLoadingAvatar = false
                            avatarUpdated = false
                            timeout = true
                            Toast.makeText(
                                context,
                                "Что-то пошло не так, попробуйте ещё раз!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(CIRCULAR_PROGRESS_SIZE.dp))
                }
            } else {
                if (timeout) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Что-то пошло не так! Попробуйте снова!")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            userViewModel.getUserById(userId)
                        }, shape = RoundedCornerShape(8.dp)) {
                            Text(text = "Повторить")
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        item {
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
                                        onClick = { navController.navigate(ScreensRoute.EditProfileScreen.route) },
                                        modifier = Modifier.align(
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
                                        Box(
                                            modifier = Modifier.size(136.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (!avatarUpdated) {
                                                Image(
                                                    painter = if (image != "image") rememberAsyncImagePainter(
                                                        image
                                                    ) else painterResource(
                                                        id = R.drawable.shine_logo_svg
                                                    ),
                                                    contentDescription = "avatar",
                                                    Modifier
                                                        .height(88.dp)
                                                        .width(88.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                            } else {
                                                AsyncImage(
                                                    model = pickedImageUri,
                                                    contentDescription = null,
                                                    Modifier
                                                        .height(88.dp)
                                                        .width(88.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop
                                                )
                                            }
                                            if (!isLoadingAvatar) {
                                                IconButton(
                                                    onClick = {
                                                        pickImageLauncher.launch("image/*")
                                                    },
                                                    colors = IconButtonDefaults.iconButtonColors(
                                                        containerColor = Color.Transparent
                                                    ),
                                                    modifier = Modifier.align(Alignment.BottomEnd)
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                                                        contentDescription = null,
                                                        tint = PrimaryColor
                                                    )
                                                }
                                            } else {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.fillMaxSize(
                                                        .9f
                                                    )
                                                )
                                            }
                                        }
                                        Column(
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "$firstName $lastName",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = "$phone",
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
                                    .fillMaxWidth(.9f)
                            ) {
                                Column {
                                    var userIsManager by remember {
                                        mutableStateOf(false)
                                    }
                                    authViewModel.userRole.observe(lifecycleOwner) {
                                        if (it == UserRole.ADMIN.name || it == UserRole.MANAGER.name || UserRole.USER.name == it) {
                                            userIsManager = true
                                        }
                                    }
                                    if (userIsManager) {
                                        Box(modifier = Modifier.padding(vertical = 12.dp)) {
                                            Card(
                                                onClick = {
                                                    navController.navigate(ScreensRoute.AddNewCarScreen.route)
                                                },
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
                                                        colors = CardDefaults.cardColors(
                                                            containerColor = PrimaryColor
                                                        ),
                                                        modifier = Modifier
                                                            .size(54.dp),
                                                    ) {
                                                        Box(
                                                            Modifier.fillMaxSize(),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Icon(
                                                                Icons.Filled.Add,
                                                                contentDescription = "Add",
                                                                tint = Color.White,
                                                                modifier = Modifier.size(24.dp)
                                                            )
                                                        }
                                                    }
                                                    Text(
                                                        text = stringResource(R.string.add_a_car),
                                                        fontSize = 20.sp,
                                                        modifier = Modifier.padding(horizontal = 8.dp),
                                                        color = Color.Black
                                                    )
                                                }
                                            }
                                        }
                                    }


                                    Box(modifier = Modifier.padding(vertical = 12.dp)) {
                                        Card(
                                            onClick = {
                                                navController.navigate(ScreensRoute.AssignManagerRoleScreen.route)
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
                                                            painter = painterResource(id = R.drawable.baseline_manage_accounts_24),
                                                            contentDescription = "назначить роль менеджера",
                                                            tint = Color.White,
                                                            modifier = Modifier.size(24.dp)
                                                        )
                                                    }
                                                }
                                                Text(
                                                    text = "Назначить роль менеджера",
                                                    fontSize = 20.sp,
                                                    modifier = Modifier.padding(horizontal = 8.dp),
                                                    color = Color.Black
                                                )
                                            }
                                        }
                                    }
                                    Box(modifier = Modifier.padding(vertical = 12.dp)) {
                                        Card(
                                            onClick = { navController.navigate(ScreensRoute.AboutDeveloperScreen.route) },
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
                                                authViewModel.userIsAuthenticatedAndUserId(
                                                    isTrue = {
                                                        isAuthenticated = it
                                                    },
                                                    userId = { userId = it })
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
                            navController.navigate(ScreensRoute.AuthorizationScreen.route)
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

}

@Composable
fun ProfileTopAppBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(CustomConstants.BOTTOM_APP_BAR_HEIGHT.dp)
            .background(PrimaryColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.profile),
            Modifier.padding(horizontal = 12.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}