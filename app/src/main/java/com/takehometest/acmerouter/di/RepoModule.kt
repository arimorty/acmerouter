package com.takehometest.acmerouter.di

import com.takehometest.acmerouter.repo.destination.DestinationRepo
import com.takehometest.acmerouter.repo.destination.DestinationRepoImpl
import com.takehometest.acmerouter.repo.destination.DestinationRepoLocalSource
import com.takehometest.acmerouter.repo.destination.DestinationRepoRemoteSource
import com.takehometest.acmerouter.repo.destination.source.DestinationRepoLocalSourceImpl
import com.takehometest.acmerouter.repo.destination.source.DestinationRepoRemoteSourceImpl
import com.takehometest.acmerouter.repo.driver.DriverRepo
import com.takehometest.acmerouter.repo.driver.DriverRepoImpl
import com.takehometest.acmerouter.repo.driver.DriverRepoLocalSource
import com.takehometest.acmerouter.repo.driver.DriverRepoRemoteSource
import com.takehometest.acmerouter.repo.driver.source.DriverRepoLocalSourceImpl
import com.takehometest.acmerouter.repo.driver.source.DriverRepoRemoteSourceImpl
import com.takehometest.acmerouter.usecase.GetDestinationForDriver
import com.takehometest.acmerouter.usecase.GetDriverById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Singleton
    @Provides
    fun provideDriverRepo(
        driverRepoLocalSource: DriverRepoLocalSource,
        driverRepoRemoteSource: DriverRepoRemoteSource,
    ): DriverRepo {
        return DriverRepoImpl(driverRepoLocalSource, driverRepoRemoteSource)
    }

    @Singleton
    @Provides
    fun provideDriverRepoLocalSource(): DriverRepoLocalSource {
        return DriverRepoLocalSourceImpl()
    }

    @Singleton
    @Provides
    fun provideDriverRepoRemoteSource(): DriverRepoRemoteSource {
        return DriverRepoRemoteSourceImpl()
    }

    @Singleton
    @Provides
    fun provideDestinationRepo(
        destinationRepoLocalSource: DestinationRepoLocalSource,
        destinationRepoRemoteSource: DestinationRepoRemoteSource,
    ): DestinationRepo {
        return DestinationRepoImpl(destinationRepoLocalSource, destinationRepoRemoteSource)
    }

    @Singleton
    @Provides
    fun provideDestinationRepoLocalSource(): DestinationRepoLocalSource {
        return DestinationRepoLocalSourceImpl()
    }

    @Singleton
    @Provides
    fun provideDestinationRepoRemoteSource(): DestinationRepoRemoteSource {
        return DestinationRepoRemoteSourceImpl()
    }

    @Singleton
    @Provides
    fun provideFindDestinationForDriver(
        driverRepo: DriverRepo,
        destinationRepo: DestinationRepo,
    ): GetDestinationForDriver {
        return GetDestinationForDriver(driverRepo, destinationRepo)
    }

    @Singleton
    @Provides
    fun provideGetDriverById(
        driverRepo: DriverRepo,
    ): GetDriverById {
        return GetDriverById(driverRepo)
    }
}
