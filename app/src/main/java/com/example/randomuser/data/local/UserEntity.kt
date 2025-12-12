package com.example.randomuser.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gender: String,
    val firstName: String,
    val lastName: String,
    @Embedded val location: LocationEntity,
    val email: String,
    val dateOfBirth: String,
    val age: Int,
    val phone: String,
    @Embedded val picture: PictureEntity,
    val nat: String
)

data class LocationEntity(
    val streetNumber: Int,
    val streetName: String,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val latitude: String,
    val longitude: String,
    val timezoneOffset: String,
    val timezoneDescription: String
)

data class PictureEntity(
    val large: String,
    val medium: String,
    val thumbnail: String
)