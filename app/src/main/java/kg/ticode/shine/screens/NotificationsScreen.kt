package kg.ticode.shine.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
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
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.ui.theme.PrimaryColor100
import kg.ticode.shine.utils.CustomConstants

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun NotificationsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = CustomConstants.BOTTOM_APP_BAR_HEIGHT.dp)
    ) {
        stickyHeader {
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
                        .padding(vertical = 12.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.notifications),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
        }
        items(100) {
            NotificationCard()
        }
    }
}

@Composable
fun NotificationCard() {
    val cars = listOf( CarResponse(
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
        currency = Currency.SOM
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
            currency = Currency.USD
        )
    )
    val car = cars.random()
    Spacer(modifier = Modifier.height(12.dp))
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxWidth(.9f),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryColor100)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(model = car.images[0]),
                    contentDescription = "",
                    Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "${car.brand} ${car.model}, ${car.yearOfIssue}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "${intFormatter(car.price.toLong())} $", fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier.padding(horizontal = 8.dp)
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
                    }
                }
            }
        }
    }
}