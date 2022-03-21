package com.takehometest.acmerouter.repo.driver

import com.takehometest.acmerouter.entity.Driver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Local source, where data changes from the app are saved to, thus simulating a fully functional
 * remote source.
 */
interface DriverRepoLocalSource {
    suspend fun getDrivers(): List<Driver>

    suspend fun getDriverById(driverId: Int): Driver

    suspend fun saveDrivers(drivers: List<Driver>)
}

/**
 * Remote source, readonly for now, so only a accessor method is needed.
 */
interface DriverRepoRemoteSource {
    suspend fun getDrivers(): List<Driver>
}

/**
 * A default implementation of a [DriverRepo] that serves data first from local storage, if exist,
 * or fetches data from a remote source if local data does not exist, or if it is forced to refresh.
 *
 * @param localSource the data source for local storage/cache.
 * @param remoteSource the data source for remote data.
 * @param ioDispatcher the [CoroutineDispatcher] to execute data ops with.
 */
class DriverRepoImpl(
    private val localSource: DriverRepoLocalSource,
    private val remoteSource: DriverRepoRemoteSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DriverRepo {

    /**
     * @param refreshCache true, to force update even if local storage exist.
     */
    override suspend fun getDrivers(refreshCache: Boolean): List<Driver> =
        withContext(ioDispatcher) {
            if (refreshCache) {
                getDriversAndCache()
            } else {
                val local = localSource.getDrivers()
                local.ifEmpty {
                    getDriversAndCache()
                }
            }
        }

    override suspend fun getDriverById(driverId: Int): Driver {
        return localSource.getDriverById(driverId)
    }

    private suspend fun getDriversAndCache(): List<Driver> =
        withContext(ioDispatcher) {
            val remoteData = remoteSource.getDrivers()
            localSource.saveDrivers(remoteData)
            localSource.getDrivers()
        }
}
