package com.example.randomuser.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.randomuser.domain.Dob
import com.example.randomuser.domain.Location
import com.example.randomuser.domain.Name
import com.example.randomuser.domain.Picture

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gender: String,
    @Embedded val name: Name,
    @Embedded val location: Location,
    val email: String,
    @Embedded val dob: Dob,
    val phone: String,
    @Embedded
    val picture: Picture,
    val nat: String
)