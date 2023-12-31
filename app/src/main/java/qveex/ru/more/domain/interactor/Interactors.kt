package qveex.ru.more.domain.interactor

data class Interactors(
    val homeInteractor: HomeInteractor,
    val departmentInteractor: DepartmentInteractor,
    val onboardingInteractor: OnboardingInteractor,
    val startInteractor: StartInteractor
)
