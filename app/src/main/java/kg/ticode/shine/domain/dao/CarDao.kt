package kg.ticode.shine.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kg.ticode.shine.entity.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<CarEntity>>

    @Upsert
    suspend fun insertCar(carEntity: CarEntity)

    @Upsert
    suspend fun insertCars(carEntities: List<CarEntity>)

    @Query("DELETE FROM cars WHERE id = :carId")
    suspend fun deleteCarById(carId: Long)

    @Delete
    suspend fun deleteCar(carEntity: CarEntity)

    @Query("DELETE FROM cars")
    suspend fun clearCars()

    @Query("SELECT * FROM cars WHERE id = :carId")
    suspend fun getCarById(carId: Long): CarEntity
}