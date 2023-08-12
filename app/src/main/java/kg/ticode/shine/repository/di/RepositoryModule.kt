package kg.ticode.shine.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.ticode.shine.repository.AuthRepository
import kg.ticode.shine.repository.CarRepository
import kg.ticode.shine.repository.FCMRepository
import kg.ticode.shine.repository.UserRepository
import kg.ticode.shine.repository.impl.AuthRepositoryImpl
import kg.ticode.shine.repository.impl.CarRepositoryImpl
import kg.ticode.shine.repository.inMemory.impl.CarRepositoryInMemoryImpl
import kg.ticode.shine.repository.impl.FCMRepositoryImpl
import kg.ticode.shine.repository.impl.UserRepositoryImpl
import kg.ticode.shine.repository.inMemory.CarRepositoryInMemory
import kg.ticode.shine.repository.inMemory.UserRepositoryInMemory
import kg.ticode.shine.repository.inMemory.impl.UserRepositoryInMemoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepositoryImpl(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindCarRepositoryImpl(impl: CarRepositoryImpl): CarRepository

    @Binds
    @Singleton
    fun bindFCMRepositoryImpl(impl: FCMRepositoryImpl): FCMRepository

    @Binds
    @Singleton
    fun bindUserRepositoryImpl(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindCarRepositoryInMemoryImpl(impl: CarRepositoryInMemoryImpl): CarRepositoryInMemory
    @Binds
    @Singleton
    fun bindUserRepositoryInMemoryImpl(impl: UserRepositoryInMemoryImpl): UserRepositoryInMemory
}