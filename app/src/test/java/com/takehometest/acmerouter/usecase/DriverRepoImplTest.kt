package com.takehometest.acmerouter.usecase

import com.takehometest.acmerouter.common.MainCoroutineRule
import com.takehometest.acmerouter.repo.destination.DestinationRepo
import com.takehometest.acmerouter.entity.Destination
import io.mockk.coEvery
import io.mockk.mockk
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

    private var destinationRepo: DestinationRepo = mockk()

    private lateinit var testDestinations: List<Destination>

    private lateinit var getDestinationForDriver: GetDestinationForDriver

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        /*getDestinationForDriver = GetDestinationForDriver(
            destinationRepo, Dispatchers.Main
        )
        testDestinations = listOf(
            Destination(
                fullAddress = "full_address_one",
            ),
            Destination(
                fullAddress = "full_address_two",
            )
        )*/
    }

    @Test
    fun findDestination_() =
        mainCoroutineRule.runBlockingTest {
           /* coEvery { destinationRepo.getDestinations(true) } returns testDestinations
            val driverFullName: String = "someone"

            var result: Destination = getDestinationForDriver.execute(driverFullName)

            assertThat(result.fullAddress, IsEqual("full_address_one"))*/
        }

}
