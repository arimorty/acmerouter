package com.takehometest.acmerouter.repo.driver.source

import android.util.Log
import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.repo.driver.DriverRepoRemoteSource
import javax.inject.Inject

/**
 * A [DriverRepoRemoteSource] that returns mock data.
 */
class DriverRepoRemoteSourceImpl
@Inject constructor() : DriverRepoRemoteSource {
    override suspend fun getDrivers(): List<Driver> = generateDrivers()
}

private fun generateDrivers(): List<Driver> {
    val items = mutableListOf<Driver>()
    items.add(Driver(id = "1", fullName = "one"))
    items.add(Driver(id = "2", fullName = "two"))
    Log.d("Items: ", items.toString())//TODO: abstract out logging
    return items
}
