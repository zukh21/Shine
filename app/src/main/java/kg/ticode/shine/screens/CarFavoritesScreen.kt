package kg.ticode.shine.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kg.ticode.shine.R
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.enums.CarBodyType
import kg.ticode.shine.enums.CarCategory
import kg.ticode.shine.enums.CarColor
import kg.ticode.shine.enums.CarEngineType
import kg.ticode.shine.enums.CarTransmissionType
import kg.ticode.shine.enums.CarYearOfIssue
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.enums.Cities
import kg.ticode.shine.enums.Currency
import kg.ticode.shine.enums.DriveUnitType
import kg.ticode.shine.enums.ExchangeType
import kg.ticode.shine.enums.SteeringWheel
import kg.ticode.shine.screens.itemsCard.CarItemCard
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.viewmodel.CarsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarFavoritesScreen(carsViewModel: CarsViewModel, navController: NavHostController) {
    val cars = listOf(
        CarResponse(
            body = CarBodyType.COUPE,
            brand = "Ford",
            carsStatus = CarsStatus.AUCTION,
            category = CarCategory.PASSENGER_CAR,
            city = Cities.BATKEN,
            color = CarColor.BLACK,
            description = "Машина на ходу",
            driveUnit = DriveUnitType.ALL_WHEEL_DRIVE,
            engine = CarEngineType.DIESEL,
            engineCapacity = "",
            id = 2L,
            images = listOf("https://cartechnic.ru/800/ford/mustang/ford_mustang_2014_2.jpg"),
            mileage = "20000",
            model = "Mustang",
            price = 1500000.0,
            steeringWheel = SteeringWheel.LEFT,
            favorites = false,
            transmission = CarTransmissionType.AUTOMATIC,
            yearOfIssue = CarYearOfIssue.YEAR_1980,
            currency = Currency.USD
        ),
        CarResponse(
            body = CarBodyType.COUPE,
            brand = "X5",
            carsStatus = CarsStatus.AUCTION,
            category = CarCategory.PASSENGER_CAR,
            city = Cities.BATKEN,
            color = CarColor.BLACK,
            description = "Машина на ходу",
            driveUnit = DriveUnitType.ALL_WHEEL_DRIVE,
            engine = CarEngineType.DIESEL,
            engineCapacity = "",
            id = 2L,
            images = listOf("https://1gai.ru/uploads/posts/2018-06/1528215882_1-2.jpg"),
            mileage = "20000",
            model = "BMW",
            price = 1500000.0,
            steeringWheel = SteeringWheel.LEFT,
            favorites = false,
            transmission = CarTransmissionType.AUTOMATIC,
            yearOfIssue = CarYearOfIssue.YEAR_1980,
            currency = Currency.SOM
        )
    )
    val car = cars.random()

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
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.car_favorites),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                top = it.calculateTopPadding(),
                bottom = 124.dp,
                start = it.calculateStartPadding(LayoutDirection.Ltr),
                end = it.calculateEndPadding(LayoutDirection.Rtl)
            )
        ) {
            items(20) {
                CarItemCard(car = car, carsViewModel = carsViewModel, navController = navController)
            }
        }
    }
}