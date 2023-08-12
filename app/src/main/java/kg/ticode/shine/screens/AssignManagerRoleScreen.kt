package kg.ticode.shine.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.ticode.shine.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignManagerRoleScreen(navController: NavHostController) {
    Scaffold(topBar = { AssignManagerRoleScreenTopBar(navController) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignManagerRoleScreenTopBar(navController: NavHostController, ) {
    var searchFiledFocused by remember {
        mutableStateOf(false)
    }
    if (!searchFiledFocused) {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }, title = {
            Text(
                text = "Назначить роль менеджера",
                Modifier.padding(horizontal = 12.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }, actions = {
            IconButton(onClick = { searchFiledFocused = true }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = PrimaryColor))

    } else {
        TextField(value = "", onValueChange = {}, modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (it.hasFocus) {
                    searchFiledFocused = true
                }
            }, trailingIcon = {
            IconButton(onClick = { searchFiledFocused = false }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
            placeholder = {
                Text(text = "Поиск...", color = Color.White)
            },
            label = {
                Text(text = "Поиск...", color = Color.White)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = PrimaryColor,
                textColor = Color.White
            ),
            shape = RoundedCornerShape(0)
        )
    }
}