package com.takehometest.acmerouter.usecase

import android.text.format.DateUtils
import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.entity.assignDriversToDestinations
import com.takehometest.acmerouter.repo.destination.DestinationRepo
import com.takehometest.acmerouter.repo.driver.DriverRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDestinationForDriver @Inject constructor(
    private val driverRepo: DriverRepo,
    private val destinationRepo: DestinationRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun execute(driverId: String) = withContext(ioDispatcher) {
        var destinationForDriver: Destination = destinationRepo.getDestinationByDriver(driverId)

        val isNewDay: Boolean = !DateUtils.isToday(destinationForDriver.dateAssignedToDriver!!)

        val refreshNeeded: Boolean = destinationForDriver == null || isNewDay;

        if (refreshNeeded) {
            var destinations: List<Destination> = destinationRepo.getDestinations(true)
            val drivers: List<Driver> = driverRepo.getDrivers(true)

            destinations = assignDriversToDestinations(drivers, destinations)
            destinationRepo.updateDestinations(destinations)

            destinationForDriver = destinationRepo.getDestinationByDriver(driverId)
        }
        destinationForDriver
    }
}
