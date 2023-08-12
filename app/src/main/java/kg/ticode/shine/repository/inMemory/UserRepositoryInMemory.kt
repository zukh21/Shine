package kg.ticode.shine.repository.inMemory
import kg.ticode.shine.entity.UserEntity

interface UserRepositoryInMemory {
    suspend fun insertUser(userEntity: UserEntity)
    suspend fun getUserById(userId: Long): UserEntity
}