package kg.ticode.shine.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.presentation.RegistrationFormEvent
import kg.ticode.shine.ui.theme.Blue
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.utils.CustomConstants.CIRCULAR_PROGRESS_SIZE
import kg.ticode.shine.utils.CustomConstants.REGISTRATION_TEXT_FIELDS_ERROR_SIZE
import kg.ticode.shine.viewmodel.AuthViewModel

@SuppressLint("UnrememberedAnimatable")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class
)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    lifecycleOwner: LifecycleOwner
) {


    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val state = authViewModel.state
    var animated by remember {
        mutableStateOf(false)
    }

    var passwordFocus by rememberSaveable {
        mutableStateOf(false)
    }
    var timeoutError by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = context) {
        authViewModel.validationEvents.collect { event ->
            when (event) {
                ApiResult.Success -> {
                    authViewModel.state = authViewModel.state.copy(
                        firstNameError = null,
                        lastNameError = null,
                        phoneNumberError = null,
                        emailError = null,
                        ageError = null,
                        passwordError = null
                    )
                    navController.navigate(ScreensRoute.ProfileScreen.route){
                        popUpTo(0)
                    }
                }

                ApiResult.Error -> {
                    animated = false
                }

                ApiResult.Timeout -> {
                    animated = false
                    timeoutError = true
                }

                ApiResult.Loading -> Unit
            }
        }
    }
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (timeoutError) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Snackbar(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Что-то пошло не так! Попробуйте ещё раз.")
                }
            }

        }
        Text(
            text = stringResource(id = R.string.registration),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        TextField(
            value = state.firstName,
            onValueChange = { authViewModel.onEvent(RegistrationFormEvent.FirstNameChanged(it)) },
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
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = state.firstNameError != null,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        if (state.firstNameError != null) {
            Text(
                text = state.firstNameError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                 fontSize = REGISTRATION_TEXT_FIELDS_ERROR_SIZE.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        TextField(
            value = state.lastName,
            onValueChange = {
                authViewModel.onEvent(RegistrationFormEvent.LastNameChanged(it))
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
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            isError = state.lastNameError != null,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        if (state.lastNameError != null) {
            Text(
                text = state.lastNameError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                fontSize = REGISTRATION_TEXT_FIELDS_ERROR_SIZE.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        TextField(
            value = state.phoneNumber,
            onValueChange = {
                authViewModel.onEvent(RegistrationFormEvent.PhoneNumberChanged(it))
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
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
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
            isError = state.phoneNumberError != null,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        if (state.phoneNumberError != null) {
            Text(
                text = state.phoneNumberError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                fontSize = REGISTRATION_TEXT_FIELDS_ERROR_SIZE.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        var emailHasFocus by remember {
            mutableStateOf(false)
        }
        TextField(
            value = state.email,
            onValueChange = {
                authViewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
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
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
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
            isError = state.emailError != null,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.hasFocus) {
                        emailHasFocus = it.hasFocus
                    }
                }
        )
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                fontSize = REGISTRATION_TEXT_FIELDS_ERROR_SIZE.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        TextField(
            value = if (state.age != 0) state.age.toString() else "",
            onValueChange = {
                if (it.isDigitsOnly() && it.isNotBlank()) {
                    authViewModel.onEvent(RegistrationFormEvent.AgeChanged(it.toInt()))
                }
                if (it.isBlank()) {
                    authViewModel.onEvent(RegistrationFormEvent.AgeChanged(0))
                }
            },
            label = {
                Text(text = stringResource(id = R.string.age))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.age))
            },
            leadingIcon = {
                if (state.age.toString().isNotBlank() && state.age != 0) {
                    when (state.age) {
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
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
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
            isError = state.ageError != null,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        if (state.ageError != null) {
            Text(
                text = state.ageError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                fontSize = REGISTRATION_TEXT_FIELDS_ERROR_SIZE.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        var passwordVisible by remember {
            mutableStateOf(false)
        }
        TextField(value = state.password,
            onValueChange = {
                authViewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
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
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(CustomConstants.AUTH_TEXT_FIELDS_CORNER_ROUND),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Exit)
            }),
            isError = state.passwordError != null,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .onFocusEvent {
                    passwordFocus = it.isFocused
                }
                .fillMaxWidth()
        )
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                fontSize = REGISTRATION_TEXT_FIELDS_ERROR_SIZE.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            animated = true
                            authViewModel.onEvent(RegistrationFormEvent.Registration)
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
                        authViewModel.state = authViewModel.state.copy(
                            firstNameError = null,
                            lastNameError = null,
                            phoneNumberError = null,
                            emailError = null,
                            ageError = null,
                            passwordError = null
                        )
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
        authViewModel.state = authViewModel.state.copy(
            firstNameError = null,
            lastNameError = null,
            phoneNumberError = null,
            emailError = null,
            ageError = null,
            passwordError = null
        )
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