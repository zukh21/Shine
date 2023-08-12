package kg.ticode.shine.domain.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kg.ticode.shine.domain.dao.CarDao
import kg.ticode.shine.domain.dao.UserDao
import kg.ticode.shine.domain.database.AppDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppDbModule {
    @Singleton
    @Provides
    fun provideAppDb(
        @ApplicationContext context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, "ShineDataBase").build()

    @Singleton
    @Provides
    fun provideCarDao(appDb: AppDb): CarDao = appDb.carDao()

    @Singleton
    @Provides
    fun provideUserDao(appDb: AppDb): UserDao = appDb.userDao()
}