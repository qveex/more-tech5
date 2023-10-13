package qveex.ru.more.domain.repository

import qveex.ru.more.data.repository.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource
) {

    suspend fun getAtmInfo(atmId: Long) = remote.getAtmInfo(atmId)
    suspend fun getDepartmentInfo(departmentId: Long) = remote.getDepartmentInfo(departmentId)

    suspend fun getDepartmentsAndAtmsAround(
        latitude: Double,
        longitude: Double,
        radius: Double
    ) = remote.getDepartmentsAndAtmsAround(
        latitude,
        longitude,
        radius
    )

}