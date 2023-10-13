package qveex.ru.more.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Constants {

    const val BASE_URL = "https://www.google.com/"

    const val PREFERENCES_NAME = "5heads_preferences"

    const val SCREEN_TRANSIT_DURATION_MILLIS = 400

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
}