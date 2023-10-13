package qveex.ru.more.utils

import qveex.ru.more.utils.Constants.dateFormat
import qveex.ru.more.utils.Constants.dateTimeFormat
import qveex.ru.more.utils.Constants.timeFormat
import java.util.Date

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