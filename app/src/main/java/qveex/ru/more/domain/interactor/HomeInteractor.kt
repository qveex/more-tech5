package qveex.ru.more.domain.interactor

import qveex.ru.more.data.models.Atm
import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.Entity
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.Info
import qveex.ru.more.data.models.OpenHours
import qveex.ru.more.data.models.Status
import qveex.ru.more.domain.repository.Repository

class HomeInteractor(
    private val repo: Repository
) {

    suspend fun getDepartmentsAndAtmsAround(
        leftTopCoordinate: Location,
        rightBottomCoordinate: Location
    ) = Info(
        atms = listOf(
            Atm(
                atmId = 1,
                address = "ул. Пушкина д. 3",
                metroStation = "Сенная площадь",
                allDay = true,
                distance = 123,
                coordinates = Location(.0, .0),
                services = emptyList(),
            )
        ),
        departments = listOf(
            Department(
                departmentId = 1,
                address = "ул. Пушкина д. 3",
                metroStation = "Сенная площадь",
                status = Status.CLOSED,
                distance = 123,
                workload = 50,
                coordinates = Location(.0, .0),
                hasRamp = true,
                withVIP = true,
                legal = listOf(
                    Entity(Days.SATURDAY, OpenHours("9:00", "22:00"))
                ),
                individual = listOf(
                    Entity(Days.SATURDAY, OpenHours("9:00", "22:00"))
                ),
            )
        )
    ) /*repo.getDepartmentsAndAtmsAround(
        leftTopCoordinate = leftTopCoordinate,
        rightBottomCoordinate = rightBottomCoordinate
    )*/

}