package kg.ticode.shine.dto

import kg.ticode.shine.enums.CarAccountingCountry
import kg.ticode.shine.enums.CarAvailability
import kg.ticode.shine.enums.CarBodyType
import kg.ticode.shine.enums.CarEngineType
import kg.ticode.shine.enums.CarTransmissionType
import kg.ticode.shine.enums.CarCategory
import kg.ticode.shine.enums.CarColor
import kg.ticode.shine.enums.CarYearOfIssue
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.enums.Cities
import kg.ticode.shine.enums.Condition
import kg.ticode.shine.enums.Currency
import kg.ticode.shine.enums.DriveUnitType
import kg.ticode.shine.enums.ExchangeType
import kg.ticode.shine.enums.SteeringWheel
import java.io.File

data class CarDTO(
    val brand: String,
    val model: String,
    val body: CarBodyType,
    val price: Long,
    val city: Cities?,
    val color: CarColor,
    val carsStatus: CarsStatus,
    val transmission: CarTransmissionType,
    val engine: CarEngineType,
    val currency: Currency?,
    val yearOfIssue: CarYearOfIssue?,
    val mileage: String,
    val steeringWheel: SteeringWheel,
    val driveUnit: DriveUnitType,
    val engineCapacity: String,
    val category: CarCategory?,
    val favorites: Boolean = false,
    val description: String?,
    val images: List<String> = emptyList(),
    val id: Long = 0
){
    companion object{
        fun fromCarResponse(carResponse: CarResponse): CarDTO = CarDTO(
            brand = carResponse.brand,
            model = carResponse.model,
            body = carResponse.body,
            price = carResponse.price.toLong(),
            city = carResponse.city,
            color = carResponse.color,
            carsStatus = carResponse.carsStatus,
            transmission = carResponse.transmission,
            engine = carResponse.engine,
            currency = carResponse.currency,
            yearOfIssue = carResponse.yearOfIssue,
            mileage = carResponse.mileage,
            steeringWheel = carResponse.steeringWheel,
            driveUnit = carResponse.driveUnit,
            engineCapacity = carResponse.engineCapacity,
            category = carResponse.category,
            favorites = carResponse.favorites,
            description = carResponse.description
        )
    }
}
