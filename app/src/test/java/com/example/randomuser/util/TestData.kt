package com.example.randomuser.util


import com.example.randomuser.domain.Location
import com.example.randomuser.domain.Picture
import com.example.randomuser.domain.User

object TestData {
    val fakeUser = User(
        id = 1,
        gender = "female",
        firstName = "Jennie",
        lastName = "Nichols",
        location = Location(
            streetNumber = 8929,
            streetName = "Valwood Pkwy",
            city = "Billings",
            state = "Michigan",
            country = "United States",
            postcode = "63104",
            latitude = "-69.8246",
            longitude = "134.8719",
            timezoneOffset = "+9:30",
            timezoneDescription = "Adelaide, Darwin"
        ),
        email = "jennie.nichols@example.com",
        dateOfBirth = "1992-03-08T15:13:16.688Z",
        age = 30,
        phone = "(272) 790-0888",
        picture = Picture(
            large = "https://randomuser.me/api/portraits/women/75.jpg",
            medium = "https://randomuser.me/api/portraits/med/women/75.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/women/75.jpg"
        ),
        nat = "US"
    )
}
