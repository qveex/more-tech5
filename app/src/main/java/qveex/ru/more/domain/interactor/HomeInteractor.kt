package qveex.ru.more.domain.interactor

import qveex.ru.more.data.models.Location
import qveex.ru.more.domain.repository.Repository

class HomeInteractor(
    private val repo: Repository
) {

    suspend fun getDepartmentsAndAtmsAround(
        leftTopCoordinate: Location,
        rightBottomCoordinate: Location
    ) = repo.getDepartmentsAndAtmsAround(
        leftTopCoordinate = leftTopCoordinate,
        rightBottomCoordinate = rightBottomCoordinate
    )

}