package qveex.ru.more.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import qveex.ru.more.data.models.Location
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
        leftTopCoordinate: Location,
        rightBottomCoordinate: Location,
    ) = scope.async {
        api.getDepartmentsAndAtmsAround(
            leftTopCoordinate = leftTopCoordinate,
            rightBottomCoordinate = rightBottomCoordinate,
        )
    }.await()

}