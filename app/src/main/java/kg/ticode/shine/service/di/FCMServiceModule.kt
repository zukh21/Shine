package kg.ticode.shine.service.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.ticode.shine.BuildConfig
import kg.ticode.shine.service.FCMService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FCMServiceModule {
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor())
        .addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader(
                    "Authorization",
                    "key=AAAAlzEWAtI:APA91bH-ynBBNfLOCeKYUyNoc4vBHyvkVeWhHeIGDvk0HgyN0XnFOaipqo9FvhWRLgI2sF4-hbIYARqSkymEKb0CT6s0Dys_LuEq00tTwE1rBTLvSmOykg4MmrVWFtbjQ9Yu-cznpkRN"
                ).build()
            chain.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun provideFCMRetrofit(): FCMService {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        val gson: Gson = gsonBuilder.create()
        return Retrofit.Builder().baseUrl("https://fcm.googleapis.com/").client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(FCMService::class.java)
    }
}