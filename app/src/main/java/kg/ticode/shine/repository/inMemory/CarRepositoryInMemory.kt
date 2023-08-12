package kg.ticode.shine.repository.inMemory

import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.CarDTO
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.entity.CarEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CarRepositoryInMemory {
    suspend fun carInsert(carDTO: CarDTO): ApiResult
    suspend fun carsInsert(cars: List<CarEntity>)
    suspend fun getAllCars(): Flow<List<CarResponse>>
    suspend fun carDeleteById(carId: Long): ApiResult
    suspend fun carDelete(carEntity: CarEntity)
    suspend fun carFavoriteById(carId: Long): Response<String>
    suspend fun removeCarFavoriteById(carId: Long): Response<String>
    suspend fun getCarById(carId: Long): CarEntity
    suspend fun clearCars()
}