package qveex.ru.more.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import qveex.ru.more.domain.interactor.DepartmentInteractor
import qveex.ru.more.domain.interactor.HomeInteractor
import qveex.ru.more.domain.interactor.Interactors
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
    )

}