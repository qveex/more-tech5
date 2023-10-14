package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Atm(
    val atmId: Long,
    val address: String,
    val metroStation: String,
    val allDay: Boolean,
    val distance: Int,
    val coordinates: Location,
    val services: List<Service>
)
