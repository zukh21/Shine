package kg.ticode.shine.screens

import android.annotation.SuppressLint
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kg.profris.shine.R
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.Blue
import kg.ticode.shine.ui.theme.Green
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedAnimatable")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class
)
@Composable
fun RegistrationScreen(navController: NavHostController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val authViewModel: AuthViewModel = hiltViewModel()
    var isError by remember {
        mutableStateOf(false)
    }
    val errorText by remember {
        mutableStateOf("")
    }
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var age by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordFocus by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.authorization),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(text = stringResource(id = R.string.first_name))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.first_name))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.first_name),
                    contentDescription = stringResource(id = R.string.first_name)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(text = stringResource(id = R.string.last_name))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.last_name))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.last_name),
                    contentDescription = stringResource(id = R.string.last_name)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = {
                Text(text = stringResource(id = R.string.phone_number))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.phone_number))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = stringResource(id = R.string.phone_number)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        var emailHasFocus by remember {
            mutableStateOf(false)
        }
        TextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.email))
            },
            leadingIcon = {
                if (!emailHasFocus) {
                    Icon(
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = stringResource(id = R.string.email)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.open_email),
                        contentDescription = stringResource(id = R.string.email)
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .onFocusEvent {
                    emailHasFocus = it.hasFocus
                }
        )
        var isAgeErrorValue by remember {
            mutableStateOf("")
        }
        var isAgeError by remember {
            mutableStateOf(false)
        }
        TextField(
            value = age,
            onValueChange = {
                if (it.isDigitsOnly() && it.isNotBlank()) {
                    age = it
                    if (it.toInt() <= 150) {
                        isAgeError = false
                    } else {
                        isAgeError = true
                        isAgeErrorValue = "Max age is 150!"
                    }
                } else {
                    age = ""
                }
            },
            label = {
                Text(text = stringResource(id = R.string.age))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.age))
            },
            leadingIcon = {
                if (age.isDigitsOnly() && age.isNotBlank()) {
                    when (age.toInt()) {
                        in 1..30 -> {
                            Icon(
                                painter = painterResource(id = R.drawable.man_for_age),
                                contentDescription = stringResource(id = R.string.age)
                            )
                        }

                        in 30..50 -> {
                            Icon(
                                painter = painterResource(id = R.drawable.middle_man_for_age),
                                contentDescription = stringResource(id = R.string.age)
                            )
                        }

                        in 50..Int.MAX_VALUE -> {
                            Icon(
                                painter = painterResource(id = R.drawable.old_man_for_age),
                                contentDescription = stringResource(id = R.string.age)
                            )
                        }
                    }
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.man_for_age),
                        contentDescription = stringResource(id = R.string.age)
                    )
                }

            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = isAgeError,
            supportingText = {
                if (isAgeError) {
                    Text(text = isAgeErrorValue)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        var passwordVisible by remember {
            mutableStateOf(false)
        }
        TextField(value = password,
            onValueChange = { password = it },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.password))
            },
            leadingIcon = {
                if (!passwordFocus) {
                    Icon(
                        painter = painterResource(id = R.drawable.close_lock),
                        contentDescription = stringResource(id = R.string.password)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.open_lock),
                        contentDescription = stringResource(id = R.string.password)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    if (!passwordVisible) {
                        Icon(
                            painter = painterResource(id = R.drawable.show_pass),
                            contentDescription = "password_visibility_icon",
                            Modifier.size(CustomConstants.AUTH_TEXT_FIELDS_ICON_SIZE.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.hide_pass),
                            contentDescription = "password_visibility_off_icon",
                            Modifier.size(CustomConstants.AUTH_TEXT_FIELDS_ICON_SIZE.dp)
                        )
                    }
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Exit)
            }),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .onFocusEvent {
                    passwordFocus = it.isFocused
                }
                .fillMaxWidth())
        var animated by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                var ageIsDigit by remember {
                    mutableStateOf(false)
                }
                if (age.isDigitsOnly() && age.isNotBlank()) {
                    ageIsDigit = true
                }
                val state = rememberCoroutineScope()
                Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            state.launch {
                                animated = true
                                delay(2000)
                                navController.navigate(ScreensRoute.PhoneNumberVerifyScreen.route)
                            }
//                            val user = RegistrationUserRequest(
//                                if (ageIsDigit) age.toInt() else 0,
//                                email,
//                                firstName,
//                                lastName,
//                                password,
//                                phoneNumber
//                            )
//                            state.launch {
//                                withContext(state.coroutineContext) {
//                                    authViewModel.registration(user)
//                                    authViewModel.responseIsSuccess.collectLatest {
//                                        if (it) {
//                                            animated = true
//                                            activePageRoute = ScreensRoute.PhoneNumberVerifyScreen
//                                        } else {
//                                            animated = false
//                                        }
//                                    }
//                                }
//                            }
                        },
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(containerColor = Green),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (!animated) {
                            Text(
                                text = stringResource(id = R.string.registration).uppercase(),
                                fontSize = 14.sp
                            )
                        }
                    }
                    androidx.compose.animation.AnimatedVisibility(
                        visible = animated,
                        enter = fadeIn() + slideInHorizontally(initialOffsetX = { -1000 })
                    ) {
                        Image(
                            modifier = Modifier
                                .size(size = 64.dp),
                            painter = painterResource(id = R.drawable.car_vector_full),
                            contentDescription = "Dog",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.already_have_an_account),
                        fontSize = 14.sp
                    )
                    TextButton(modifier = Modifier.padding(start = 8.dp), onClick = {
                        navController.navigate(ScreensRoute.AuthorizationScreen.route)
                    }) {
                        Text(
                            text = stringResource(id = R.string.authorization),
                            color = Blue,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }


    }
}