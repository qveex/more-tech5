package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: Long,
    val name: String,
    val serviceCapability: String?,
    val serviceActivity: String?,
)
