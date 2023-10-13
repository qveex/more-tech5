package qveex.ru.more.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
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
        latitude: Double,
        longitude: Double,
        radius: Double
    ) = scope.async {
        api.getDepartmentsAndAtmsAround(
            latitude = latitude,
            longitude = longitude,
            radius = radius
        )
    }.await()

}