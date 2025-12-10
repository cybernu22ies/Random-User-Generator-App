package com.example.randomuser.domain

import com.example.randomuser.data.local.UserDao
import com.example.randomuser.data.network.RandomUserApi
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun insertUser(user: User)
    suspend fun requestRandomUser(gender: String, nationality: String): ApiResponse<User>
}

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val randomUserApi: RandomUserApi
): UserRepository {
    override fun getUsers(): Flow<List<User>> = userDao.getUsers().map {
        users-> users.map {user -> user.toUser() }
    }

    override suspend fun insertUser(user: User) = userDao.insertUser(user.toUserEntity())

    override suspend fun requestRandomUser(
        gender: String,
        nationality: String
    ): ApiResponse<User> {
        try {
            val user = randomUserApi.requestRandomUser(gender, nationality).toUser()
            return ApiResponse.Success(user)
        } catch (e: Exception) {
            return ApiResponse.Error(e.message.toString())
        }
    }
}
