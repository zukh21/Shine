package kg.ticode.shine.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.ticode.shine.domain.dao.CarDao
import kg.ticode.shine.domain.dao.UserDao
import kg.ticode.shine.entity.CarEntity
import kg.ticode.shine.entity.UserEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [CarEntity::class, UserEntity::class], version = 1)
abstract class AppDb: RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun userDao(): UserDao
}