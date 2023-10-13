package qveex.ru.more.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes id: Int) = context.getString(id)
    fun getString(@StringRes id: Int, vararg args: Any) = context.getString(id, *args)

    fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)

    fun getAsset(fileName: String) = context.assets.open(fileName)

}