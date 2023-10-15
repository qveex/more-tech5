package qveex.ru.more.data.models

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import qveex.ru.more.R

@Serializable
data class Entity(
    val day: Days,
    val openHours: OpenHours,
    val workLoad: Map<String, Int>
)

@Serializable
enum class Days(@Transient @StringRes val labelId: Int) {
    MONDAY(R.string.label_monday),
    TUESDAY(R.string.label_tuesday),
    WEDNESDAY(R.string.label_wednesday),
    THURSDAY(R.string.label_thursday),
    FRIDAY(R.string.label_friday),
    SATURDAY(R.string.label_saturday),
    SUNDAY(R.string.label_sunday)
}
