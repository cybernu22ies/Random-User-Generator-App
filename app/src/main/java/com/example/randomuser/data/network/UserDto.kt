package com.example.randomuser.data.network
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.longOrNull

@Serializable
data class UsersResponse(
    val results: List<UserDto>
)

@Serializable
data class UserDto(
    val gender: String,
    val name: NameDto,
    val location: LocationDto,
    val email: String,
    val dob: DobDto,
    val phone: String,
    val picture: PictureDto,
    val nat: String
)

@Serializable
data class NameDto(
    val title: String,
    val first: String,
    val last: String
)

@Serializable
data class LocationDto(
    val street: StreetDto,
    val city: String,
    val state: String,
    val country: String,
    @Serializable(with = PostcodeSerializer::class)
    val postcode: String,
    val coordinates: CoordinatesDto,
    val timezone: TimezoneDto
)

object PostcodeSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("postcode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
        return when (jsonElement) {
            is JsonPrimitive -> {
                if (jsonElement.isString) {
                    jsonElement.content
                } else {
                    jsonElement.longOrNull?.toString() ?: ""
                }
            }
            else -> ""
        }
    }
}

@Serializable
data class StreetDto(
    val number: Int,
    val name: String
)

@Serializable
data class CoordinatesDto(
    val latitude: String,
    val longitude: String
)

@Serializable
data class TimezoneDto(
    val offset: String,
    val description: String
)

@Serializable
data class DobDto(
    val date: String,
    val age: Int
)

@Serializable
data class PictureDto(
    val large: String,
    val medium: String,
    val thumbnail: String
)
