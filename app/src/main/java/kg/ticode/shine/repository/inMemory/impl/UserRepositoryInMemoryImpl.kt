package kg.ticode.shine.repository.inMemory.impl

import kg.ticode.shine.domain.dao.UserDao
import kg.ticode.shine.entity.UserEntity
import kg.ticode.shine.repository.inMemory.UserRepositoryInMemory
import javax.inject.Inject

class UserRepositoryInMemoryImpl @Inject constructor(
    private val userDao: UserDao
): UserRepositoryInMemory {
    override suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    override suspend fun getUserById(userId: Long): UserEntity {
        return userDao.getUserById(userId = userId)
    }
}