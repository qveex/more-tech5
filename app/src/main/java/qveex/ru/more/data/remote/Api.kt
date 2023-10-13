package qveex.ru.more.data.remote

import qveex.ru.more.data.models.Atm
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.Objects
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("api/v1/departments/{departmentId}")
    suspend fun getDepartmentInfo(
        @Path("departmentId") departmentId: Long
    ): Department

    @GET("api/v1/atms/{atmId}")
    suspend fun getAtmInfo(
        @Path("atmId") atmId: Long
    ): Atm

    @GET("api/v1/objects")
    suspend fun getDepartmentsAndAtmsAround(
        @Query("leftTopCoordinate") leftTopCoordinate : Location,
        @Query("rightBottomCoordinate") rightBottomCoordinate : Location
    ): Objects

}