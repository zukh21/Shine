package kg.ticode.shine.repository

import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.CarDTO
import kg.ticode.shine.dto.CarResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CarRepository {
    suspend fun carInsert(carDTO: CarDTO): Response<Any>
    suspend fun getAllCars(): Response<List<CarResponse>>
    suspend fun carDeleteById(carId: Long): Response<Any>
    suspend fun carFavoriteById(carId: Long): Response<String>
    suspend fun removeCarFavoriteById(carId: Long): Response<String>
    suspend fun getCarById(carId: Long): Response<CarResponse>
}