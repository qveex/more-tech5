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

class RemoteDataSource @Inject constructor(
    private val api: Api
) {

    private val coroutineContext = Dispatchers.IO + SupervisorJob()
    private val scope = CoroutineScope(coroutineContext)

    suspend fun getAtmInfo(atmId: Long) = withContext(coroutineContext) {
        api.getAtmInfo(atmId).body()
    }

    suspend fun getDepartmentInfo(departmentId: Long) = withContext(coroutineContext) {
        api.getDepartmentInfo(departmentId).body()
    }
    suspend fun getDepartmentsAndAtmsAround(
        filter: RequestFilter
    ) = withContext(coroutineContext) {
        Log.i("REMOTE", filter.toString())
        api.getDepartmentsAndAtmsAround(filter = filter).takeIf { it.isSuccessful }?.body()
            ?: Info(atms = emptyList(), departments = emptyList())
    }.also { Log.i("REMOTE", it.toString()) }

    suspend fun getServicesFilters() = withContext(coroutineContext) {
        api.getServicesFilters().takeIf { it.isSuccessful }?.body() ?: emptyList()
    }
    suspend fun getOfficesFilters() = withContext(coroutineContext) {
        api.getOfficesFilters().takeIf { it.isSuccessful }?.body() ?: emptyList()
    }
    suspend fun getClientsFilters() = withContext(coroutineContext) {
        api.getClientsFilters().takeIf { it.isSuccessful }?.body() ?: emptyList()
    }

}