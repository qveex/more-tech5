package qveex.ru.more.domain.interactor

import android.util.Log
import qveex.ru.more.data.models.Filter
import qveex.ru.more.data.models.Info
import qveex.ru.more.data.models.RequestFilter
import qveex.ru.more.domain.repository.Repository

class StartInteractor(
    private val repo: Repository
) {

    suspend fun getDepartmentFilters() = repo.getOfficesFilters()

    suspend fun getClientFilters() = repo.getClientsFilters()

    suspend fun findInfo() {}/*: Info = repo.getDepartmentsAndAtmsAround(
        requestFilter = RequestFilter(
            leftTopCoordinate = leftTopCoordinate,
            rightBottomCoordinate = rightBottomCoordinate,
            curUserCoordinate = curUserCoordinate,
            services = services,
            officeTypes = officeTypes,
            clientTypes = clientTypes,
            hasRamp = hasRamp,
        )
    )*/

}