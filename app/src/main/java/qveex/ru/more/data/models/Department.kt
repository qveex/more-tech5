package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Department(
    val departmentId: Long,
    val address: String,
    val metroStation: String,
    val status: String,
    val distance: Int,
    val workload: Int,
    val location: Location,
    val hasRamp: Boolean,
    val legal: List<Entity>,
    val individual: List<Entity>
)