package com.example.randomuser.domain

data class User(
    val id: Int,
    val gender: String,
    val firstName: String,
    val lastName: String,
    val location: Location,
    val email: String,
    val dateOfBirth: String,
    val age: Int,
    val phone: String,
    val picture: Picture,
    val nat: String
)

data class Location(
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

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)