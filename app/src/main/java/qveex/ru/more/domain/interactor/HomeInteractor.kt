package qveex.ru.more.domain.interactor

import android.util.Log
import qveex.ru.more.InfoParams
import qveex.ru.more.data.models.Atm
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.Entity
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.Info
import qveex.ru.more.data.models.OpenHours
import qveex.ru.more.data.models.RequestFilter
import qveex.ru.more.data.models.Status
import qveex.ru.more.domain.repository.Repository

class HomeInteractor(
    private val repo: Repository
) {

    suspend fun getDepartmentsAndAtmsAround(
        leftTopCoordinate: Location? = null,
        rightBottomCoordinate: Location? = null,
        curLocation: Location,
        infoParams: InfoParams?
    ) = /*Info(
        atms = listOf(
            Atm(
                id = 1,
                address = "ул. Пушкина д. 3",
                metroStation = "Сенная площадь",
                allDay = true,
                distance = 123,
                coordinates = Location(59.929560, 30.296671),
                services = emptyList(),
            )
        ),
        departments = listOf(
            Department(
                id = 1,
                address = "ул. Пушкина д. 3",
                metroStation = "Сенная площадь",
                status = Status.CLOSED,
                distance = 123,
                workload = 50,
                coordinates = Location(59.930182, 30.362152 ),
                hasRamp = true,
                withVIP = true,
                legal = listOf(
                    Entity(
                        Days.SUNDAY,
                        OpenHours("9:00", "22:00"),
                        mapOf(
                            "1" to 10,
                            "2" to 20,
                            "3" to 30,
                            "4" to 40,
                            "5" to 50,
                            "6" to 60,
                            "7" to 70,
                            "8" to 80,
                        )
                    )
                ),
                individual = listOf(
                    Entity(
                        Days.SUNDAY,
                        OpenHours("9:00", "22:00"),
                        mapOf(
                            "1" to 10,
                            "2" to 20,
                            "3" to 30,
                            "4" to 40,
                            "5" to 50,
                            "6" to 60,
                            "7" to 70,
                            "8" to 80,
                        )
                    )
                ),
                services = emptyList(),
                clients = emptyList()
            )
        )
    ) */repo.getDepartmentsAndAtmsAround(
        requestFilter = RequestFilter(
            leftTopCoordinate = leftTopCoordinate,
            rightBottomCoordinate = rightBottomCoordinate,
            curUserCoordinate = curLocation,
            services = infoParams?.serviceFilters ?: emptyList(),
            officeTypes = infoParams?.departmentFilters ?: emptyList(),
            clientTypes = infoParams?.clientFilters ?: emptyList(),
            hasRamp = infoParams?.hasRamp ?: false
        ).also { Log.i("REMOTE", "filter = $it") }
    )

}