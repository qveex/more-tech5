package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    val day: String,
    val openHours: OpenHours
)
