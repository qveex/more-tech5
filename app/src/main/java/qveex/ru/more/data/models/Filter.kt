package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Filter(
    val id: Long,
    val name: String
)
