package qveex.ru.more.data.remote

import retrofit2.http.GET

interface Api {

    @GET
    suspend fun get()

}