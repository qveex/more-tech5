package qveex.ru.more.domain.interactor

import qveex.ru.more.data.models.Days
import qveex.ru.more.data.models.Department
import qveex.ru.more.data.models.Entity
import qveex.ru.more.data.models.Location
import qveex.ru.more.data.models.OpenHours
import qveex.ru.more.data.models.Status
import qveex.ru.more.domain.repository.Repository

class DepartmentInteractor(
    private val repo: Repository
) {

    suspend fun getInfo(id: Long) = Department(
        departmentId = 1,
        address = "ул. Пушкина д. 3",
        metroStation = "Сенная площадь",
        status = Status.OPEN,
        distance = 123,
        workload = 50,
        location = Location(.0, .0),
        hasRamp = true,
        legal = listOf(
            Entity(Days.SATURDAY, OpenHours("9:00", "22:00"))
        ),
        individual = listOf(
            Entity(Days.SATURDAY, OpenHours("9:00", "22:00"))
        ),
    )//repo.getDepartmentInfo(id)

}