package kg.ticode.shine.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.ticode.shine.R
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.enums.CarsCategory
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.enums.Cities
import kg.ticode.shine.model.CarsCategoryButtonModel
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.ui.theme.WhiteGray
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    val buttons = listOf(
        CarsCategoryButtonModel("Автомобили", R.drawable.car, ScreensRoute.MainScreen),
        CarsCategoryButtonModel(
            "Спецтехники",
            R.drawable.track,
            ScreensRoute.NotificationsScreen
        ),
        CarsCategoryButtonModel(
            "Мотоциклы",
            R.drawable.motorcycle,
            ScreensRoute.ProfileScreen
        ),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        Column {
            TabRow(selectedTabIndex = pagerState.currentPage, contentColor = PrimaryColor, indicator = {tabPositions ->
                TabRowDefaults.Indicator(height = 1.dp, color = PrimaryColor, modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]))
            }, containerColor = Color.White) {
                buttons.forEachIndexed { index, carsCategoryButtonModel ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
                    ) {
                        Image(
                            painter = painterResource(id = carsCategoryButtonModel.image),
                            contentDescription = "",
                            modifier = Modifier.size(36.dp)
                        )
                        Text(text = carsCategoryButtonModel.name, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }

            HorizontalPager(pageCount = buttons.size, state = pagerState) { page ->
                when(page){
                    0 ->{
                        CarsScreen()
                    }
                    1 ->{
                        SpecialCars()
                    }
                    2 ->{
                        MotorcycleScreen()
                    }
                    else ->{
                    }
                }
            }
        }
    }
}

