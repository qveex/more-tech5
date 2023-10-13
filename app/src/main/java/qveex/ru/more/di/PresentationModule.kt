package qveex.ru.more.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import qveex.ru.more.utils.ResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context) =
        ResourceProvider(context)

}