package com.example.randomuser.domain

import com.example.randomuser.data.local.LocationEntity
import com.example.randomuser.data.local.PictureEntity
import com.example.randomuser.data.local.UserEntity
import com.example.randomuser.data.network.LocationDto
import com.example.randomuser.data.network.PictureDto
import com.example.randomuser.data.network.UserDto

fun UserDto.toUser(): User {
    return User(
        id = 0,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        location = location.toLocation(),
        email = email,
        dateOfBirth = dob.date,
        age = dob.age,
        phone = phone,
        picture = picture.toPicture(),
        nat = nat
    )
}

fun LocationDto.toLocation(): Location {
    return Location(
        streetName = street.name,
        streetNumber = street.number,
        city = city,
        state = state,
        country = country,
        postcode = postcode,
        latitude = coordinates.latitude,
        longitude = coordinates.longitude,
        timezoneOffset = timezone.offset,
        timezoneDescription = timezone.description
    )
}

fun PictureDto.toPicture(): Picture {
    return Picture(large = large, medium = medium, thumbnail = thumbnail)
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        gender = gender,
        firstName = firstName,
        lastName = lastName,
        location = location.toLocation(),
        email = email,
        dateOfBirth = dateOfBirth,
        age = age,
        phone = phone,
        picture = picture.toPicture(),
        nat = nat
    )
}

fun LocationEntity.toLocation(): Location {
    return Location(
        streetName = streetName,
        streetNumber = streetNumber,
        city = city,
        state = state,
        country = country,
        postcode = postcode,
        latitude = latitude,
        longitude = longitude,
        timezoneOffset = timezoneOffset,
        timezoneDescription = timezoneDescription
    )
}

fun PictureEntity.toPicture(): Picture {
    return Picture(large = large, medium = medium, thumbnail = thumbnail)
}


fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        gender = gender,
        firstName = firstName,
        lastName = lastName,
        location = location.toLocationEntity(),
        email = email,
        dateOfBirth = dateOfBirth,
        age = age,
        phone = phone,
        picture = picture.toPictureEntity(),
        nat = nat
    )
}

fun Location.toLocationEntity(): LocationEntity {
    return LocationEntity(
        streetName = streetName,
        streetNumber = streetNumber,
        city = city,
        state = state,
        country = country,
        postcode = postcode,
        latitude = latitude,
        longitude = longitude,
        timezoneOffset = timezoneOffset,
        timezoneDescription = timezoneDescription
    )
}

fun Picture.toPictureEntity(): PictureEntity {
    return PictureEntity(large = large, medium = medium, thumbnail = thumbnail)
}
