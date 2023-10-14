package qveex.ru.more.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import qveex.ru.more.domain.interactor.DepartmentInteractor
import qveex.ru.more.domain.interactor.HomeInteractor
import qveex.ru.more.domain.interactor.Interactors
import qveex.ru.more.domain.interactor.OnboardingInteractor
import qveex.ru.more.domain.interactor.StartInteractor
import qveex.ru.more.domain.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideInteractors(repository: Repository) = Interactors(
        homeInteractor = HomeInteractor(repository),
        departmentInteractor = DepartmentInteractor(repository),
        onboardingInteractor = OnboardingInteractor(repository),
        startInteractor = StartInteractor(repository)
    )

    @Provides
    fun provideHomeInteractor(interactors: Interactors) = interactors.homeInteractor

    @Provides
    fun provideDepartmentsInteractor(interactors: Interactors) = interactors.departmentInteractor

    @Provides
    fun provideOnboardingInteractor(interactors: Interactors) = interactors.onboardingInteractor

    @Provides
    fun provideStartInteractor(interactors: Interactors) = interactors.startInteractor
}