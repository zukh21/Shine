package kg.ticode.shine.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import kg.ticode.shine.MainActivity
import kg.ticode.shine.R
import kg.ticode.shine.model.RegistrationUserRequest
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.Blue
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.utils.CustomToast
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.ScreensViewModel
import kotlinx.coroutines.delay
import okhttp3.internal.notify

@SuppressLint("UnrememberedAnimatable")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class
)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: ScreensViewModel,
    authViewModel: AuthViewModel,
    lifecycleOwner: LifecycleOwner
) {


    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current
    val displayHeight = configuration.screenHeightDp
    var animated by remember {
        mutableStateOf(false)
    }
    var firstName by rememberSaveable {
        mutableStateOf("")
    }
    var lastName by rememberSaveable {
        mutableStateOf("")
    }
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var age by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var passwordFocus by rememberSaveable {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var to by remember {
            mutableStateOf(false)
        }
        authViewModel.timeout.observe(lifecycleOwner) { timeout ->
            if (timeout != null && timeout) {
                animated = false
                to = true
                authViewModel.clearTimeoutException()
            }
        }
        if (to){
            Box(Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                Snackbar(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Что-то пошло не так! Попробуйте ещё раз.")
                }
            }
            LaunchedEffect(true){
                delay(3000)
                to = false
            }
        }
        Text(
            text = stringResource(id = R.string.registration),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        var firstNameError by remember {
            mutableStateOf(false)
        }
        var firstNameErrorText by remember {
            mutableStateOf("")
        }
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
            isError = firstNameError,
            supportingText = {
                if (firstNameError) {
                    Text(text = firstNameErrorText)
                }
                if (firstName.isNotBlank()) {
                    firstNameError = false
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        var lastNameError by remember {
            mutableStateOf(false)
        }
        var lastNameErrorText by remember {
            mutableStateOf("")
        }
        TextField(
            value = lastName,
            onValueChange = {
                lastName = it
                if (lastName.isNotBlank()) {
                    lastNameError = false
                }
            },
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
            isError = lastNameError,
            supportingText = {
                if (lastNameError) {
                    Text(text = lastNameErrorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        var phoneError by remember {
            mutableStateOf(false)
        }
        var phoneErrorText by remember {
            mutableStateOf("")
        }
        TextField(
            value = phoneNumber,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    if (phoneNumber.length > 16 || phoneNumber.length < 9) {
                        phoneError = true
                        animated = false
                        phoneErrorText = "Номер телефона должен быть меньше 16" +
                                " и больше 9"
                    } else {
                        phoneError = false
                    }
                    phoneNumber = it
                }
            },
            label = {
                Text(text = stringResource(id = R.string.phone_number))
            },
            placeholder = {
                Text(text = "996007112233")
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
                    Text(text = phoneErrorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        phoneError = false
                    }
                }
        )
        var emailHasFocus by remember {
            mutableStateOf(false)
        }
        var emailError by remember {
            mutableStateOf(false)
        }
        var emailErrorText by remember {
            mutableStateOf("")
        }
        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = "@" !in email
                if (emailError) {
                    animated = false
                    emailErrorText = "Напишите правильная Эл.почта"
                }

            },
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
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text(text = emailErrorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.hasFocus) {
                        emailError = false
                        emailHasFocus = it.hasFocus
                    }
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
                        animated = false
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
        var passwordError by remember {
            mutableStateOf(false)
        }
        var passwordErrorText by remember {
            mutableStateOf("")
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
            isError = passwordError == password.length < 8,
            supportingText = {
                if (passwordError) {
                    Text(text = passwordErrorText)
                }
            },
            modifier = Modifier
                .padding(vertical = 5.dp)
                .onFocusEvent {
                    passwordFocus = it.isFocused
                }
                .fillMaxWidth())


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
                Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (!firstNameError && !lastNameError && !phoneError && !emailError && !isAgeError && !passwordError) {
                                if (firstName.isNotBlank() && lastName.isNotBlank()
                                    && phoneNumber.isNotBlank() && email.isNotBlank()
                                    && age.isNotBlank() && password.isNotBlank()
                                    && phoneNumber.isNotBlank()
                                ) {
                                    animated = true
                                    val user = RegistrationUserRequest(
                                        age.toInt(),
                                        email,
                                        firstName,
                                        lastName,
                                        password,
                                        phoneNumber,
                                        true
                                    )
                                    authViewModel.registration(
                                        (user)
                                    )
                                    authViewModel.responseIsSuccess.observe(lifecycleOwner) {
                                        if (it) {
                                            val activity = context as Activity
                                            activity.startActivity(
                                                Intent(
                                                    context,
                                                    MainActivity::class.java
                                                )
                                            )
                                            activity.finish()
                                        } else {
                                            animated = false
                                        }
                                    }

                                } else {
                                    when {
                                        firstName.isBlank() -> {
                                            animated = false
                                            firstNameError = true
                                            firstNameErrorText = "Имя должно быть заполнено"
                                        }

                                        lastName.isBlank() -> {
                                            animated = false
                                            lastNameError = true
                                            lastNameErrorText = "Фамилия должно быть заполнено"
                                        }

                                        age.isBlank() -> {
                                            animated = false
                                            isAgeError = true
                                            isAgeErrorValue = "Возраст должен быть заполнено"
                                        }

                                        phoneNumber.isBlank() -> {
                                            animated = false
                                            phoneError = true
                                            phoneErrorText =
                                                "Номер телефона должен быть меньше 16 и больше 9"
                                        }

                                        email.isBlank() -> {
                                            animated = false
                                            emailError = true
                                            emailErrorText = "Эл.почта должен быть заполнено"
                                        }

                                        password.isBlank() -> {
                                            animated = false
                                            passwordError = true
                                            passwordErrorText = "Пароль должен быть заполнено"
                                        }
                                    }
                                }
                            } else {
                                animated = false
                            }

                        },
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (!animated) {
                            Text(
                                text = stringResource(id = R.string.registration).uppercase(),
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