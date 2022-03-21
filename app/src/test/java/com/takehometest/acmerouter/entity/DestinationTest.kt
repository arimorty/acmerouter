package com.takehometest.acmerouter.entity

import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Test

class DestinationTest {

    private lateinit var testDrivers: List<Driver>

    private lateinit var testDestinations: List<Destination>

    @Test
    fun sumOfVowels() {
        val result: Int = sumOfVowels("hey")

        MatcherAssert.assertThat(result, IsEqual(1))
    }

    @Test
    fun sumOfConsonants() {
        val result: Int = sumOfConsonants("hey")

        MatcherAssert.assertThat(result, IsEqual(2))
    }

    @Test
    fun findGCD() {
        val result: Int = findGCD(50, 80)

        MatcherAssert.assertThat(result, IsEqual(10))
    }

    @Test
    fun calculateTotalSS_whenStreetLengthIsEven_andDriverHasNoVowel_andCommonFactorIsOne() {
        val driver = Driver(
            id = 1,
            fullName = "b",
        )
        val destination = Destination(
            id = 1,
            streetNumber = "1",
            street = "mm",
        )

        val result: Double = Destination.calculateTotalSS(driver, destination)

        MatcherAssert.assertThat(result, IsEqual(0.0))
    }

    @Test
    fun calculateTotalSS_whenStreetLengthIsEven_andStreetHasOneVowel_andCommonFactorIsOne() {
        val driver = Driver(
            id = 1,
            fullName = "a",
        )
        val destination = Destination(
            id = 1,
            streetNumber = "1",
            street = "am",
        )

        val result: Double = Destination.calculateTotalSS(driver, destination)

        MatcherAssert.assertThat(result, IsEqual(1.5))
    }

    @Test
    fun calculateTotalSS_whenStreetLengthIsEven_andDriverHasNoVowel_andCommonFactorIsMoreThanOne() {
        val driver = Driver(
            id = 1,
            fullName = "bb",
        )
        val destination = Destination(
            id = 1,
            streetNumber = "1",
            street = "mm",
        )

        val result: Double = Destination.calculateTotalSS(driver, destination)

        MatcherAssert.assertThat(result, IsEqual(0.0))
    }

    @Test
    fun calculateTotalSS_whenStreetLengthIsEven_andSDriverHasOneVowel_andCommonFactorIsMoreThanOne() {
        val driver = Driver(
            id = 1,
            fullName = "ab",
        )
        val destination = Destination(
            id = 1,
            streetNumber = "1",
            street = "am",
        )

        val result: Double = Destination.calculateTotalSS(driver, destination)

        MatcherAssert.assertThat(result, IsEqual(2.25))
    }

    @Test
    fun calculateTotalSS_whenStreetLengthIsOdd_andDriverNameHasNoConsonants_andCommonFactorIsOne() {
        val driver = Driver(
            id = 1,
            fullName = "a",
        )
        val destination = Destination(
            id = 1,
            streetNumber = "1",
            street = "mmm",
        )

        val result: Double = Destination.calculateTotalSS(driver, destination)

        MatcherAssert.assertThat(result, IsEqual(0.0))
    }

    @Test
    fun calculateTotalSS_whenStreetLengthIsOdd_andDriverNameHasOneConsonant_andCommonFactorIsOne() {
        val driver = Driver(
            id = 1,
            fullName = "b",
        )
        val destination = Destination(
            id = 1,
            streetNumber = "1",
            street = "mmm",
        )

        val result: Double = Destination.calculateTotalSS(driver, destination)

        MatcherAssert.assertThat(result, IsEqual(1.0))
    }

    @Test
    fun calculateTotalSS_whenStreetLengthIsOdd_andDriverNameHasOneConsonant_andCommonFactorIsMoreThanOne() {
        val driver = Driver(
            id = 1,
            fullName = "baa",
        )
        val destination = Destination(
            id = 1,
            streetNumber = "1",
            street = "mmm",
        )

        val result: Double = Destination.calculateTotalSS(driver, destination)

        MatcherAssert.assertThat(result, IsEqual(1.5))
    }

    @Test
    fun assignDriversToDestinations() {
        testDestinations = listOf(
            Destination(
                id = 1,
                streetNumber = "215",
                street = "Osinski Manors",
            ),
            Destination(
                id = 1,
                streetNumber = "2431",
                street = "Lindgren Corners",
            )
        )
        testDrivers = listOf(
            Driver(
                id = 1,
                fullName = "Everardo Welch",
            ),
            Driver(
                id = 2,
                fullName = "Orval Mayert",
            )
        )
        val ssOfFirstDriverAndFirstDestination: Double =
            Destination.calculateTotalSS(testDrivers[0], testDestinations[0])
        val ssOfSecondDriverAndFirstDestination: Double =
            Destination.calculateTotalSS(testDrivers[1], testDestinations[0])
        val expectedDriverAssignedToFirstDestination =
            if (ssOfFirstDriverAndFirstDestination > ssOfSecondDriverAndFirstDestination) 1 else 2

        val destination: List<Destination> =
            Destination.assignDriversToDestinations(testDrivers, testDestinations)

        MatcherAssert.assertThat(
            destination[0].id,
            IsEqual(expectedDriverAssignedToFirstDestination)
        )
    }
}
