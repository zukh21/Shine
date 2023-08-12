package kg.ticode.shine.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.ticode.shine.R
import kg.ticode.shine.ui.theme.PrimaryColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutDeveloperScreen(navController: NavHostController) {
    val context = LocalContext.current
    val pm = context.packageManager
    Scaffold(topBar = {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            colors = CardDefaults.cardColors(
                PrimaryColor
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                Text(
                    text = stringResource(R.string.about_developer),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(Modifier.fillMaxWidth(.8f), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ticode),
                    contentDescription = "",
                    Modifier.width(164.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "команда разработчиков TIcode", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "2023 г.", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            val annotatedString = buildAnnotatedString {
                append("По всем вопросом пишите на")
                pushStringAnnotation("ticode_email", "https://webref.ru/recipe/2328")
                withStyle(SpanStyle(color = PrimaryColor, fontWeight = FontWeight.W600)){
                    append(" ticode996@yandex.ru")
                }
            }
            ClickableText(text = annotatedString, onClick = {offset->
                annotatedString.getStringAnnotations(tag = "ticode_email", start = offset, end = offset).firstOrNull()?.let {
                    mailTo(arrayOf("ticode996@yandex.ru"), "Привет! Я из Shine.", pm, context)
                }
            }, modifier = Modifier.fillMaxWidth(.8f).align(Alignment.BottomCenter).padding(vertical = 24.dp), style = TextStyle(textAlign = TextAlign.Center))
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun mailTo(addresses: Array<String>, subject: String, packageManager: PackageManager, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, addresses)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (intent.resolveActivity(packageManager) != null){
        context.startActivity(intent)
    }

}
