package kg.ticode.shine.domain.use_case.car_validate

import kg.ticode.shine.domain.use_case.ValidationResult
import kg.ticode.shine.enums.CarAccountingCountry
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

class MarkValidation {
    fun execute(mark: String): ValidationResult{
        if (mark.isBlank()){
            return ValidationResult(false, "Поле для марки должно быть заполнено!")
        }
        return ValidationResult(true)
    }
}

class BodyValidation {
    fun execute(body: CarBodyType): ValidationResult{
        if (body == CarBodyType.NO_CHOOSE){
            return ValidationResult(false, "Выберите кузов автомобиля!")
        }
        return ValidationResult(true)
    }
}

class ModelValidation {
    fun execute(model: String): ValidationResult{
        if (model.isBlank()){
            return ValidationResult(false, "Поле для модели должно быть заполнено!")
        }
        return ValidationResult(true)
    }
}

class PriceValidation {
    fun execute(price: Long): ValidationResult{
        if (price <= 0L){
                return ValidationResult(false, "Цена должна быть выше 0!")
            }
        return ValidationResult(true)
    }
}

class EngineCapacityValidation {
    fun execute(engineCapacity: String): ValidationResult{
        if (engineCapacity.isBlank()) {
                return ValidationResult(false, "Поле мощность двигателя должно быть заполнено!")
        }
        return ValidationResult(true)
    }
}

class YearOfIssueValidation {
    fun execute(yearOfIssue: CarYearOfIssue): ValidationResult{
        if (yearOfIssue == CarYearOfIssue.NO_CHOOSE) {
            return ValidationResult(false, "Выберите год выпуска!")
        }
        return ValidationResult(true)
    }
}

class CarStatusValidation {
    fun execute(carsStatus: CarsStatus): ValidationResult{
        if (carsStatus == CarsStatus.NO_CHOOSE) {
            return ValidationResult(false, "Выберите статус автомобиля!")
        }
        return ValidationResult(true)
    }
}

class EngineValidation {
    fun execute(engine: CarEngineType): ValidationResult{
        if (engine == CarEngineType.NO_CHOOSE) {
            return ValidationResult(false, "Выберите топливо!")
        }
        return ValidationResult(true)
    }
}
class TransmissionValidation {
    fun execute(transmissionType: CarTransmissionType): ValidationResult{
        if (transmissionType == CarTransmissionType.NO_CHOOSE) {
            return ValidationResult(false, "Выберите тип коробка передач!")
        }
        return ValidationResult(true)
    }
}

class DriveUnitValidation {
    fun execute(driveUnitType: DriveUnitType): ValidationResult{
        if (driveUnitType == DriveUnitType.NO_CHOOSE) {
            return ValidationResult(false, "Выберите привод!")
        }
        return ValidationResult(true)
    }
}

class ColorValidation {
    fun execute(color: CarColor): ValidationResult{
        if (color == CarColor.NO_CHOOSE) {
            return ValidationResult(false, "Выберите цвет!")
        }
        return ValidationResult(true)
    }
}

class MileageValidation {
    fun execute(mileage: String): ValidationResult{
        if (mileage.isBlank()) {
            return ValidationResult(false, "Поле пробег должно быть заполнено!")
        }
        return ValidationResult(true)
    }
}

class SteeringWheelValidation {
    fun execute(steeringWheel: SteeringWheel): ValidationResult{
        if (steeringWheel == SteeringWheel.NO_CHOOSE) {
            return ValidationResult(false, "Выберите руль!")
        }
        return ValidationResult(true)
    }
}

class CityValidation {
    fun execute(city: Cities): ValidationResult{
        if (city == Cities.NO_CHOOSE || city == Cities.ANY) {
            return ValidationResult(false, "Выберите город!")
        }
        return ValidationResult(true)
    }
}
