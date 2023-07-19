package kg.ticode.shine.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import kg.ticode.shine.R
import kg.ticode.shine.dto.UserDto
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun EditProfileScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val appAuth = authViewModel.refAuth.authStateFlow.collectAsState()
    appAuth.value?.let {
        authViewModel.getUserById(it.id)
    }
    var user by remember {
        mutableStateOf<UserDto?>(null)
    }
    authViewModel.user.observe(lifecycleOwner) {
        user = it
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val fieldValues = remember {
            mutableStateListOf<String>()
        }
        val fields = listOf(
            "Имя",
            "Фамилия",
            "Эл.Почта",
            "Телефон номер",
            "Возрост"
        )
        LaunchedEffect(fields) {
            fieldValues.add(user?.firstName ?: fields[0])
            fieldValues.add(user?.lastName ?: fields[1])
            fieldValues.add(user?.email ?: fields[2])
            fieldValues.add(user?.phoneNumber ?: fields[3])
            fieldValues.add(user?.age.toString() ?: fields[4])
        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                Box(modifier = Modifier.size(124.dp), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.shine_logo_svg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(124.dp)
                            .clip(CircleShape)
                    )
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    ) {
                        drawRect(Color.Black.copy(.3f))
                    }
                    Button(onClick = {
                        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(64.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                fields.forEachIndexed { index, field ->
                    CustomEditText(text = {
                        fieldValues[index] = it
                    }, field)

                }
            }
            item {
                Button(
                    onClick = {
                        val userReq = RegistrationUserRequest(
                            age = if (fieldValues[4].isDigitsOnly()) fieldValues[4].toInt() else 0,
                            email = fieldValues[2],
                            firstName = fieldValues[0],
                            lastName = fieldValues[1],
                            password = "",
                            phoneNumber = fieldValues[3],
                            true
                        )
                        println("user: ${userReq}")
                        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
                        navController.navigate(ScreensRoute.ProfileScreen.route) {
                            popUpTo(0)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Обновить", color = Color.White, fontSize = 20.sp)

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CustomEditText(text: (String) -> Unit, fieldName: String) {

    var firstNameError by remember {
        mutableStateOf(false)
    }
    var firstNameSupportingText by remember {
        mutableStateOf("")
    }
    var myText by remember {
        mutableStateOf("")
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = myText,
        onValueChange = {
            text.invoke(it)
            myText = it
        },
        label = {
            Text(text = fieldName)
        },
        placeholder = {
            Text(text = fieldName)
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            containerColor = PrimaryColor,
            textColor = Color.White,
            placeholderColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        isError = firstNameError,
        supportingText = {
            Text(text = firstNameSupportingText)
        },
    )
}