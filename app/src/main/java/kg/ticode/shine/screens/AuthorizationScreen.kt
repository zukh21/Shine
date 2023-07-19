package kg.ticode.shine.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import kg.ticode.shine.MainActivity
import kg.ticode.shine.R
import kg.ticode.shine.model.AuthorizationUserRequestEmail
import kg.ticode.shine.model.AuthorizationUserRequestPhone
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.Blue
import kg.ticode.shine.ui.theme.Green
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthorizationScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    lifecycleOwner: LifecycleOwner
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var isLoading by remember {
        mutableStateOf(false)
    }
    var phoneError by remember {
        mutableStateOf(false)
    }
    val errorText by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var phoneNumberSupportingText by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("")
    }
    var emailSupportingText by remember {
        mutableStateOf("")
    }
    var passwordError by remember {
        mutableStateOf(false)
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordSupportingText by remember {
        mutableStateOf("")
    }
    var passwordFocus by remember {
        mutableStateOf(false)
    }
    var to by remember {
        mutableStateOf(false)
    }
    authViewModel.timeout.observe(lifecycleOwner) { timeout ->
        if (timeout != null && timeout) {
            isLoading = false
            to = true
            authViewModel.clearTimeoutException()
        }
    }
    if (to) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(), contentAlignment = Alignment.BottomCenter
        ) {
            Snackbar(modifier = Modifier.padding(12.dp)) {
                Text(text = "Что-то пошло не так! Попробуйте ещё раз.")
            }
        }
        LaunchedEffect(true) {
            delay(3000)
            to = false
        }
    }
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var isPhone by remember { mutableStateOf(true) }
        Text(
            text = stringResource(id = R.string.authorization),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        if (isPhone) {
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
                isError = phoneError,
                supportingText = {
                    if (phoneError) {
                        Text(text = phoneNumberSupportingText)
                    }
                },
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.hasFocus) {
                            phoneError = false
                        }
                    }
            )
        } else {
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
                    Icon(
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = stringResource(id = R.string.email)
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
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                isError = emailError,
                supportingText = {
                    if (emailError) {
                        Text(text = emailSupportingText)
                    }
                },
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.hasFocus) {
                            emailError = false
                        }
                    }
            )
        }
        var passwordVisible by remember {
            mutableStateOf(false)
        }
        TextField(value = password,
            onValueChange = {
                password = it
                if (password.isNotBlank()) {
                    passwordError = false
                }
            },
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
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text(text = passwordSupportingText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .onFocusEvent {
                    passwordFocus = it.isFocused

                    if (it.hasFocus) {
                        passwordError = false
                    }

                }
                .fillMaxWidth())
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Authorization with")
            Switch(
                checked = isPhone,
                onCheckedChange = { isPhone = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Green,
                    uncheckedThumbColor = Blue,
                    checkedTrackColor = Color.White,
                    uncheckedTrackColor = Color.White,
                    checkedBorderColor = Green,
                    uncheckedBorderColor = Blue,
                ), thumbContent = {
                    if (!isPhone) {
                        Icon(
                            painter = painterResource(id = R.drawable.phone),
                            contentDescription = "phone",
                            Modifier.size(12.dp),
                            tint = Color.White
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "email",
                            Modifier.size(12.dp),
                            tint = Color.White
                        )
                    }

                })
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Button(
                    onClick = {
                        isLoading = true
                        println("isLoading: $isLoading")
                        if (phoneNumber.isNotBlank() && password.isNotBlank() || email.isNotBlank() && password.isNotBlank()) {
                            isLoading = true
                            if (isPhone) {
                                isLoading = true
                                val user = AuthorizationUserRequestPhone(
                                    password, phoneNumber
                                )
                                authViewModel.authorizationWithPhone(user)
                                authViewModel.responseIsSuccess.observe(lifecycleOwner) { isSuccess ->
                                    if (isSuccess) {
                                        val activity = context as Activity
                                        activity.startActivity(
                                            Intent(
                                                context,
                                                MainActivity::class.java
                                            )
                                        )
                                        activity.finish()
                                    } else {
                                        authViewModel.errorBody.observe(lifecycleOwner) {
                                            phoneNumberSupportingText = it
                                        }
                                    }
                                    phoneError = !isSuccess
                                    isLoading = isSuccess
                                }
                            } else {
                                isLoading = true
                                val user = AuthorizationUserRequestEmail(
                                    email, password
                                )
                                authViewModel.authorizationWithEmail(user)
                                authViewModel.responseIsSuccess.observe(lifecycleOwner) { isSuccess ->
                                    if (isSuccess) {
                                        val activity = context as Activity
                                        activity.startActivity(
                                            Intent(
                                                context,
                                                MainActivity::class.java
                                            )
                                        )
                                        activity.finish()
                                    } else {
                                        authViewModel.errorBody.observe(lifecycleOwner){
                                            emailSupportingText = it
                                        }
                                    }
                                    emailError = !isSuccess
                                    isLoading = isSuccess
                                }
                            }
                        } else {
                            isLoading = false
                            if (isPhone) {
                                if (phoneNumber.isBlank()) {
                                    phoneError = true
                                    phoneNumberSupportingText =
                                        "Номер телефона должен быть заполнен!"
                                } else {
                                    phoneError = false
                                }

                            } else {
                                if (email.isBlank()) {
                                    emailError = true
                                    emailSupportingText = "Эл.почта должна быть заполнена!"
                                } else {
                                    emailError = false
                                }
                            }
                            if (password.isBlank()) {
                                passwordError = true
                                passwordSupportingText = "Пароль должен быть заполнен!"
                            } else {
                                passwordError = false
                            }


                        }
                        println("isLoading: $isLoading")
                    },
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (!isLoading) {
                        Text(
                            text = stringResource(id = R.string.authorization).uppercase(),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }
            }
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.do_not_have_an_account),
                        fontSize = 14.sp
                    )
                    TextButton(modifier = Modifier.padding(start = 8.dp), onClick = {
                        navHostController.navigate(ScreensRoute.RegistrationScreen.route) {
                            popUpTo(ScreensRoute.MainScreen.route)
                        }
                    }) {
                        Text(
                            text = stringResource(id = R.string.registration),
                            color = Blue,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }


    }
    BackHandler {
        val activity = context as Activity
        activity.startActivity(
            Intent(
                context,
                MainActivity::class.java
            )
        )
        activity.finish()
    }
}