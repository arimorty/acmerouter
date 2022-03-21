package com.takehometest.acmerouter.repo.driver

import com.takehometest.acmerouter.entity.Driver

/**
 * A repository that serves [Driver] data as a [List].
 */
interface DriverRepo {
    suspend fun getDrivers(refreshCache: Boolean): List<Driver>

    suspend fun getDriverById(driverId: Int): Driver
}
