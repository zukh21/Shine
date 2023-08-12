package kg.ticode.shine.model

import kg.ticode.shine.enums.CarAccountingCountry
import kg.ticode.shine.enums.CarAvailability
import kg.ticode.shine.enums.CarBodyType
import kg.ticode.shine.enums.CarCategory
import kg.ticode.shine.enums.CarColor
import kg.ticode.shine.enums.CarEngineType
import kg.ticode.shine.enums.CarTransmissionType
import kg.ticode.shine.enums.CarYearOfIssue
import kg.ticode.shine.enums.CarsStatus
import kg.ticode.shine.enums.Cities
import kg.ticode.shine.enums.Condition
import kg.ticode.shine.enums.Currency
import kg.ticode.shine.enums.DriveUnitType
import kg.ticode.shine.enums.ExchangeType
import kg.ticode.shine.enums.SteeringWheel
import java.io.File

data class CarCreateFormState(
    val brand: String = "",
    val brandError: String? = null,
    val model: String = "",
    val modelError: String? = null,
    val stateCarNumber: String = "",
    val stateCarNumberError: String? = null,
    val body: CarBodyType = CarBodyType.NO_CHOOSE,
    val bodyError: String? = null,
    val price: Long = 0L,
    val priceError: String? = null,
    val city: Cities = Cities.NO_CHOOSE,
    val cityError: String? = null,
    val color: CarColor = CarColor.NO_CHOOSE,
    val colorError: String? = null,
    val carsStatus: CarsStatus = CarsStatus.NO_CHOOSE,
    val carsStatusError: String? = null,
    val accounting: CarAccountingCountry = CarAccountingCountry.NO_CHOOSE,
    val accountingError: String? = null,
    val availability: CarAvailability = CarAvailability.NO_CHOOSE,
    val availabilityError: String? = null,
    val transmission: CarTransmissionType = CarTransmissionType.NO_CHOOSE,
    val transmissionError: String? = null,
    val engine: CarEngineType = CarEngineType.NO_CHOOSE,
    val engineError: String? = null,
    val currency: Currency = Currency.USD,
    val currencyError: String? = null,
    val yearOfIssue: CarYearOfIssue = CarYearOfIssue.NO_CHOOSE,
    val yearOfIssueError: String? = null,
    val mileage: String = "",
    val mileageError: String? = null,
    val steeringWheel: SteeringWheel = SteeringWheel.NO_CHOOSE,
    val steeringWheelError: String? = null,
    val driveUnit: DriveUnitType = DriveUnitType.NO_CHOOSE,
    val driveUnitError: String? = null,
    val engineCapacity: String = "",
    val engineCapacityError: String? = null,
    val condition: Condition = Condition.NO_CHOOSE,
    val conditionError: String? = null,
    val exchange: ExchangeType = ExchangeType.NO_CHOOSE,
    val exchangeError: String? = null,
    val category: CarCategory = CarCategory.NO_CHOOSE,
    val categoryError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val images: List<String> = emptyList(),
    val imagesError: String? = null
)
