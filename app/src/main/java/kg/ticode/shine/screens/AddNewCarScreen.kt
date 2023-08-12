package kg.ticode.shine.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kg.ticode.shine.R
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.enums.CarBodyType
import kg.ticode.shine.enums.CarColor
import kg.ticode.shine.enums.CarEngineType
import kg.ticode.shine.enums.CarTransmissionType
import kg.ticode.shine.enums.CarYearOfIssue
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.enums.Cities
import kg.ticode.shine.enums.Currency
import kg.ticode.shine.enums.DriveUnitType
import kg.ticode.shine.enums.SteeringWheel
import kg.ticode.shine.model.FCMPushNotificationRequestModelData
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.presentation.CarCreateFormEvent
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.utils.CustomConstants
import kg.ticode.shine.utils.CustomConstants.CIRCULAR_PROGRESS_SIZE
import kg.ticode.shine.utils.SendFCMNotification
import kg.ticode.shine.viewmodel.CarsViewModel
import kg.ticode.shine.viewmodel.FCMViewModel
import kg.ticode.shine.viewmodel.MediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "SimpleDateFormat",
    "CoroutineCreationDuringComposition"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCarScreen(navController: NavHostController) {
    val context = LocalContext.current

    val carViewModel = hiltViewModel<CarsViewModel>()

    val mediaViewModel = hiltViewModel<MediaViewModel>()

    val state = carViewModel.state

    var fCMPushNotificationRequestModelData by remember {
        mutableStateOf<FCMPushNotificationRequestModelData?>(null)
    }


    var pickedImages by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val photosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> pickedImages = uris }
    )

    var timeout by remember {
        mutableStateOf(false)
    }


    val fcmViewModel = hiltViewModel<FCMViewModel>()
    var tokens by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    val appName = stringResource(id = R.string.app_name)

    getFCMTokensFromFirebase(tokens = {
        tokens = it
    })

    val ownToken = getOwnFCMToken(context)


    InsertCarEventChannel(carViewModel, isLoading = {
        isLoading = it
    }, timeout = {
        timeout = it
    }, isSuccess = {
        if (it){
            sendFCMPushNotification(
                tokens = tokens,
                ownToken = ownToken,
                fcmViewModel = fcmViewModel,
                fCMPushNotificationRequestModelData = fCMPushNotificationRequestModelData,
                appName = appName
            )
            navController.navigate(ScreensRoute.MainScreen.route) {
                popUpTo(0)
            }
        }
    })
    if (timeout) {
        TimeOutErrorSnackbar {
            timeout = it
        }
    }
    Scaffold(topBar = {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
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
                    text = stringResource(R.string.add_a_car),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    val sdf =
                        SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
                    val currentDateAndTime = sdf.format(Date())
                    val files = mutableListOf<File>()
                    var sendImagesToVM by remember {
                        mutableStateOf(false)
                    }
                    if (!isLoading) {
                        IconButton(onClick = {
                            isLoading = true
                            pickedImages.forEachIndexed { index, uri ->
                                val file = createTmpFileFromUri(
                                    context,
                                    uri,
                                    "car_image-$index-${currentDateAndTime}"
                                )
                                file?.let { files.add(it) }
                            }
                            insertCarImage(files, mediaViewModel, isFinish = {
                                if (it) {
                                    sendImagesToVM = true
                                }
                            }) { list ->
                                if (sendImagesToVM) {
                                    carViewModel.state = state.copy(images = list)
                                    fCMPushNotificationRequestModelData =
                                        FCMPushNotificationRequestModelData(
                                            state.brand,
                                            state.model, state.yearOfIssue.value, list[0]
                                        )
                                    createCar(carViewModel)
                                }
                            }
                        }, modifier = Modifier.align(Alignment.CenterEnd)) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }

                    } else {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(horizontal = 12.dp)
                        ) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        }

                    }
                }
            }
        }
    }, floatingActionButton = {
        Card(
            onClick = {
                photosPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = PrimaryColor),
            enabled = !isLoading
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.padding(12.dp)
            )
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(bottom = 80.dp, start = 24.dp, end = 24.dp)
            ) {
                item {
                    if (pickedImages.isNotEmpty()) {
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(1), Modifier.height(
                                120.dp
                            )
                        ) {
                            items(pickedImages.size) { i ->
                                Card(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(8.dp), shape = RoundedCornerShape(10)
                                ) {
                                    AsyncImage(
                                        model = pickedImages[i],
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                    CustomTextField(
                        value = state.brand,
                        onValueChange = { carViewModel.onEvent(CarCreateFormEvent.Brand(it)) },
                        label = "Марка",
                        isError = state.brandError != null,
                        supportingText = state.brandError ?: "",
                        enabled = !isLoading
                    )
                    CustomTextField(
                        value = state.model,
                        onValueChange = { carViewModel.onEvent(CarCreateFormEvent.Model(it)) },
                        label = "Модель",
                        isError = state.modelError != null,
                        supportingText = state.modelError ?: "",
                        enabled = !isLoading
                    )
                    var isExpandedCarCity by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedCarCity,
                        onExpandedChange = { isExpandedCarCity = it }) {
                        TextField(
                            value = state.city.value, onValueChange = {},
                            label = {
                                Text(text = "Где машина?")
                            },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedCarCity)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.cityError != null,
                            supportingText = {
                                if (state.cityError != null) {
                                    Text(text = state.cityError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedCarCity,
                            onDismissRequest = { isExpandedCarCity = false }) {
                            Cities.values().forEach { city ->
                                DropdownMenuItem(text = {
                                    Text(text = city.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.City(city))
                                    isExpandedCarCity = false
                                })
                            }
                        }
                    }
                    var isExpandedCarColor by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedCarColor,
                        onExpandedChange = { isExpandedCarColor = it }) {
                        TextField(
                            value = state.color.value, onValueChange = {},
                            label = {
                                Text(text = "Цвет")
                            },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedCarColor)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.colorError != null,
                            supportingText = {
                                if (state.colorError != null) {
                                    Text(text = state.colorError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedCarColor,
                            onDismissRequest = { isExpandedCarColor = false }) {
                            CarColor.values().forEach { color ->
                                DropdownMenuItem(text = {
                                    Text(text = color.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.Color(color))
                                    isExpandedCarColor = false
                                })
                            }
                        }
                    }
                    var isExpandedCarReleaseYear by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedCarReleaseYear,
                        onExpandedChange = { isExpandedCarReleaseYear = it }) {
                        TextField(
                            value = state.yearOfIssue.value, onValueChange = {},
                            label = {
                                Text(text = "Год выпуска")
                            },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedCarReleaseYear)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.yearOfIssueError != null,
                            supportingText = {
                                if (state.yearOfIssueError != null) {
                                    Text(text = state.yearOfIssueError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedCarReleaseYear,
                            onDismissRequest = { isExpandedCarReleaseYear = false }) {
                            CarYearOfIssue.values().forEach { year ->
                                DropdownMenuItem(text = {
                                    Text(text = year.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.YearOfIssue(year))
                                    isExpandedCarReleaseYear = false
                                })
                            }
                        }
                    }
                    var isExpandedCarBodyType by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedCarBodyType,
                        onExpandedChange = { isExpandedCarBodyType = it }) {
                        TextField(value = state.body.value, onValueChange = {}, label = {
                            Text(text = "Кузов автомобиля")
                        }, readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedCarBodyType)
                        },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.bodyError != null,
                            supportingText = {
                                if (state.bodyError != null) {
                                    Text(text = state.bodyError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedCarBodyType,
                            onDismissRequest = { isExpandedCarBodyType = false }) {
                            CarBodyType.values().forEach { carBodyType ->
                                DropdownMenuItem(text = {
                                    Text(text = carBodyType.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.Body(carBodyType))
                                    isExpandedCarBodyType = false
                                })
                            }
                        }
                    }

                    CustomTextField(
                        value = state.price.toString(),
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                if (it.isBlank()) carViewModel.onEvent(CarCreateFormEvent.Price(0)) else carViewModel.onEvent(
                                    CarCreateFormEvent.Price(it.toLong())
                                )
                            }
                        },
                        label = "Цена",
                        isError = state.priceError != null,
                        supportingText = state.priceError ?: "",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
                    )
                    var isExpandedCurrency by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedCurrency,
                        onExpandedChange = { isExpandedCurrency = it }) {
                        TextField(value = state.currency.value, onValueChange = {}, label = {
                            Text(text = "Валюта")
                        }, readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedCurrency)
                        },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.currencyError != null,
                            supportingText = {
                                if (state.currencyError != null) {
                                    Text(text = state.currencyError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedCurrency,
                            onDismissRequest = { isExpandedCurrency = false }) {
                            Currency.values().forEach { currency ->
                                DropdownMenuItem(text = {
                                    Text(text = currency.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.Currency(currency))
                                    isExpandedCurrency = false
                                })
                            }
                        }
                    }
                    var isExpandedCarStatus by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedCarStatus,
                        onExpandedChange = { isExpandedCarStatus = it }) {
                        TextField(value = state.carsStatus.value, onValueChange = {}, label = {
                            Text(text = "Статус автомобиля")
                        }, readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedCarStatus)
                        },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.carsStatusError != null,
                            supportingText = {
                                if (state.carsStatusError != null) {
                                    Text(text = state.carsStatusError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedCarStatus,
                            onDismissRequest = { isExpandedCarStatus = false }) {
                            CarsStatus.values().forEach { carStatus ->
                                DropdownMenuItem(text = {
                                    Text(text = carStatus.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.CarsStatus(carStatus))
                                    isExpandedCarStatus = false
                                })
                            }
                        }
                    }

                    var isExpandedEngine by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedEngine,
                        onExpandedChange = { isExpandedEngine = it }) {
                        TextField(value = state.engine.value, onValueChange = {}, label = {
                            Text(text = "Топливо")
                        }, readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedEngine)
                        },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.engineCapacityError != null,
                            supportingText = {
                                if (state.engineCapacityError != null) {
                                    Text(text = state.engineCapacityError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedEngine,
                            onDismissRequest = { isExpandedEngine = false }) {
                            CarEngineType.values().forEach { engine ->
                                DropdownMenuItem(text = {
                                    Text(text = engine.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.Engine(engine))
                                    isExpandedEngine = false
                                })
                            }
                        }
                    }

                    CustomTextField(
                        value = state.engineCapacity,
                        onValueChange = { carViewModel.onEvent(CarCreateFormEvent.EngineCapacity(it)) },
                        label = "Мощность двигателя",
                        isError = state.engineCapacityError != null,
                        supportingText = state.engineCapacityError ?: "",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
                    )

                    var isExpandedTransmission by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedTransmission,
                        onExpandedChange = { isExpandedTransmission = it }) {
                        TextField(value = state.transmission.value, onValueChange = {}, label = {
                            Text(text = "Коробка передач автомобиля")
                        }, readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedTransmission)
                        },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.transmissionError != null,
                            supportingText = {
                                if (state.transmissionError != null) {
                                    Text(text = state.transmissionError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedTransmission,
                            onDismissRequest = { isExpandedTransmission = false }) {
                            CarTransmissionType.values().forEach { transmission ->
                                DropdownMenuItem(text = {
                                    Text(text = transmission.value)
                                }, onClick = {
                                    carViewModel.onEvent(
                                        CarCreateFormEvent.Transmission(
                                            transmission = transmission
                                        )
                                    )
                                    isExpandedTransmission = false
                                })
                            }
                        }
                    }

                    var isExpandedDriveUnit by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedDriveUnit,
                        onExpandedChange = { isExpandedDriveUnit = it }) {
                        TextField(value = state.driveUnit.value, onValueChange = {}, label = {
                            Text(text = "Привод")
                        }, readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedDriveUnit)
                        },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.driveUnitError != null,
                            supportingText = {
                                if (state.driveUnitError != null) {
                                    Text(text = state.driveUnitError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedDriveUnit,
                            onDismissRequest = { isExpandedDriveUnit = false }) {
                            DriveUnitType.values().forEach { driveUnit ->
                                DropdownMenuItem(text = {
                                    Text(text = driveUnit.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.DriveUnit(driveUnit))
                                    isExpandedDriveUnit = false
                                })
                            }
                        }
                    }
                    CustomTextField(
                        value = state.mileage,
                        onValueChange = { carViewModel.onEvent(CarCreateFormEvent.Mileage(it)) },
                        label = "Пробег",
                        isError = state.mileageError != null,
                        supportingText = state.mileageError ?: "",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = !isLoading
                    )
                    var isExpandedWheel by remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = isExpandedWheel,
                        onExpandedChange = { isExpandedWheel = it }) {
                        TextField(value = state.steeringWheel.value, onValueChange = {}, label = {
                            Text(text = "Руль")
                        }, readOnly = true, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedWheel)
                        },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10),
                            isError = state.steeringWheelError != null,
                            supportingText = {
                                if (state.steeringWheelError != null) {
                                    Text(text = state.steeringWheelError, fontSize = 12.sp)
                                }
                            },
                            enabled = !isLoading
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedWheel,
                            onDismissRequest = { isExpandedWheel = false }) {
                            SteeringWheel.values().forEach { wheel ->
                                DropdownMenuItem(text = {
                                    Text(text = wheel.value)
                                }, onClick = {
                                    carViewModel.onEvent(CarCreateFormEvent.SteeringWheel(wheel))
                                    isExpandedWheel = false
                                })
                            }
                        }
                    }

                    CustomTextField(
                        value = state.description,
                        onValueChange = { carViewModel.onEvent(CarCreateFormEvent.Description(it)) },
                        label = "Дополнительно",
                        isError = state.descriptionError != null,
                        supportingText = state.descriptionError ?: "",
                        singleLine = false,
                        enabled = !isLoading
                    )

                }
            }
        }
    }
}

@Composable
fun TimeOutErrorSnackbar(timeout: (Boolean) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    Snackbar(modifier = Modifier.padding(8.dp)) {
        Text("Что-то пошло не так попробуйте еще раз!")
        LaunchedEffect(key1 = true) {
            coroutineScope.launch {
                delay(3000)
                timeout.invoke(false)
            }
        }
    }
}

fun getOwnFCMToken(context: Context): String? {
    val ref = context.getSharedPreferences(CustomConstants.REF_FCM, Context.MODE_PRIVATE)
    return ref.getString(CustomConstants.REF_FCM_TOKEN_KEY, null)
}

fun getFCMTokensFromFirebase(tokens: (List<String>) -> Unit) {
    val db =
        FirebaseDatabase.getInstance()
            .getReference(CustomConstants.FIREBASE_REALTIME_DATABASE_TOKEN_KEY)
    db.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            tokens.invoke(snapshot.children.map { it.value as String })
        }

        override fun onCancelled(error: DatabaseError) {
            Unit
        }
    })
}

@Composable
fun InsertCarEventChannel(
    carViewModel: CarsViewModel,
    isLoading: (Boolean) -> Unit,
    timeout: (Boolean) -> Unit,
    isSuccess: (Boolean) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        carViewModel.insertCarEventChannel.collect { event ->
            when (event) {
                ApiResult.Error -> {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    isLoading.invoke(false)
                    timeout.invoke(false)
                    isSuccess.invoke(false)
                }

                is ApiResult.Success -> {
                    isSuccess.invoke(true)
                    timeout.invoke(false)
                    isLoading.invoke(false)
                }

                ApiResult.Timeout -> {
                    isSuccess.invoke(false)
                    timeout.invoke(true)
                    isLoading.invoke(false)
                }

                ApiResult.Loading -> {
                    isSuccess.invoke(false)
                    timeout.invoke(false)
                    isLoading.invoke(true)
                }
            }
        }
    }
}

fun sendFCMPushNotification(
    tokens: List<String>,
    ownToken: String?,
    fcmViewModel: FCMViewModel,
    fCMPushNotificationRequestModelData: FCMPushNotificationRequestModelData?,
    appName: String
) {
    tokens.forEach { token ->
        ownToken?.let { myToken ->
            fCMPushNotificationRequestModelData?.let { data ->
                SendFCMNotification.send(
                    token, myToken,
                    appName, fcmViewModel, data
                )
            }
        }
    }
}


fun insertCarImage(
    files: List<File>,
    mediaViewModel: MediaViewModel,
    isFinish: (Boolean) -> Unit,
    urls: (List<String>) -> Unit,
) {
    println("files: $files")
    mediaViewModel.insertCarImages(files) {
        if (it.first) {
            isFinish.invoke(true)
            urls.invoke(it.second)
        }
    }
}

fun createCar(carsViewModel: CarsViewModel) {
    carsViewModel.onEvent(CarCreateFormEvent.Create)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    supportingText: String,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    enabled: Boolean
) {
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(text = label)
        }, label = {
            Text(text = label)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = supportingText)
            }
        },
        shape = RoundedCornerShape(10),
        readOnly = readOnly,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        enabled = enabled
    )

}

