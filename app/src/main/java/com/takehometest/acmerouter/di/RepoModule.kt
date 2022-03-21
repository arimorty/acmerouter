package com.takehometest.acmerouter.di

import android.content.Context
import androidx.room.Room
import com.takehometest.acmerouter.remote.AssetRequestInterceptor
import com.takehometest.acmerouter.repo.common.source.local.AcmeDatabase
import com.takehometest.acmerouter.repo.common.source.remote.AcmeService
import com.takehometest.acmerouter.repo.destination.DestinationRepo
import com.takehometest.acmerouter.repo.destination.DestinationRepoImpl
import com.takehometest.acmerouter.repo.destination.DestinationRepoLocalSource
import com.takehometest.acmerouter.repo.destination.DestinationRepoRemoteSource
import com.takehometest.acmerouter.repo.destination.source.local.DestinationDao
import com.takehometest.acmerouter.repo.destination.source.local.DestinationRepoLocalSourceImpl
import com.takehometest.acmerouter.repo.destination.source.remote.DestinationRepoRemoteSourceImpl
import com.takehometest.acmerouter.repo.driver.DriverRepo
import com.takehometest.acmerouter.repo.driver.DriverRepoImpl
import com.takehometest.acmerouter.repo.driver.DriverRepoLocalSource
import com.takehometest.acmerouter.repo.driver.DriverRepoRemoteSource
import com.takehometest.acmerouter.repo.driver.source.local.DriverDao
import com.takehometest.acmerouter.repo.driver.source.local.DriverRepoLocalSourceImpl
import com.takehometest.acmerouter.repo.driver.source.remote.DriverRepoRemoteSourceImpl
import com.takehometest.acmerouter.usecase.GetDestinationForDriver
import com.takehometest.acmerouter.usecase.GetDriverById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideDriverRepoLocalSource(driverDao: DriverDao): DriverRepoLocalSource {
        return DriverRepoLocalSourceImpl(driverDao)
    }

    @Singleton
    @Provides
    fun provideDriverRepoRemoteSource(acmeService: AcmeService): DriverRepoRemoteSource {
        return DriverRepoRemoteSourceImpl(acmeService)
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
    fun provideDestinationRepoLocalSource(destinationDao: DestinationDao): DestinationRepoLocalSource {
        return DestinationRepoLocalSourceImpl(destinationDao)
    }

    @Singleton
    @Provides
    fun provideDestinationRepoRemoteSource(acmeService: AcmeService): DestinationRepoRemoteSource {
        return DestinationRepoRemoteSourceImpl(acmeService)
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

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context) = OkHttpClient.Builder()
        .addInterceptor(AssetRequestInterceptor(context))
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl("http://10.0.2.2:8080/")
        .build()

    @Provides
    @Singleton
    fun provideAcmeService(retrofit: Retrofit) = retrofit.create(AcmeService::class.java)

    @Provides
    @Singleton
    fun provideAcmeDatabase(@ApplicationContext appContext: Context): AcmeDatabase {
        return Room.databaseBuilder(
            appContext,
            AcmeDatabase::class.java,
            AcmeDatabase::class.java.simpleName
        ).build()
    }

    @Provides
    fun provideDestinationDao(acmeDatabase: AcmeDatabase): DestinationDao {
        return acmeDatabase.destinationDao;
    }

    @Provides
    fun provideDriverDao(acmeDatabase: AcmeDatabase): DriverDao {
        return acmeDatabase.driverDao;
    }
}
