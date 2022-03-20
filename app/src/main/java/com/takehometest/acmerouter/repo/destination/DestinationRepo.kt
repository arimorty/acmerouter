package com.takehometest.acmerouter.repo.destination

import com.takehometest.acmerouter.entity.Destination

/**
 * A repository that serves [Destination] data as a [List].
 */
interface DestinationRepo {
    suspend fun getDestinations(refreshCache: Boolean): List<Destination>

    suspend fun getDestinationByDriver(driverId: String): Destination

    suspend fun updateDestinations(destinations: List<Destination>)
}
