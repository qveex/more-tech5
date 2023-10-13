package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    val day: Days,
    val openHours: OpenHours
)

@Serializable
enum class Days {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}