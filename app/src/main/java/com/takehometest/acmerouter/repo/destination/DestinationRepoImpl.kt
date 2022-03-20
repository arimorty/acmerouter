package com.takehometest.acmerouter.repo.destination

import com.takehometest.acmerouter.entity.Destination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DestinationRepoLocalSource {
    suspend fun getDestinations(): List<Destination>

    suspend fun getDestinationByDriver(driverId: String): Destination

    suspend fun saveDestinations(destinations: List<Destination>)
}

interface DestinationRepoRemoteSource {
    suspend fun getDestinations(): List<Destination>
}

/**
 * A default implementation of a [DestinationRepo] that serves data first from local storage, if exist,
 * or fetches data from a remote source if local data does not exist, or if it is forced to refresh.
 *
 * @param localSource the data source for local storage/cache.
 * @param remoteSource the data source for remote data.
 * @param ioDispatcher the [CoroutineDispatcher] to execute data ops with.
 */
class DestinationRepoImpl(
    private val localSource: DestinationRepoLocalSource,
    private val remoteSource: DestinationRepoRemoteSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DestinationRepo {
    override suspend fun getDestinations(refreshCache: Boolean): List<Destination> =
        withContext(ioDispatcher) {
            if (refreshCache) {
                getDestinationsAndCache()
            } else {
                val local = localSource.getDestinations()
                local.ifEmpty {
                    getDestinationsAndCache()
                }
            }
        }

    override suspend fun getDestinationByDriver(driverId: String) = withContext(ioDispatcher) {
        localSource.getDestinationByDriver(driverId)
    }

    override suspend fun updateDestinations(destinations: List<Destination>): Unit =
        withContext(ioDispatcher) {
            localSource.saveDestinations(destinations)
        }

    private suspend fun getDestinationsAndCache(): List<Destination> = withContext(ioDispatcher) {
        val remoteData = remoteSource.getDestinations()
        localSource.saveDestinations(remoteData)
        remoteData
    }
}
