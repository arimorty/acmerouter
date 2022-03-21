package com.takehometest.acmerouter.repo.driver.source.local

import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.repo.driver.DriverRepoLocalSource
import javax.inject.Inject

/**
 * A [DriverRepoLocalSource] that saves and returns Destination data using a Dao object.
 */
class DriverRepoLocalSourceImpl @Inject constructor(
    private var driverDao: DriverDao
) : DriverRepoLocalSource {
    override suspend fun getDrivers(): List<Driver> =
        mapFromEntities(driverDao.getDrivers())

    override suspend fun getDriverById(driverId: Int): Driver =
        mapFromEntity(driverDao.getDriverById(driverId = driverId))

    override suspend fun saveDrivers(drivers: List<Driver>) =
        driverDao.saveDrivers(mapToEntities(drivers))
}

private fun mapFromEntity(driverEntity: DriverEntity): Driver {
    return Driver(
        id = driverEntity.id,
        fullName = driverEntity.fullName,
    )
}

private fun mapFromEntities(driverEntities: List<DriverEntity>): List<Driver> {
    return driverEntities.map {
        mapFromEntity(it)
    }
}

private fun mapToEntities(drivers: List<Driver>): List<DriverEntity> {
    return drivers.map {
        DriverEntity(
            id = it.id,
            fullName = it.fullName,
        )
    }
}
