package kg.ticode.shine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.domain.use_case.car_validate.BodyValidation
import kg.ticode.shine.domain.use_case.car_validate.CarStatusValidation
import kg.ticode.shine.domain.use_case.car_validate.CityValidation
import kg.ticode.shine.domain.use_case.car_validate.ColorValidation
import kg.ticode.shine.domain.use_case.car_validate.DriveUnitValidation
import kg.ticode.shine.domain.use_case.car_validate.EngineCapacityValidation
import kg.ticode.shine.domain.use_case.car_validate.EngineValidation
import kg.ticode.shine.domain.use_case.car_validate.MarkValidation
import kg.ticode.shine.domain.use_case.car_validate.MileageValidation
import kg.ticode.shine.domain.use_case.car_validate.ModelValidation
import kg.ticode.shine.domain.use_case.car_validate.PriceValidation
import kg.ticode.shine.domain.use_case.car_validate.SteeringWheelValidation
import kg.ticode.shine.domain.use_case.car_validate.TransmissionValidation
import kg.ticode.shine.domain.use_case.car_validate.YearOfIssueValidation
import kg.ticode.shine.dto.CarDTO
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.entity.CarEntity
import kg.ticode.shine.model.CarCreateFormState
import kg.ticode.shine.presentation.CarCreateFormEvent
import kg.ticode.shine.repository.CarRepository
import kg.ticode.shine.repository.inMemory.CarRepositoryInMemory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    val repository: CarRepository,
    private val carRepositoryInMemoryImpl: CarRepositoryInMemory
) : ViewModel() {
    private val markValidation = MarkValidation()
    private val modelValidation = ModelValidation()
    private val yearOfIssueValidation = YearOfIssueValidation()
    private val priceValidation = PriceValidation()
    private val engineCapacityValidation = EngineCapacityValidation()
    private val carStatusValidation = CarStatusValidation()
    private val engineValidation = EngineValidation()
    private val transmissionValidation = TransmissionValidation()
    private val driveUnitValidation = DriveUnitValidation()
    private val bodyValidation = BodyValidation()
    private val colorValidation = ColorValidation()
    private val mileageValidation = MileageValidation()
    private val steeringWheelValidation = SteeringWheelValidation()
    private val cityValidation = CityValidation()

    var state by mutableStateOf(CarCreateFormState())

    private var _carFavoriteByIdEventChannel = Channel<ApiResult>()
    val carFavoriteByIdEventChannel = _carFavoriteByIdEventChannel.receiveAsFlow()

    private var _removeCarFavoriteByIdEventChannel = Channel<ApiResult>()
    val removeCarFavoriteByIdEventChannel = _removeCarFavoriteByIdEventChannel.receiveAsFlow()

    private val carDeleteEventChannel = MutableLiveData<ApiResult>()
    val carDeleteEvents = carDeleteEventChannel

    private var _carToDetail: MutableLiveData<CarResponse> = MutableLiveData<CarResponse>()
    val carToDetail: LiveData<CarResponse> = _carToDetail

    private var _insertCarEventChannel = Channel<ApiResult>()
    val insertCarEventChannel = _insertCarEventChannel.receiveAsFlow()

    private var _getAllCarsEventChannel = Channel<ApiResult>()
    val getAllCarsEventChannel = _getAllCarsEventChannel.receiveAsFlow()

    private var _getCarByIdEventChannel = Channel<ApiResult>()
    val getCarByIdEventChannel = _getCarByIdEventChannel.receiveAsFlow()

    private var _cars: MutableLiveData<List<CarResponse>?> = MutableLiveData()
    val cars: LiveData<List<CarResponse>?> = _cars

    init {
        getAllCars()
    }

    fun setCarToDetail(carResponse: CarResponse) {
        viewModelScope.launch {
            _carToDetail.value = carResponse
        }
    }


    fun carEventClear() {
        carDeleteEvents.value = null
    }

    fun onEvent(event: CarCreateFormEvent) {
        when (event) {
            is CarCreateFormEvent.Accounting -> state = state.copy(accounting = event.accounting)
            is CarCreateFormEvent.Availability -> state =
                state.copy(availability = event.availability)

            is CarCreateFormEvent.Body -> state = state.copy(body = event.body)
            is CarCreateFormEvent.Brand -> state = state.copy(brand = event.brand)
            is CarCreateFormEvent.CarsStatus -> state = state.copy(carsStatus = event.carsStatus)
            is CarCreateFormEvent.Category -> state = state.copy(category = event.category)
            is CarCreateFormEvent.City -> state = state.copy(city = event.city)
            is CarCreateFormEvent.Color -> state = state.copy(color = event.color)
            is CarCreateFormEvent.Condition -> state = state.copy(condition = event.condition)
            is CarCreateFormEvent.Currency -> state = state.copy(currency = event.currency)
            is CarCreateFormEvent.Description -> state = state.copy(description = event.description)
            is CarCreateFormEvent.DriveUnit -> state = state.copy(driveUnit = event.driveUnit)
            is CarCreateFormEvent.Engine -> state = state.copy(engine = event.engine)
            is CarCreateFormEvent.EngineCapacity -> state =
                state.copy(engineCapacity = event.engineCapacity)

            is CarCreateFormEvent.Exchange -> state = state.copy(exchange = event.exchange)
            is CarCreateFormEvent.Images -> state = state.copy(images = event.images)
            is CarCreateFormEvent.Mileage -> state = state.copy(mileage = event.mileage)
            is CarCreateFormEvent.Model -> state = state.copy(model = event.model)
            is CarCreateFormEvent.Price -> state = state.copy(price = event.price)
            is CarCreateFormEvent.StateCarNumber -> state =
                state.copy(stateCarNumber = event.stateCarNumber)

            is CarCreateFormEvent.SteeringWheel -> state =
                state.copy(steeringWheel = event.steeringWheel)

            is CarCreateFormEvent.Transmission -> state =
                state.copy(transmission = event.transmission)

            is CarCreateFormEvent.YearOfIssue -> state = state.copy(yearOfIssue = event.yearOfIssue)
            CarCreateFormEvent.Create -> {
                carCreate()
            }
        }
    }

    private fun carCreate() {
        val brandResult = markValidation.execute(state.brand)
        val modelResult = modelValidation.execute(state.model)
        val yearOfIssueResult = yearOfIssueValidation.execute(state.yearOfIssue)
        val priceResult = priceValidation.execute(state.price)
        val cityResult = cityValidation.execute(state.city)
        val carsStatusResult = carStatusValidation.execute(state.carsStatus)
        val engineResult = engineValidation.execute(state.engine)
        val engineCapacityResult = engineCapacityValidation.execute(state.engineCapacity)
        val transmissionResult = transmissionValidation.execute(state.transmission)
        val driveUnitResult = driveUnitValidation.execute(state.driveUnit)
        val bodyTypeResult = bodyValidation.execute(state.body)
        val colorResult = colorValidation.execute(state.color)
        val mileageResult = mileageValidation.execute(state.mileage)
        val steeringWheelResult = steeringWheelValidation.execute(state.steeringWheel)
        val hasError = listOf(
            brandResult,
            modelResult,
            yearOfIssueResult,
            priceResult,
            carsStatusResult,
            engineResult,
            engineCapacityResult,
            transmissionResult,
            driveUnitResult,
            bodyTypeResult,
            carsStatusResult,
            mileageResult,
            steeringWheelResult
        ).any {
            !it.successful
        }
        if (hasError) {
            state = state.copy(
                brandError = brandResult.reason,
                modelError = modelResult.reason,
                bodyError = bodyTypeResult.reason,
                yearOfIssueError = yearOfIssueResult.reason,
                priceError = priceResult.reason,
                carsStatusError = carsStatusResult.reason,
                engineError = engineResult.reason,
                engineCapacityError = engineCapacityResult.reason,
                transmissionError = transmissionResult.reason,
                driveUnitError = driveUnitResult.reason,
                colorError = colorResult.reason,
                mileageError = mileageResult.reason,
                steeringWheelError = steeringWheelResult.reason,
                cityError = cityResult.reason
            )
            viewModelScope.launch {
                _insertCarEventChannel.send(ApiResult.Error)
            }
        } else {
            insertCar()
        }
    }


    private fun insertCar() = viewModelScope.launch {
        try {
            _insertCarEventChannel.send(ApiResult.Loading)
            val car = CarDTO(
                brand = state.brand,
                model = state.model,
                body = state.body,
                price = state.price,
                city = state.city,
                color = state.color,
                carsStatus = state.carsStatus,
                transmission = state.transmission,
                engine = state.engine,
                currency = state.currency,
                yearOfIssue = state.yearOfIssue,
                mileage = state.mileage,
                steeringWheel = state.steeringWheel,
                driveUnit = state.driveUnit,
                engineCapacity = state.engineCapacity,
                category = null,
                description = state.description,
                images = state.images.ifEmpty { listOf("https://i.mycdn.me/i?r=AzEPZsRbOZEKgBhR0XGMT1RkAWjKIcYSU2FadSsZ4LWtg6aKTM5SRkZCeTgDn6uOyic") }
            )

            val response = repository.carInsert(
                car
            )
            if (!response.isSuccessful) {
                carRepositoryInMemoryImpl.carDelete(CarEntity.fromDto(car))
                _insertCarEventChannel.send(ApiResult.Error)
            } else {
                _insertCarEventChannel.send(ApiResult.Success)
            }
        } catch (socketTOE: SocketTimeoutException) {
            _insertCarEventChannel.send(ApiResult.Timeout)
        } catch (e: Exception) {
            _insertCarEventChannel.send(ApiResult.Error)
        }
    }

    fun getAllCars() {
        viewModelScope.launch {
            try {
                _getAllCarsEventChannel.send(ApiResult.Loading)
                val response = repository.getAllCars()
                if (response.isSuccessful) {
                    carRepositoryInMemoryImpl.getAllCars().collectLatest {
                        _cars.value = it
                        _getAllCarsEventChannel.send(ApiResult.Success)
                    }
                }else{
                    _getAllCarsEventChannel.send(ApiResult.Error)
                }
            } catch (socketTOE: SocketTimeoutException) {
                _getAllCarsEventChannel.send(ApiResult.Timeout)
            } catch (e: Exception) {
                e.printStackTrace()
                _getAllCarsEventChannel.send(ApiResult.Error)
            }
        }
    }

    fun carDeleteById(carId: Long) {
        viewModelScope.launch {
            try {
                carDeleteEventChannel.value = (ApiResult.Loading)
                carRepositoryInMemoryImpl.carDeleteById(carId)
                repository.carDeleteById(carId)
                carDeleteEventChannel.value = (ApiResult.Success)
            } catch (socketTOE: SocketTimeoutException) {
                socketTOE.printStackTrace()
                carDeleteEventChannel.value = (ApiResult.Timeout)
            } catch (e: Exception) {
                e.printStackTrace()
                carDeleteEventChannel.value = (ApiResult.Error)
            }
        }
    }

    fun carFavoriteById(carId: Long) {
        viewModelScope.launch {
            try {
                _carFavoriteByIdEventChannel.send(ApiResult.Loading)
                val response = repository.carFavoriteById(carId)
                if (response.isSuccessful) {
                    _carFavoriteByIdEventChannel.send(ApiResult.Success)
                } else {
                    _carFavoriteByIdEventChannel.send(ApiResult.Error)
                }
            } catch (socketTOE: SocketTimeoutException) {
                _carFavoriteByIdEventChannel.send(ApiResult.Timeout)
            } catch (e: Exception) {
                _carFavoriteByIdEventChannel.send(ApiResult.Error)
            }
        }
    }

    fun removeCarFavoriteById(carId: Long) {
        viewModelScope.launch {
            try {
                _removeCarFavoriteByIdEventChannel.trySend(ApiResult.Loading)
                val response = repository.removeCarFavoriteById(carId)
                if (response.isSuccessful) {
                    _removeCarFavoriteByIdEventChannel.send(ApiResult.Success)
                } else {
                    _removeCarFavoriteByIdEventChannel.send(ApiResult.Error)
                }
            } catch (socketTOE: SocketTimeoutException) {
                _removeCarFavoriteByIdEventChannel.send(ApiResult.Timeout)
            } catch (e: Exception) {
                _removeCarFavoriteByIdEventChannel.send(ApiResult.Error)
            }
        }
    }

    fun getCarByIdFromMemory(carId: Long, car: (CarResponse) -> Unit) {
        viewModelScope.launch {
            try {
                _getCarByIdEventChannel.send(ApiResult.Loading)
                car.invoke(carRepositoryInMemoryImpl.getCarById(carId).toCarResponse())
                _getCarByIdEventChannel.send(ApiResult.Success)
            } catch (socketTOE: SocketTimeoutException) {
                _getCarByIdEventChannel.send(ApiResult.Timeout)

            } catch (e: Exception) {
                _getCarByIdEventChannel.send(ApiResult.Error)
            }
        }
    }

}