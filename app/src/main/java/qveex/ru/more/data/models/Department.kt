package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Department(
    val departmentId: Long,
    val address: String,
    val metroStation: String,
    val status: Status,
    val distance: Int,
    val workload: Int,
    val location: Location,
    val hasRamp: Boolean,
    val legal: List<Entity>,
    val individual: List<Entity>
)

@Serializable
enum class Status {
    OPEN,
    CLOSED,
    TECHNICAL_PROBLEMS
}