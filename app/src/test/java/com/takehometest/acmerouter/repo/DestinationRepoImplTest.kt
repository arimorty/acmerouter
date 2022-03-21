package com.takehometest.acmerouter.repo

import com.takehometest.acmerouter.common.MainCoroutineRule
import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.repo.destination.DestinationRepoImpl
import com.takehometest.acmerouter.repo.destination.DestinationRepoLocalSource
import com.takehometest.acmerouter.repo.destination.DestinationRepoRemoteSource
import io.mockk.coEvery
import io.mockk.coVerify
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
class DestinationRepoImplTest {

    private var destinationRepoRemoteSource: DestinationRepoRemoteSource = mockk()

    private var destinationRepoLocalSource: DestinationRepoLocalSource = mockk()

    private lateinit var testDestinationsLocal: List<Destination>

    private lateinit var testDestinationsRemote: List<Destination>

    private lateinit var destinationRepo: DestinationRepoImpl

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        destinationRepo = DestinationRepoImpl(
            destinationRepoLocalSource, destinationRepoRemoteSource, Dispatchers.Main
        )
        testDestinationsLocal = listOf(
            Destination(
                id = 1,
                streetNumber = "street_placeholder_local",
                street = "street_number_placeholder_local",
            )
        )
        testDestinationsRemote = listOf(
            Destination(
                id = 1,
                streetNumber = "street_placeholder_remote",
                street = "street_number_address_placeholder_remote",
            )
        )
    }

    //todo use verify
    @Test
    fun getDestinations_requestsDestinationsFromRemoteDataSource_whenForcedRefreshTrue_andLocalDataExist() =
        mainCoroutineRule.runBlockingTest {
            coEvery { destinationRepoLocalSource.getDestinations() } returns testDestinationsLocal
            coEvery { destinationRepoLocalSource.saveDestinations(any()) } returns Unit
            coEvery { destinationRepoRemoteSource.getDestinations() } returns testDestinationsRemote

            val result: List<Destination> = destinationRepo.getDestinations(true)

            coVerify { destinationRepoLocalSource.saveDestinations(testDestinationsRemote) }
            assertThat(result, IsEqual(testDestinationsLocal))
        }

    @Test
    fun getDestinations_requestsDestinationsFromRemoteDataSource_whenForcedRefreshFalse_andLocalDataExist() =
        mainCoroutineRule.runBlockingTest {
            coEvery { destinationRepoLocalSource.getDestinations() } returns testDestinationsLocal
            coEvery { destinationRepoRemoteSource.getDestinations() } returns testDestinationsRemote

            var result: List<Destination> = destinationRepo.getDestinations(false)

            assertThat(result, IsEqual(testDestinationsLocal))
        }

    @Test
    fun getDestinations_requestsDestinationsFromRemoteDataSource_whenForcedRefreshTrue_andLocalDataNotExist() =
        mainCoroutineRule.runBlockingTest {
            testDestinationsLocal = listOf()
            coEvery { destinationRepoLocalSource.getDestinations() } returns testDestinationsLocal
            coEvery { destinationRepoLocalSource.saveDestinations(any()) } returns Unit
            coEvery { destinationRepoRemoteSource.getDestinations() } returns testDestinationsRemote

            var result: List<Destination> = destinationRepo.getDestinations(true)

            coVerify { destinationRepoLocalSource.saveDestinations(testDestinationsRemote) }
            assertThat(result, IsEqual(testDestinationsLocal))
        }

    @Test
    fun getDestinations_requestsDestinationsFromRemoteDataSource_whenForcedRefreshFalse_andLocalDataNotExist() =
        mainCoroutineRule.runBlockingTest {
            testDestinationsLocal = listOf()
            coEvery { destinationRepoLocalSource.getDestinations() } returns testDestinationsLocal
            coEvery { destinationRepoLocalSource.saveDestinations(any()) } returns Unit
            coEvery { destinationRepoRemoteSource.getDestinations() } returns testDestinationsRemote

            var result: List<Destination> = destinationRepo.getDestinations(false)

            coVerify { destinationRepoLocalSource.saveDestinations(testDestinationsRemote) }
            assertThat(result, IsEqual(testDestinationsLocal))
        }
}
