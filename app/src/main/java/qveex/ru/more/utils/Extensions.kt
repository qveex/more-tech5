package qveex.ru.more.utils

import android.util.Log
import androidx.navigation.NavController
import qveex.ru.more.data.models.Days
import qveex.ru.more.utils.Constants.dateFormat
import qveex.ru.more.utils.Constants.dateTimeFormat
import qveex.ru.more.utils.Constants.timeFormat
import java.util.Calendar
import java.util.Date

fun NavController.safeNavigate(route: String) = try {
    navigate(route) { launchSingleTop = true }
} catch (e: IllegalArgumentException) {
    Log.e("Navigation", "safeNavigate failed: $e")
}

val Long.time: String get() = timeFormat.format(Date(this))

val Long.date: String get() = dateFormat.format(Date(this))

val Long.dateTime: String get() = dateTimeFormat.format(Date(this))

/**
 * Заменяет в списке все элементы, удовлетворяющие условию, на новый
 * @param newValue заменяющий элемент
 * @param predicate условие замены
 * @return новый список с замененными элементами
 */
fun <T> List<T>.replace(newValue: T, predicate: (T) -> Boolean) = map { if (predicate(it)) newValue else it }

val Calendar.curDay get() = when (get(Calendar.DAY_OF_WEEK)) {
    Calendar.MONDAY -> Days.MONDAY
    Calendar.TUESDAY -> Days.TUESDAY
    Calendar.WEDNESDAY -> Days.WEDNESDAY
    Calendar.THURSDAY -> Days.THURSDAY
    Calendar.FRIDAY -> Days.FRIDAY
    Calendar.SATURDAY -> Days.SATURDAY
    Calendar.SUNDAY -> Days.SUNDAY
    else -> Days.MONDAY
}