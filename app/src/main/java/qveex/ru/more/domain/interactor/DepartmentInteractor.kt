package qveex.ru.more.domain.interactor

import qveex.ru.more.domain.repository.Repository
import javax.inject.Inject

class DepartmentInteractor @Inject constructor(
    private val repo: Repository
) {

    suspend fun getAtmInfo(atmId: Long) = repo.getAtmInfo(atmId)
    suspend fun getDepartmentInfo(departmentId: Long) = repo.getDepartmentInfo(departmentId)
    /*Department(
        id = 1,
        address = "ул. Пушкина д. 3",
        metroStation = "Сенная площадь",
        status = Status.OPEN,
        distance = 123,
        workload = 50,
        coordinates = Location(.0, .0),
        hasRamp = true,
        withVIP = true,
        legal = listOf(
            Entity(Days.SATURDAY, OpenHours("9:00", "22:00"))
        ),
        individual = listOf(
            Entity(Days.MONDAY, OpenHours("9:00", "22:00")),
            Entity(Days.TUESDAY, OpenHours("9:00", "22:00")),
            Entity(Days.WEDNESDAY, OpenHours("9:00", "22:00")),
            Entity(Days.THURSDAY, OpenHours("9:00", "22:00")),
            Entity(Days.FRIDAY, OpenHours("9:00", "22:00")),
            Entity(Days.SATURDAY, OpenHours("11:00", "20:00")),
            Entity(Days.SUNDAY, OpenHours("12:00", "20:00"))
        ),
    )*/

}