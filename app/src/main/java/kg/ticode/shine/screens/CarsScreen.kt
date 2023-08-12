package kg.ticode.shine.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.screens.itemsCard.CarItemCard
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.viewmodel.CarsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CarsScreen(navController: NavHostController, carsViewModel: CarsViewModel) {
    val filter by remember {
        mutableStateOf<String?>("")
    }
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }
    var cars by remember {
        mutableStateOf<List<CarResponse>>(emptyList())
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    GetAllCars(context, carsViewModel = carsViewModel, cars = {
        cars = it
    })
    val lifecycleOwner = LocalLifecycleOwner.current

    val filtered =
        if (filter.isNullOrBlank()) cars.reversed() else cars.filter { it.brand == filter }
            .reversed()
    val coroutineScope = rememberCoroutineScope()
    if (filtered.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        carsViewModel.getAllCars()
                        carsViewModel.getAllCarsEventChannel.collectLatest { event ->
                            when (event) {
                                ApiResult.Error -> {
                                    isLoading = false
                                    Toast.makeText(
                                        context,
                                        "Что-то пошло не так, поэтому обновить данные не удалось, проверьте свой Интернет и повторите попытку.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                ApiResult.Loading -> {
                                    isLoading = true
                                }

                                is ApiResult.Success -> {
                                    carsViewModel.cars.observe(lifecycleOwner) { list ->
                                        cars = list.orEmpty()
                                    }
                                    isLoading = false
                                }

                                ApiResult.Timeout -> {
                                    isLoading = false
                                    Toast.makeText(
                                        context,
                                        "Что-то пошло не так, поэтому обновить данные не удалось, проверьте свой Интернет и повторите попытку.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = PrimaryColor,
                        contentColor = Color.White
                    )
                }) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(bottom = CustomConstants.BOTTOM_APP_BAR_HEIGHT.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(filtered.size) { i ->
                            CarItemCard(filtered[i], carsViewModel, navController)
                        }
                    })
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                Text(text = "В базе данных нет автомобилей, когда администратор введет данные о автомобилях, они появятся здесь!")
            }
        }
    }

}

@Composable
fun GetAllCars(
    context: Context,
    carsViewModel: CarsViewModel,
    cars: (List<CarResponse>) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = context) {
        carsViewModel.cars.observe(lifecycleOwner) { list ->
            cars.invoke(list.orEmpty())
        }
    }
}
