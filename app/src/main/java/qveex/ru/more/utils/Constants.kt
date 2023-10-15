package qveex.ru.more.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Constants {

    const val BASE_URL = "http://45.12.237.22:8080/api/v1/"

    const val PREFERENCES_NAME = "5heads_preferences"

    const val SCREEN_TRANSIT_DURATION_MILLIS = 400

    const val INFO_ID_ARGUMENT = "id"
    const val INFO_TYPE_ARGUMENT = "type"

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
}