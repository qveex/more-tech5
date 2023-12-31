package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val atms: List<Atm>,
    val departments: List<Department>,
)

enum class InfrastructureType {
    ATM, DEPARTMENT
}