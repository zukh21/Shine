package kg.ticode.shine.service

import kg.ticode.shine.dto.CarDTO
import kg.ticode.shine.dto.CarResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CarService {
    @POST("cars/carCreate")
    suspend fun insertCar(@Body carDTO: CarDTO): Response<Any>

    @GET("just/getAllCarsJust")
    suspend fun getAllCars(): Response<List<CarResponse>>

    @DELETE("cars/deleteCar/{id}")
    suspend fun carDeleteById(@Path("id") carId: Long): Response<Any>

    @POST("cars/favorites/{carId}")
    suspend fun carFavoriteById(@Path("carId") carId: Long): Response<String>

    @DELETE("cars/removeFavorites/{carId}")
    suspend fun removeCarFavoriteById(@Path("carId") carId: Long): Response<String>

    @GET("just/getCarByIDJust/{id}")
    suspend fun getCarById(@Path("id") carId: Long): Response<CarResponse>
}