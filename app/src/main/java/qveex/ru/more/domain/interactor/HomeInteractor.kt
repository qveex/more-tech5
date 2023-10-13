package qveex.ru.more.domain.interactor

import qveex.ru.more.domain.repository.Repository

class HomeInteractor(
    private val repo: Repository
) {

    suspend fun getDepartmentsAndAtmsAround(
        latitude: Double,
        longitude: Double,
        radius: Double
    ) = repo.getDepartmentsAndAtmsAround(
        latitude = latitude,
        longitude = longitude,
        radius = radius
    )

}