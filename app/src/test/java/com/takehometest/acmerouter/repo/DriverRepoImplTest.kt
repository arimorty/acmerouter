package com.takehometest.acmerouter.repo

import com.takehometest.acmerouter.common.MainCoroutineRule
import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.repo.driver.DriverRepoImpl
import com.takehometest.acmerouter.repo.driver.DriverRepoLocalSource
import com.takehometest.acmerouter.repo.driver.DriverRepoRemoteSource
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
class DriverRepoImplTest {

    private val driverRepoRemoteSource: DriverRepoRemoteSource = mockk()

    private val driverRepoLocalSource: DriverRepoLocalSource = mockk()

    private lateinit var testDriversLocal: List<Driver>

    private lateinit var testDriversRemote: List<Driver>

    private lateinit var driverRepo: DriverRepoImpl

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        driverRepo = DriverRepoImpl(
            driverRepoLocalSource, driverRepoRemoteSource, Dispatchers.Main
        )
        testDriversLocal = listOf(
            Driver(
                id = 1,
                fullName = "full_name_placeholder_local",
            )
        )
        testDriversRemote = listOf(
            Driver(
                id = 2,
                fullName = "full_name_placeholder_remote",
            )
        )
    }

    @Test
    fun getFeed_requestsDriversFromRemoteDataSource_whenForcedRefreshTrue_andLocalDataExist() =
        mainCoroutineRule.runBlockingTest {
            coEvery { driverRepoLocalSource.getDrivers() } returns testDriversLocal
            coEvery { driverRepoLocalSource.saveDrivers(any()) } returns Unit
            coEvery { driverRepoRemoteSource.getDrivers() } returns testDriversRemote

            val result: List<Driver> = driverRepo.getDrivers(true)

            coVerify { driverRepoLocalSource.saveDrivers(testDriversRemote) }
            assertThat(result, IsEqual(testDriversLocal))
        }

    @Test
    fun getFeed_requestsDriversFromRemoteDataSource_whenForcedRefreshFalse_andLocalDataExist() =
        mainCoroutineRule.runBlockingTest {
            coEvery { driverRepoLocalSource.getDrivers() } returns testDriversLocal
            coEvery { driverRepoLocalSource.saveDrivers(any()) } returns Unit

            val result: List<Driver> = driverRepo.getDrivers(false)

            assertThat(result, IsEqual(testDriversLocal))
        }

    @Test
    fun getFeed_requestsDriversFromRemoteDataSource_whenForcedRefreshTrue_andLocalDataNotExist() =
        mainCoroutineRule.runBlockingTest {
            testDriversLocal = listOf()
            coEvery { driverRepoLocalSource.getDrivers() } returns testDriversLocal
            coEvery { driverRepoLocalSource.saveDrivers(any()) } returns Unit
            coEvery { driverRepoRemoteSource.getDrivers() } returns testDriversRemote

            val result: List<Driver> = driverRepo.getDrivers(true)

            coVerify { driverRepoLocalSource.saveDrivers(testDriversRemote) }
            assertThat(result, IsEqual(testDriversLocal))
        }

    @Test
    fun getFeed_requestsDriversFromRemoteDataSource_whenForcedRefreshFalse_andLocalDataNotExist() =
        mainCoroutineRule.runBlockingTest {
            testDriversLocal = listOf()
            coEvery { driverRepoLocalSource.getDrivers() } returns testDriversLocal
            coEvery { driverRepoLocalSource.saveDrivers(any()) } returns Unit
            coEvery { driverRepoRemoteSource.getDrivers() } returns testDriversRemote

            val result: List<Driver> = driverRepo.getDrivers(false)

            coVerify { driverRepoLocalSource.saveDrivers(testDriversRemote) }
            assertThat(result, IsEqual(testDriversLocal))
        }
}
