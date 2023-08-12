package kg.ticode.shine.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.UserUpdateDto
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.utils.CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND
import kg.ticode.shine.utils.CustomConstants.CIRCULAR_PROGRESS_SIZE
import kg.ticode.shine.utils.CustomConstants.TOP_BAR_TITLE_SIZE
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.UserViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    authViewModel.userIsAuthenticatedAndUserId(isTrue = {}, userId = {
        userViewModel.getUserById(it)
    })
    var user by remember {
        mutableStateOf<UserDto?>(null)
    }
    userViewModel.user.observe(lifecycleOwner) {
        user = it
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        userViewModel.updateUserResult.collect { result ->
            when (result) {
                ApiResult.Error -> {
                    isLoading = false
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }

                ApiResult.Loading -> {
                    isLoading = true
                    Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                }

                ApiResult.Success -> {
                    navController.popBackStack()
                    isLoading = false
                }

                ApiResult.Timeout -> {
                    isLoading = false
                    Toast.makeText(context, "timeout", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    Scaffold(topBar = { EditProfileScreenTopBar(navController) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (!isLoading) {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    item {
                        var firstName by remember {
                            mutableStateOf("")
                        }
                        TextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                errorIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                containerColor = PrimaryColor,
                                textColor = Color.White,
                                placeholderColor = Color.White,
                            ),
                            label = {
                                Text(text = "Имя", color = Color.White)
                            },
                            placeholder = {
                                Text(text = user?.firstName ?: "Имя", color = Color.White)
                            },
                            shape =
                            RoundedCornerShape(AUTH_TEXT_FIELDS_CORNER_ROUND.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        var lastName by remember {
                            mutableStateOf("")
                        }
                        TextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                errorIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                containerColor = PrimaryColor,
                                textColor = Color.White,
                                placeholderColor = Color.White,
                            ),
                            label = {
                                Text(text = "Фамилия", color = Color.White)
                            },
                            placeholder = {
                                Text(text = user?.lastName ?: "Фамилия", color = Color.White)
                            },
                            shape =
                            RoundedCornerShape(AUTH_TEXT_FIELDS_CORNER_ROUND.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        var email by remember {
                            mutableStateOf("")
                        }
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                errorIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                containerColor = PrimaryColor,
                                textColor = Color.White,
                                placeholderColor = Color.White,
                            ),
                            label = {
                                Text(text = "Эл.Почта", color = Color.White)
                            },
                            placeholder = {
                                Text(text = user?.email ?: "Эл.Почта", color = Color.White)
                            },
                            shape =
                            RoundedCornerShape(AUTH_TEXT_FIELDS_CORNER_ROUND.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        var phoneNumber by remember {
                            mutableStateOf("")
                        }
                        TextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                errorIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                containerColor = PrimaryColor,
                                textColor = Color.White,
                                placeholderColor = Color.White,
                            ),
                            label = {
                                Text(
                                    text = "Телефон номер",
                                    color = Color.White
                                )
                            },
                            placeholder = {
                                Text(
                                    text = user?.phoneNumber ?: "Телефон номер",
                                    color = Color.White
                                )
                            },
                            shape =
                            RoundedCornerShape(AUTH_TEXT_FIELDS_CORNER_ROUND.dp)
                        )
                        Button(
                            onClick = {
                                val userReq = UserUpdateDto(
                                    age = user?.age!!,
                                    email = email.ifEmpty { user!!.email },
                                    firstName = firstName.ifEmpty { user!!.firstName },
                                    lastName = lastName.ifEmpty { user!!.lastName },
                                    phoneNumber = phoneNumber.ifEmpty { user!!.phoneNumber },
                                    privacyPolicyAccepted = true,
                                    avatarUrl = user?.avatarUrl,
                                    id = user!!.id
                                )
                                userViewModel.updateUser(userUpdateDto = userReq)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = "Обновить", color = Color.White, fontSize = 20.sp)

                        }
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(CIRCULAR_PROGRESS_SIZE.dp),
                    color = PrimaryColor
                )
            }
        }
    }
}

@Composable
fun EditProfileScreenTopBar(navHostController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(CustomConstants.BOTTOM_APP_BAR_HEIGHT.dp)
            .background(PrimaryColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Text(
                text = "Редактировать профиль",
                fontSize = TOP_BAR_TITLE_SIZE.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}

fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String): File? {
    return try {
        val stream = context.contentResolver.openInputStream(uri)
        val file = File.createTempFile(fileName, "", context.cacheDir)
        org.apache.commons.io.FileUtils.copyInputStreamToFile(stream, file)
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}