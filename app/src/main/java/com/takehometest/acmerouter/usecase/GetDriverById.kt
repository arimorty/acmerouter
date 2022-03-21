package com.takehometest.acmerouter.usecase

import com.takehometest.acmerouter.repo.driver.DriverRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDriverById @Inject constructor(
    private val driverRepo: DriverRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun execute(driverId: Int) = withContext(ioDispatcher) {
        driverRepo.getDriverById(driverId)
    }
}
