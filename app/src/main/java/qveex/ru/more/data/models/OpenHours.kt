package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class OpenHours(
    val from: String,
    val to: String
)
