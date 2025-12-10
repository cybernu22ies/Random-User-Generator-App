package com.example.randomuser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM userentity")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert
    fun insertUser(user: UserEntity)
}