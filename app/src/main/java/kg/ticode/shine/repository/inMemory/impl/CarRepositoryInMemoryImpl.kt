package kg.ticode.shine.repository.inMemory.impl

import kg.ticode.shine.domain.dao.CarDao
import kg.ticode.shine.domain.use_case.ApiResult
import kg.ticode.shine.dto.CarDTO
import kg.ticode.shine.dto.CarResponse
import kg.ticode.shine.entity.CarEntity
import kg.ticode.shine.repository.CarRepository
import kg.ticode.shine.repository.inMemory.CarRepositoryInMemory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class CarRepositoryInMemoryImpl @Inject constructor(private val carDao: CarDao) : CarRepositoryInMemory {
    override suspend fun carInsert(carDTO: CarDTO): ApiResult {
        return try {
            ApiResult.Loading
            carDao.insertCar(CarEntity.fromDto(carDTO))
            ApiResult.Success
        } catch (e: Exception) {
            ApiResult.Error
            throw Exception(e)
        }
    }

    override suspend fun carsInsert(cars: List<CarEntity>) {
        carDao.insertCars(cars)
    }

    override suspend fun getAllCars(): Flow<List<CarResponse>> {
        return carDao.getAllCars().map { list -> list.map { it.toCarResponse() } }
    }

    override suspend fun carDeleteById(carId: Long): ApiResult {
        return try {
            ApiResult.Loading
            carDao.deleteCarById(carId)
            ApiResult.Success
        } catch (e: Exception) {
            ApiResult.Error
            throw Exception(e)
        }
    }

    override suspend fun carDelete(carEntity: CarEntity) {
        carDao.deleteCar(carEntity)
    }

    override suspend fun carFavoriteById(carId: Long): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun removeCarFavoriteById(carId: Long): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getCarById(carId: Long):CarEntity {
        return carDao.getCarById(carId)
    }

    override suspend fun clearCars() {
        carDao.clearCars()
    }
}