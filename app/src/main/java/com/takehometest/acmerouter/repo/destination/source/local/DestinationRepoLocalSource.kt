package com.takehometest.acmerouter.repo.destination.source.local

import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.repo.destination.DestinationRepoLocalSource
import javax.inject.Inject

/**
 * A [DestinationRepoLocalSource] that saves and returns Destination data using a Dao object.
 */
class DestinationRepoLocalSourceImpl @Inject constructor(
    private var destinationDao: DestinationDao
) : DestinationRepoLocalSource {
    override suspend fun getDestinations(): List<Destination> =
        mapFromEntities(destinationDao.getDestinations())

    override suspend fun getDestinationByDriver(driverId: Int): Destination? =
        mapFromEntity(destinationDao.getDestinationByDriver(driverId = driverId))

    override suspend fun saveDestinations(destinations: List<Destination>) =
        destinationDao.saveDestinations(mapToEntities(destinations))
}

private fun mapFromEntity(destinationEntity: DestinationEntity): Destination? {
    if (destinationEntity != null) {
        return Destination(
            id = destinationEntity.id,
            street = destinationEntity.street,
            streetNumber = destinationEntity.streetNumber,
            assignedToDriverId = destinationEntity.assignedToDriverId,
            dateAssignedToDriver = destinationEntity.dateAssignedToDriver
        )
    } else {
        return null
    }
}

private fun mapFromEntities(destinationEntities: List<DestinationEntity>): List<Destination> {
    return destinationEntities.map {
        mapFromEntity(it)!!
    }
}

private fun mapToEntities(destinations: List<Destination>): List<DestinationEntity> {
    return destinations.map {
        DestinationEntity(
            id = it.id,
            street = it.street,
            streetNumber = it.streetNumber,
            assignedToDriverId = it.assignedToDriverId,
            dateAssignedToDriver = it.dateAssignedToDriver
        )
    }
}