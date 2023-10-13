package qveex.ru.more.domain.repository

import qveex.ru.more.data.repository.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource
) {



}