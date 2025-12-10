package com.example.randomuser.domain

import com.example.randomuser.data.local.UserEntity
import com.example.randomuser.data.network.DobDto
import com.example.randomuser.data.network.LocationDto
import com.example.randomuser.data.network.NameDto
import com.example.randomuser.data.network.PictureDto
import com.example.randomuser.data.network.UserDto

fun UserDto.toUser(): User {
    return User(
        id = 0,
        gender = gender,
        name = name.toName(),
        location = location.toLocation(),
        email = email,
        dob = dob.toDob(),
        phone = phone,
        picture = picture.toPicture(),
        nat = nat
    )
}

fun NameDto.toName(): Name {
    return Name(title = title, first = first, last = last)
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

fun DobDto.toDob(): Dob {
    return Dob(date = date, age = age)
}

fun PictureDto.toPicture(): Picture {
    return Picture(large = large, medium = medium, thumbnail = thumbnail)
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        gender = gender,
        name = name,
        location = location,
        email = email,
        dob = dob,
        phone = phone,
        picture = picture,
        nat = nat
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        gender = gender,
        name = name,
        location = location,
        email = email,
        dob = dob,
        phone = phone,
        picture = picture,
        nat = nat
    )
}
