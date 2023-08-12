package kg.ticode.shine.repository.impl

import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.CarDTO
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.entity.CarEntity
import kg.ticode.shine.repository.CarRepository
import kg.ticode.shine.repository.inMemory.impl.CarRepositoryInMemoryImpl
import kg.ticode.shine.service.CarService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    val service: CarService,
    private val carRepositoryInMemoryImpl: CarRepositoryInMemoryImpl
) : CarRepository {
    override suspend fun carInsert(carDTO: CarDTO): Response<Any> {
            carRepositoryInMemoryImpl.carInsert(carDTO)
            return service.insertCar(carDTO)
    }

    override suspend fun getAllCars(): Response<List<CarResponse>> {
        val response = service.getAllCars()
        if (response.isSuccessful) {
            carRepositoryInMemoryImpl.clearCars()
            carRepositoryInMemoryImpl.carsInsert(response.body().orEmpty().map { CarEntity.fromCarResponse(it) })
        }
        return response
    }

    override suspend fun carDeleteById(carId: Long): Response<Any> {
        return service.carDeleteById(carId)
    }

    override suspend fun carFavoriteById(carId: Long): Response<String> {
        return service.carFavoriteById(carId = carId)
    }

    override suspend fun removeCarFavoriteById(carId: Long): Response<String> {
        return service.removeCarFavoriteById(carId = carId)
    }

    override suspend fun getCarById(carId: Long): Response<CarResponse> {
        return service.getCarById(carId = carId)
    }
}