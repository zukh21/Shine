package kg.ticode.shine.presentation

import kg.ticode.shine.enums.CarAccountingCountry
import kg.ticode.shine.enums.CarAvailability
import kg.ticode.shine.enums.CarBodyType
import kg.ticode.shine.enums.CarCategory
import kg.ticode.shine.enums.CarColor
import kg.ticode.shine.enums.CarEngineType
import kg.ticode.shine.enums.CarTransmissionType
import kg.ticode.shine.enums.CarYearOfIssue
import kg.ticode.shine.enums.Cities
import kg.ticode.shine.enums.DriveUnitType
import kg.ticode.shine.enums.ExchangeType
import java.io.File

sealed class CarCreateFormEvent{
    data class Brand(val brand: String): CarCreateFormEvent()
    data class Model(val model: String): CarCreateFormEvent()
    data class StateCarNumber(val stateCarNumber: String): CarCreateFormEvent()
    data class Body(val body: CarBodyType): CarCreateFormEvent()
    data class Price(val price: Long): CarCreateFormEvent()
    data class City(val city: Cities): CarCreateFormEvent()
    data class Color(val color: CarColor): CarCreateFormEvent()
    data class CarsStatus(val carsStatus: kg.ticode.shine.enums.CarsStatus): CarCreateFormEvent()
    data class Accounting(val accounting: CarAccountingCountry): CarCreateFormEvent()
    data class Availability(val availability: CarAvailability): CarCreateFormEvent()
    data class Transmission(val transmission: CarTransmissionType): CarCreateFormEvent()
    data class Engine(val engine: CarEngineType): CarCreateFormEvent()
    data class Currency(val currency: kg.ticode.shine.enums.Currency): CarCreateFormEvent()
    data class YearOfIssue(val yearOfIssue: CarYearOfIssue): CarCreateFormEvent()
    data class Mileage(val mileage: String): CarCreateFormEvent()
    data class SteeringWheel(val steeringWheel: kg.ticode.shine.enums.SteeringWheel): CarCreateFormEvent()
    data class DriveUnit(val driveUnit: DriveUnitType): CarCreateFormEvent()
    data class EngineCapacity(val engineCapacity: String): CarCreateFormEvent()
    data class Condition(val condition: kg.ticode.shine.enums.Condition): CarCreateFormEvent()
    data class Exchange(val exchange: ExchangeType): CarCreateFormEvent()
    data class Category(val category: CarCategory): CarCreateFormEvent()
    data class Description(val description: String): CarCreateFormEvent()
    data class Images(val images: List<String>): CarCreateFormEvent()
    object Create: CarCreateFormEvent()
}
