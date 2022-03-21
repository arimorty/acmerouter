package com.takehometest.acmerouter.usecase

import android.text.format.DateUtils
import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.entity.Driver
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
    suspend fun execute(driverId: Int) = withContext(ioDispatcher) {
        var destinationForDriver: Destination? = destinationRepo.getDestinationByDriver(driverId)

        val refreshNeeded: Boolean = destinationForDriver == null
                || !DateUtils.isToday(destinationForDriver.dateAssignedToDriver!!);

        if (refreshNeeded) {
            val destinations: List<Destination> = destinationRepo.getDestinations(true)
            val drivers: List<Driver> = driverRepo.getDrivers(true)

            destinationRepo.updateDestinations(Destination.assignDriversToDestinations(drivers, destinations))

            destinationForDriver = destinationRepo.getDestinationByDriver(driverId)
        }
        destinationForDriver
    }
}
