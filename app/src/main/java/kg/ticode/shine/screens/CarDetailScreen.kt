package kg.ticode.shine.screens

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kg.ticode.shine.R
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.domain.use_case.WhoIsUser
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.enums.CarEvents
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.model.CarEventsModel
import kg.ticode.shine.model.FCMPushNotificationRequestModelData
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants.CIRCULAR_PROGRESS_SIZE
import kg.ticode.shine.viewmodel.AuthViewModel
import kg.ticode.shine.viewmodel.CarsViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailScreen(
    carsViewModel: CarsViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var car by remember {
        mutableStateOf<CarResponse?>(null)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var isTimeout by remember {
        mutableStateOf(false)
    }
    var carId by remember {
        mutableStateOf(0L)
    }
    LaunchedEffect(key1 = context) {
        carsViewModel.carToDetail.observe(lifecycleOwner) { remoteCar ->
            carId = remoteCar.id
        }
        if (carId != 0L) {
            getCarById(carId, carsViewModel) { carResponse ->
                car = carResponse
            }
            carsViewModel.getCarByIdEventChannel.collect { result ->
                println("result: $result")
                when (result) {
                    ApiResult.Error -> {
                        isLoading = false
                        isError = true
                        isTimeout = false
                    }

                    ApiResult.Loading -> {
                        isLoading = true
                        isError = false
                        isTimeout = false
                    }

                    is ApiResult.Success -> {
                        isLoading = false
                        isError = false
                        isTimeout = false
                    }

                    ApiResult.Timeout -> {
                        isLoading = false
                        isError = false
                        isTimeout = true
                    }
                }
            }
        }
    }
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
                    text = "Детали автомобиля",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (!isLoading) {
                if (!isError || !isTimeout) {
                    if (car != null) {
                        Car(
                            car = car!!,
                            authViewModel = authViewModel,
                            carsViewModel,
                            navController = navController
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Такой автомобил нету или удален!")
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Row {
                            Text(text = "Что-то пошло не так попробуйте ешё раз!")
                            Spacer(modifier = Modifier.height(6.dp))
                            Button(onClick = {
                                if (carId != 0L) {
                                    getCarById(carId, carsViewModel) { carResponse ->
                                        car = carResponse
                                    }
                                }
                            }) {
                                Text(text = "Повторить")
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(CIRCULAR_PROGRESS_SIZE.dp))
                }
            }
        }
    }
}

fun getCarById(carId: Long, carsViewModel: CarsViewModel, car: (CarResponse) -> Unit) {
    carsViewModel.getCarByIdFromMemory(carId = carId) { carResponse ->
        car.invoke(carResponse)
    }
}

@Composable
fun Car(
    car: CarResponse,
    authViewModel: AuthViewModel,
    carsViewModel: CarsViewModel,
    navController: NavHostController
) {
    var isUserAdminOrManager by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var inFavorite by remember {
        mutableStateOf(car.favorites)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        authViewModel.isUser.observe(lifecycleOwner) { isUser ->
            println("user: $isUser")
            isUserAdminOrManager = when (isUser) {
                WhoIsUser.IsAdmin -> true
                WhoIsUser.IsJustUser -> false
                WhoIsUser.IsManager -> true
            }
            println("isYser $isUserAdminOrManager")
        }
        carsViewModel.carDeleteEvents.observe(lifecycleOwner) { event ->
            println("event: $event")
            when (event) {
                ApiResult.Loading -> isLoading = true
                ApiResult.Timeout -> isLoading = false
                ApiResult.Error -> {
                    isLoading = false
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

                is ApiResult.Success -> {
                    navController.popBackStack()
                    carsViewModel.carEventClear()
                }
            }
        }

        carsViewModel.carFavoriteByIdEventChannel.collect { result ->
            when (result) {
                ApiResult.Error -> {
                    Toast.makeText(
                        context,
                        "Что-то пошло не так, поэтому этот автомобиль не был добавлен в избранное",
                        Toast.LENGTH_SHORT
                    ).show()
                    inFavorite = false
                }

                ApiResult.Loading -> Unit
                is ApiResult.Success -> {
                    Toast.makeText(
                        context,
                        "Автомобиль был успешно добавлен в избранное",
                        Toast.LENGTH_SHORT
                    ).show()
                    inFavorite = true
                }

                ApiResult.Timeout -> {
                    Toast.makeText(
                        context,
                        "Что-то пошло не так, поэтому этот автомобиль не был добавлен в избранное",
                        Toast.LENGTH_SHORT
                    ).show()
                    inFavorite = false
                }
            }
        }
        carsViewModel.removeCarFavoriteByIdEventChannel.collect { result ->
            when (result) {
                ApiResult.Error -> {
                    Toast.makeText(
                        context,
                        "Что-то пошло не так, поэтому этот автомобиль не удален из избранного",
                        Toast.LENGTH_SHORT
                    ).show()
                    inFavorite = false
                }

                ApiResult.Loading -> Unit
                is ApiResult.Success -> {
                    Toast.makeText(
                        context,
                        "Автомобиль был успешно удален из избранного",
                        Toast.LENGTH_SHORT
                    ).show()
                    inFavorite = true
                }

                ApiResult.Timeout -> {
                    Toast.makeText(
                        context,
                        "Что-то пошло не так, поэтому этот автомобиль не удален из избранного",
                        Toast.LENGTH_SHORT
                    ).show()
                    inFavorite = false
                }
            }
        }

    }
    if (!isLoading) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.padding(horizontal = 8.dp)) {
                            Text(
                                text = "${car.brand} ${car.model}, ${car.yearOfIssue?.value}",
                                fontSize = 24.sp
                            )
                            Text(
                                text = "${intFormatter(car.price.toLong())} ${car.currency?.value?.uppercase() ?: "$"}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = car.city?.value ?: "city",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Box {
                            var isExpanded by remember {
                                mutableStateOf(false)
                            }
                            if (isUserAdminOrManager) {
                                IconButton(
                                    onClick = { isExpanded = true }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.MoreVert,
                                        contentDescription = ""
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false }) {
                                val carEventsModel = listOf(
                                    CarEventsModel(
                                        icon = Icons.Default.Delete,
                                        carEvents = CarEvents.DELETE
                                    ),
                                    CarEventsModel(
                                        icon = Icons.Default.KeyboardArrowUp,
                                        carEvents = CarEvents.CHANGE_STATUS
                                    )
                                )
                                carEventsModel.forEach { event ->
                                    DropdownMenuItem(text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(event.icon, contentDescription = null)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(text = event.carEvents.ru)
                                        }
                                    }, onClick = {
                                        when (event.carEvents) {
                                            CarEvents.DELETE -> {
                                                Toast.makeText(
                                                    context,
                                                    event.carEvents.ru,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                carsViewModel.carDeleteById(car.id)
                                            }

                                            CarEvents.CHANGE_STATUS -> {
                                                Toast.makeText(
                                                    context,
                                                    event.carEvents.ru,
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }
                                        }
                                        isExpanded = false
                                    })
                                }
                            }
                        }

                    }
                    Image(
                        painter = rememberAsyncImagePainter(model = car.images[0]),
                        contentDescription = "",
                        Modifier
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (car.carsStatus == CarsStatus.VIP) {
                            Image(
                                painter = painterResource(id = R.drawable.vip),
                                contentDescription = "",
                                modifier = Modifier.width(28.dp)
                            )
                        } else if (car.carsStatus == CarsStatus.AUCTION) {
                            Image(
                                painter = painterResource(id = R.drawable.auction),
                                contentDescription = "",
                                modifier = Modifier.width(28.dp)
                            )
                        }
                        if (inFavorite) {
                            IconButton(
                                onClick = {
                                    carsViewModel.removeCarFavoriteById(carId = car.id)
                                    inFavorite = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "",
                                    Modifier
                                        .size(28.dp),
                                    tint = Color.Red
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    carsViewModel.carFavoriteById(car.id)
                                    inFavorite = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = "",
                                    Modifier
                                        .size(28.dp)
                                )
                            }
                        }
                    }
                    CarParameter(
                        parameter = "Двигатель",
                        value = "${car.engine.value}, ${car.engineCapacity} л"
                    )
                    CarParameter(parameter = "Коробка передач", value = car.transmission.value)
                    CarParameter(parameter = "Привод", value = car.driveUnit.value)
                    CarParameter(parameter = "Тип кузова", value = car.body.value)
                    CarParameter(parameter = "Цвет", value = car.color.value)
                    CarParameter(parameter = "Пробег", value = car.mileage)
                    CarParameter(parameter = "Руль", value = car.steeringWheel.value)

                    Row(Modifier.padding(12.dp)) {

                        AdditionallyAnnotatedString(text = car.description)
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(CIRCULAR_PROGRESS_SIZE.dp))
        }
    }
}

@Composable
fun CarParameter(parameter: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            text = "$parameter: ",
            Modifier.fillMaxWidth(.5f),
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(text = value, Modifier.fillMaxWidth(.5f), fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun AdditionallyAnnotatedString(text: String) {
    val str = buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Gray)) {
            append("Дополнительно: ")
        }
        append(text)
    }
    Text(text = str, fontSize = 16.sp)
}

fun intFormatter(value: Long): String {
    return String.format("%,d", value).replace(",", " ")
}