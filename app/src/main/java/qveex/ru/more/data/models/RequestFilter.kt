package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestFilter(
    val leftTopCoordinate: Location?,
    val rightBottomCoordinate: Location?,
    val curUserCoordinate: Location,
    val services: List<Long> = emptyList(),
    val officeTypes: List<Long> = emptyList(),
    val clientTypes: List<Long> = emptyList(),
    val hasRamp: Boolean = false
)
