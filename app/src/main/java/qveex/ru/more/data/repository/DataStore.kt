package qveex.ru.more.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import qveex.ru.more.utils.Constants.PREFERENCES_NAME

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
class DataStore(context: Context) {

    companion object {
        private const val TAG = "DataStore"
        private const val ONBOARD_KEY = "isOnboardingShowed"
    }

    private object PreferencesKey {
        val isOnboardingShowed = booleanPreferencesKey(name = ONBOARD_KEY)
    }

    private val dataStore = context.dataStore
    private val coroutineContext = SupervisorJob() + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    suspend fun saveOnboard(isShowed: Boolean) = save {
        this[PreferencesKey.isOnboardingShowed] = isShowed
        Log.d(TAG, "isOnboardingShowed saved $isShowed")
    }

    val isOnboardingShowed get() = get(PreferencesKey.isOnboardingShowed)
    val isOnboardingShowedSync get() = getSync(PreferencesKey.isOnboardingShowed)

    /**Сохранение преференса в отедльном скоупе*/
    private suspend inline fun save(crossinline block: suspend MutablePreferences.() -> Unit) {
        scope.launch { dataStore.edit { block(it) } }
    }

    /**
     * Асинхронный геттер для UI
     * @return flow со значениями из преференсов по [ключу] [key]
     */
    private fun <T> get(key: Preferences.Key<T>) = dataStore.data
        .catch { emptyPreferences() }
        .mapNotNull { it[key] }

    /**
     * Синхронный геттер для бизнес логики
     * @return текущее значение по [ключу] [key]
     */
    private fun <T> getSync(key: Preferences.Key<T>) = runBlocking(coroutineContext) { dataStore.data.firstOrNull()?.get(key) }
}