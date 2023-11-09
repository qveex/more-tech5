package qveex.ru.more.domain.interactor

import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.RequestFilter
import qveex.ru.more.domain.repository.Repository
import javax.inject.Inject

class StartInteractor @Inject constructor(
    private val repo: Repository
) {

    suspend fun getDepartmentFilters() = repo.getOfficesFilters()

    suspend fun getClientFilters() = repo.getClientsFilters()

    suspend fun findInfo(
        services: List<Long>,
        officeTypes: List<Long>,
        clientTypes: List<Long>,
        hasRamp: Boolean
    ) = repo.getDepartmentsAndAtmsAround(
        requestFilter = RequestFilter(
            leftTopCoordinate = null,
            rightBottomCoordinate = null,
            curUserCoordinate = Location(59.56, 30.1850),
            services = services,
            officeTypes = officeTypes,
            clientTypes = clientTypes,
            hasRamp = hasRamp,
        )
    )

}