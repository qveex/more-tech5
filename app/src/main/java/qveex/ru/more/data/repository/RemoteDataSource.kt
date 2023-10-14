package qveex.ru.more.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import qveex.ru.more.data.models.RequestFilter
import qveex.ru.more.data.remote.Api
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: Api
) {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend fun getAtmInfo(atmId: Long) =
        scope.async { api.getAtmInfo(atmId) }.await()
    suspend fun getDepartmentInfo(departmentId: Long) =
        scope.async { api.getDepartmentInfo(departmentId) }.await()
    suspend fun getDepartmentsAndAtmsAround(
        filter: RequestFilter
    ) = scope.async {
        api.getDepartmentsAndAtmsAround(filter = filter)
    }.await()

    suspend fun getServicesFilters() = scope.async { api.getServicesFilters() }.await()
    suspend fun getOfficesFilters() = scope.async { api.getOfficesFilters() }.await()
    suspend fun getClientsFilters() = scope.async { api.getClientsFilters() }.await()

}