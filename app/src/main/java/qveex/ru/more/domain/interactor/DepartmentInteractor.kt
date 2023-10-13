package qveex.ru.more.domain.interactor

import qveex.ru.more.domain.repository.Repository

class DepartmentInteractor(
    private val repo: Repository
) {

    suspend fun getInfo(id: Long) = repo.getDepartmentInfo(id)

}