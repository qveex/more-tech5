package qveex.ru.more.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("api/departments/{departmentId}")
    suspend fun getDepartmentInfo(
        @Path("departmentId") departmentId: Long
    )

    @GET("api/atms/{atmId}")
    suspend fun getAtmInfo(
        @Path("atmId") atmId: Long
    )

    @GET("")
    suspend fun getDepartmentsAndAtmsAround(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Double
    )

}