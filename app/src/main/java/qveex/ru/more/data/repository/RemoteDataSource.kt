package qveex.ru.more.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import qveex.ru.more.data.models.Info
import qveex.ru.more.data.models.RequestFilter
import qveex.ru.more.data.remote.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val api: Api
) {

    private val coroutineContext = Dispatchers.IO + SupervisorJob()
    private val scope = CoroutineScope(coroutineContext)

    suspend fun getAtmInfo(atmId: Long) = withContext(coroutineContext) {
        runCatching {
            api.getAtmInfo(atmId).body()
        }.onFailure { Log.i("REMOTE", "remote error = $it") }.getOrNull()
    }

    suspend fun getDepartmentInfo(departmentId: Long) = withContext(coroutineContext) {
        runCatching {
            api.getDepartmentInfo(departmentId).body()
        }.onFailure { Log.i("REMOTE", "remote error = $it") }.getOrNull()
    }

    suspend fun getDepartmentsAndAtmsAround(
        filter: RequestFilter
    ) = withContext(coroutineContext) {
        runCatching {
            Log.i("REMOTE", filter.toString())
            api.getDepartmentsAndAtmsAround(filter = filter).takeIf { it.isSuccessful }?.body()
                ?: Info(atms = emptyList(), departments = emptyList())
        }.onFailure { Log.i("REMOTE", "remote error = $it") }.getOrNull()
    }.also { Log.i("REMOTE", it.toString()) }


    suspend fun getServicesFilters() = withContext(coroutineContext) {
        runCatching {
            api.getServicesFilters().takeIf { it.isSuccessful }?.body() ?: emptyList()
        }.onFailure { Log.i("REMOTE", "remote error = $it") }.getOrNull()
    }

    suspend fun getOfficesFilters() = withContext(coroutineContext) {
        runCatching {
            api.getOfficesFilters().takeIf { it.isSuccessful }?.body() ?: emptyList()
        }.onFailure { Log.i("REMOTE", "remote error = $it") }.getOrNull()
    }

    suspend fun getClientsFilters() = withContext(coroutineContext) {
        runCatching {
            api.getClientsFilters().takeIf { it.isSuccessful }?.body() ?: emptyList()
        }.onFailure { Log.i("REMOTE", "remote error = $it") }.getOrNull()
    }

}