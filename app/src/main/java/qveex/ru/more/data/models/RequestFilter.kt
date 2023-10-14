package qveex.ru.more.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestFilter(
    val leftTopCoordinate: Location,
    val rightBottomCoordinate: Location,
    val curUserCoordinate: Location,
    val services: List<Int>,
    val officeTypes: List<Int>,
    val clientTypes: List<String>,
    val hasRamp: Boolean
)
