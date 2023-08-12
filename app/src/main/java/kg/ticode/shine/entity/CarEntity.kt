package kg.ticode.shine.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kg.ticode.shine.converter.ListStringConverter
import kg.ticode.shine.dto.CarDTO
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
import kg.ticode.shine.enums.SteeringWheel

@Entity("cars")
@TypeConverters(value = [ListStringConverter::class])
data class CarEntity(
    val brand: String,
    val model: String,
    val body: CarBodyType,
    val price: Long,
    val city: Cities? = null,
    val color: CarColor,
    val carsStatus: CarsStatus,
    val transmission: CarTransmissionType,
    val engine: CarEngineType,
    val currency: Currency? = null,
    val yearOfIssue: CarYearOfIssue? = null,
    val mileage: String,
    val steeringWheel: SteeringWheel,
    val driveUnit: DriveUnitType,
    val engineCapacity: String,
    val category: CarCategory?,
    val favorites: Boolean = false,
    val description: String,
    val images: List<String>,
    @PrimaryKey
    val id: Long
) {
    fun toCarResponse(): CarResponse = CarResponse(
        brand = brand,
        model = model,
        body = body,
        price = price.toDouble(),
        city = city,
        color = color,
        carsStatus = carsStatus,
        transmission = transmission,
        engine = engine,
        currency = currency,
        yearOfIssue = yearOfIssue,
        mileage = mileage,
        steeringWheel = steeringWheel,
        driveUnit = driveUnit,
        engineCapacity = engineCapacity,
        category = category,
        favorites = favorites,
        description = description,
        id = id,
        images = images
    )
    companion object{
        fun fromCarResponse(carResponse: CarResponse): CarEntity = CarEntity(
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
            description = carResponse.description,
            images = carResponse.images,
            id = carResponse.id
        )
        fun fromDto(carDTO: CarDTO): CarEntity = CarEntity(
            brand = carDTO.brand,
            model = carDTO.model,
            body = carDTO.body,
            price = carDTO.price,
            city = carDTO.city,
            color = carDTO.color,
            carsStatus = carDTO.carsStatus,
            transmission = carDTO.transmission,
            engine = carDTO.engine,
            currency = carDTO.currency,
            yearOfIssue = carDTO.yearOfIssue,
            mileage = carDTO.mileage,
            steeringWheel = carDTO.steeringWheel,
            driveUnit = carDTO.driveUnit,
            engineCapacity = carDTO.engineCapacity,
            category = carDTO.category,
            favorites = carDTO.favorites,
            description = carDTO.description ?: "",
            images = carDTO.images,
            id = carDTO.id
        )
    }
}
