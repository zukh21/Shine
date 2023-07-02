package kg.ticode.shine.screens

import android.widget.Toast
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
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
import androidx.navigation.NavHostController
import kg.profris.shine.R
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.Blue
import kg.ticode.shine.ui.theme.Green
import kg.ticode.shine.utils.CustomConstants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthorizationScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isError by remember {
        mutableStateOf(false)
    }
    val errorText by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var email by remember {
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
        }
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
                        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
                        TODO("here write code to authorization")
                    },
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.authorization).uppercase(),
                        fontSize = 14.sp
                    )
                }
            }
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.do_not_have_an_account),
                        fontSize = 14.sp
                    )
                    TextButton(modifier = Modifier.padding(start = 8.dp), onClick = {
                        navHostController.navigate(ScreensRoute.RegistrationScreen.route){
                            popUpTo(ScreensRoute.MainScreen.route)
                        }
                    }) {
                        Text(
                            text = stringResource(id = R.string.registration),
                            color = Green,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }


    }
}