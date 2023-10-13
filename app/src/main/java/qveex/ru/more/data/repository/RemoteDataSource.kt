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

    suspend fun get() = scope.async { api.get() }.await()

}