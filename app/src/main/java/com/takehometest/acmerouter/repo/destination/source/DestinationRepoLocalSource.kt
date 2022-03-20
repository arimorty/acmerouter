package com.takehometest.acmerouter.repo.destination.source

import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.repo.destination.DestinationRepoLocalSource
import javax.inject.Inject

/**
 * A blank [DestinationRepoLocalSource] that simply returns empty data and doesn't save anything
 * to storage.
 */
class DestinationRepoLocalSourceImpl
@Inject constructor() : DestinationRepoLocalSource {
    override suspend fun getDestinations(): List<Destination> = listOf()

    override suspend fun getDestinationByDriver(driverId: String): Destination {
        return Destination(street = "two street", streetNumber = "2")
    }

    override suspend fun saveDestinations(destination: List<Destination>) {

    }
}
