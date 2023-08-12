package kg.ticode.shine.screens.itemsCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kg.ticode.shine.R
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.navigation.ScreensRoute
import kg.ticode.shine.screens.intFormatter
import kg.ticode.shine.ui.theme.PrimaryColor
import kg.ticode.shine.viewmodel.CarsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarItemCard(car: CarResponse, carsViewModel: CarsViewModel, navController: NavHostController) {

    Card(
        onClick = {
            carsViewModel.setCarToDetail(car)
            navController.navigate(ScreensRoute.CarDetailScreen.route)
        },
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(5),
        elevation = CardDefaults.cardElevation(),
    ) {
        Column(Modifier.padding(3.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberAsyncImagePainter(model = car.images[0]),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5))
                        .height(120.dp)
                        .fillMaxWidth()
                )
                if (car.carsStatus == CarsStatus.VIP) {
                    Image(
                        painter = painterResource(id = R.drawable.vip),
                        contentDescription = "",
                        modifier = Modifier
                            .align(
                                Alignment.TopStart
                            )
                            .padding(8.dp)
                            .width(28.dp)
                    )
                } else if (car.carsStatus == CarsStatus.AUCTION) {
                    Image(
                        painter = painterResource(id = R.drawable.auction),
                        contentDescription = "",
                        modifier = Modifier
                            .align(
                                Alignment.TopStart
                            )
                            .padding(8.dp)
                            .width(28.dp)
                    )
                }


            }
            Text(text = "${car.brand} ${car.model}, ${car.yearOfIssue?.value}")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = "${intFormatter(car.price.toLong())} $",
                    fontWeight = FontWeight.Bold,
                )
                if (car.favorites){
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .width(28.dp),
                        tint = Color.Red
                    )
                }else{
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .width(28.dp),
                        tint = PrimaryColor
                    )
                }
            }
            Text(text = car.city?.value ?: "City", fontSize = 10.sp)
        }
    }

}