package com.takehometest.acmerouter.usecase

import android.text.format.DateUtils
import com.takehometest.acmerouter.common.MainCoroutineRule
import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.repo.destination.DestinationRepo
import com.takehometest.acmerouter.repo.driver.DriverRepoImpl
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDestinationForDriverTest {

    private val driverRepo: DriverRepoImpl = mockk()

    private lateinit var testDrivers: List<Driver>

    private val destinationRepo: DestinationRepo = mockk()

    private lateinit var testDestinations: List<Destination>

    private lateinit var getDestinationForDriver: GetDestinationForDriver

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        getDestinationForDriver = GetDestinationForDriver(
            driverRepo, destinationRepo, Dispatchers.Main
        )
        testDestinations = listOf(
            Destination(
                id = 1,
                streetNumber = "a",
                street = "address_one",
            ),
            Destination(
                id = 2,
                streetNumber = "b",
                street = "address_two",
            ),
        )
        testDrivers = listOf(
            Driver(
                id = 1,
                fullName = "driver_name_one",
            ),
            Driver(
                id = 2,
                fullName = "driver_name_two",
            )
        )
    }

    @Test
    fun findDestination_returnsCorrectDestination_whenDestinationExistsForDriver_andItWasAssignedToday() =
        mainCoroutineRule.runBlockingTest {
            coEvery {
                destinationRepo.getDestinationByDriver(1)
            } returns Destination(
                id = 1,
                streetNumber = "a",
                street = "address_one",
                assignedToDriverId = 1
            )
            mockkStatic(DateUtils::class)
            coEvery {
                DateUtils.isToday(any())
            } returns true

            val result: Destination? = getDestinationForDriver.execute(1)

            assertThat(result?.assignedToDriverId, IsEqual(1))
        }

    //TODO: add more test coverage
}
