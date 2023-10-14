package qveex.ru.more.data.remote

import qveex.ru.more.data.models.Atm
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.Filter
import qveex.ru.more.data.models.Info
import qveex.ru.more.data.models.RequestFilter
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("departments/{departmentId}")
    suspend fun getDepartmentInfo(
        @Path("departmentId") departmentId: Long
    ): Response<Department>

    @GET("atms/{atmId}")
    suspend fun getAtmInfo(
        @Path("atmId") atmId: Long
    ): Response<Atm>

    @POST("list")
    suspend fun getDepartmentsAndAtmsAround(
        @Body filter: RequestFilter
    ): Response<Info>

    @GET("filter/services")
    suspend fun getServicesFilters(): Response<List<Filter>>

    @GET("filter/clients")
    suspend fun getClientsFilters(): Response<List<Filter>>

    @GET("filter/offices")
    suspend fun getOfficesFilters(): Response<List<Filter>>

}