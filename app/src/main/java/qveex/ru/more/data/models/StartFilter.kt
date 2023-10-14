package qveex.ru.more.data.models

import androidx.compose.runtime.MutableState
import kotlinx.serialization.Serializable

@Serializable
data class StartFilter(
    val id: Int,
    val name: String,
    val checked: Boolean
)
