package qveex.ru.more.domain.interactor

import android.util.Log
import qveex.ru.more.data.models.Filter
import qveex.ru.more.data.models.Info
import qveex.ru.more.data.models.RequestFilter
import qveex.ru.more.domain.repository.Repository

class StartInteractor(
    private val repo: Repository
) {

    suspend fun getFilters(): List<Filter> =
        //repo.getServicesFilters().also { Log.i("StartInteractor", it.toString()) } +
                repo.getClientsFilters() +
                repo.getOfficesFilters()

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