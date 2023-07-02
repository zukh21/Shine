package kg.ticode.shine.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import kg.profris.shine.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(){
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.profile), fontFamily = FontFamily(
                Font(R.font.kaushan_script_regular)
            ), fontSize = 34.sp) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.secondary,
                navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                titleContentColor = MaterialTheme.colorScheme.secondary
            ),
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
    }) {
        Box() {

        }
    }
}