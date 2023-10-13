package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val name: String,
    val serviceCapability: Boolean,
    val serviceActivity: Boolean,
)
