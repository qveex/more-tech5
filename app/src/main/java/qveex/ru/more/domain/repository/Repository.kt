package qveex.ru.more.domain.repository

import qveex.ru.more.data.models.Location
import qveex.ru.more.data.repository.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource
) {

    suspend fun getAtmInfo(atmId: Long) = remote.getAtmInfo(atmId)
    suspend fun getDepartmentInfo(departmentId: Long) = remote.getDepartmentInfo(departmentId)

    suspend fun getDepartmentsAndAtmsAround(
        leftTopCoordinate: Location,
        rightBottomCoordinate: Location
    ) = remote.getDepartmentsAndAtmsAround(
        leftTopCoordinate,
        rightBottomCoordinate
    )

}