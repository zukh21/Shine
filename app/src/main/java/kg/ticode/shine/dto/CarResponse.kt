package kg.ticode.shine.dto

import kg.ticode.shine.enums.CarsCategory
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.enums.Cities

data class CarResponse(
    val accounting: String,
    val availability: String,
    val body: String,
    val brand: String,
    val carsStatus: CarsStatus,
    val category: CarsCategory,
    val city: Cities,
    val color: String,
    val condition: String,
    val customs: String,
    val dateOfCreated: String,
    val description: String,
    val driveUnit: String,
    val engine: String,
    val exchange: String,
    val id: Long,
    val image: String,
    val mileage: String,
    val model: String,
    val price: Double,
    val regionCityOfSale: String,
    val steeringWheel: String,
    val transmission: String,
    val yearOfIssue: Int
)
