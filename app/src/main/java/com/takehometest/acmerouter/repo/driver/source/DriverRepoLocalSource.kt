package com.takehometest.acmerouter.repo.driver.source

import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.repo.driver.DriverRepoLocalSource
import javax.inject.Inject

/**
 * A blank [DriverRepoLocalSource] that simply returns empty data and doesn't save anything
 * to storage.
 */
class DriverRepoLocalSourceImpl
@Inject constructor() : DriverRepoLocalSource {
    override suspend fun getDrivers(): List<Driver> = listOf()

    override suspend fun getDriverById(driverId: String): Driver {
        return Driver(id = "1", fullName = "one")
    }

    override suspend fun saveDrivers(driver: List<Driver>): List<Driver> = driver
}
