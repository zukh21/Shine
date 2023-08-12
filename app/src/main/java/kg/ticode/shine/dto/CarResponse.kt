package kg.ticode.shine.dto

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
import kg.ticode.shine.presentation.CarCreateFormEvent

data class CarResponse(
    val body: CarBodyType,
    val brand: String,
    val carsStatus: CarsStatus,
    val category: CarCategory?,
    val city: Cities?,
    val color: CarColor,
    val description: String,
    val driveUnit: DriveUnitType,
    val currency: Currency?,
    val engine: CarEngineType,
    val engineCapacity: String,
    val id: Long,
    val images: List<String>,
    val mileage: String,
    val model: String,
    val price: Double,
    val steeringWheel: SteeringWheel,
    val favorites: Boolean = false,
    val transmission: CarTransmissionType,
    val yearOfIssue: CarYearOfIssue?
)
