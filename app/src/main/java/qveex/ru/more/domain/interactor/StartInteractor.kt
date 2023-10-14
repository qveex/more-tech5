package qveex.ru.more.domain.interactor

import qveex.ru.more.data.models.StartFilter
import qveex.ru.more.domain.repository.Repository

class StartInteractor(
    private val repo: Repository
) {

    suspend fun getFilters(): List<StartFilter> = emptyList()

}