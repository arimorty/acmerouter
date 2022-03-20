package com.takehometest.acmerouter.repo.driver.source.remote

import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.repo.common.source.remote.AcmeService
import com.takehometest.acmerouter.repo.driver.DriverRepoRemoteSource
import javax.inject.Inject

/**
 * A [DriverRepoRemoteSource] that returns data from a service.
 */
class DriverRepoRemoteSourceImpl @Inject constructor(
    private val acmeService: AcmeService
) : DriverRepoRemoteSource {
    override suspend fun getDrivers(): List<Driver> = map(acmeService.getAll().drivers)
}

fun map(from: List<String>): List<Driver> {
    return from.map {
        Driver(id = null, fullName = it)
    }
}
