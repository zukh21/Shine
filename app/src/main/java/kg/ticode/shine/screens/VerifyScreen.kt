package kg.ticode.shine.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.profris.shine.R
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.Green
import kg.ticode.shine.ui.theme.VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR
import kg.ticode.shine.utils.CustomConstants.VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE
import kg.ticode.shine.utils.CustomConstants.VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE

@Composable
fun PhoneNumberVerifyScreen() {
    val context = LocalContext.current
    var verifyCode by remember {
        mutableStateOf<StringBuilder>(StringBuilder("2023"))
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 8.dp), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.phone_number_verification).uppercase(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                Modifier
                    .padding(vertical = 12.dp)
                    .background(Green), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "+996-306-23-20-01",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(onClick = {
                    Toast.makeText(context, "Edit number", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier.padding(horizontal = 4.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_edit_24),
                        contentDescription = "Edit phone number",
                        Modifier.size(18.dp),
                        tint = Color.White
                    )
                }
            }
            Text(
                text = verifyCode.toString(), fontSize = 36.sp,
                color = Green,
                fontWeight = FontWeight.Bold, letterSpacing = TextUnit(12f, TextUnitType.Sp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Please enter the verification code received by Whatsapp sms, If you did not receive the verification code,",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            TextButton(
                onClick = {},
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Resend code", fontSize = 18.sp,
                    color = Green,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode = StringBuilder("1")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),
                modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "1", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("2")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "2", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("3")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "3", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("4")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "4", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("5")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "5", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("6")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "6", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("7")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "7", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("8")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "8", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("9")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "9", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "<", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {
                        if (verifyCode.length <= 4) {
                            verifyCode.append("0")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "0", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
                TextButton(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VERIFY_SCREEN_NUMBER_BUTTONS_BG_COLOR),
                    shape = RoundedCornerShape(VERIFY_SCREEN_NUMBER_BUTTONS_CORNER_SIZE.dp),modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "=", fontSize = VERIFY_SCREEN_NUMBER_BUTTONS_FONT_SIZE.sp)
                }
            }
        }
    }
}