package kg.ticode.shine.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.ticode.shine.repository.AuthRepository
import kg.ticode.shine.repository.impl.AuthRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepositoryImpl(impl: AuthRepositoryImpl): AuthRepository
}