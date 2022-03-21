package com.takehometest.acmerouter.entity

import java.util.*

val vowels: List<Char> = listOf('a', 'e', 'i', 'o', 'u')

data class Destination(
    val id: Int? = -1,
    val street: String,
    val streetNumber: String,
    var assignedToDriverId: Int? = null,
    var dateAssignedToDriver: Long? = -1
) {
    companion object Science {
        fun assignDriversToDestinations(drivers: List<Driver>, destinations: List<Destination>)
                : List<Destination> {

            drivers.forEach { driver ->

                val unassignedDestinations = destinations.filter { it.assignedToDriverId == null }

                var lastCandidateDestination: Destination? = null
                var lastCandidateDestinationSS = 0.0

                unassignedDestinations.forEach { unassignedDestination ->
                    val candidateDriverSS = calculateTotalSS(driver, unassignedDestination)

                    if (lastCandidateDestination == null) {
                        lastCandidateDestinationSS = candidateDriverSS
                        lastCandidateDestination = unassignedDestination
                    } else if (candidateDriverSS > lastCandidateDestinationSS) {
                        lastCandidateDestinationSS = candidateDriverSS
                        lastCandidateDestination = unassignedDestination
                    }
                }
                lastCandidateDestination?.assignedToDriverId = driver.id
                lastCandidateDestination?.dateAssignedToDriver = Date().time
            }
            return destinations
        }

        internal fun calculateTotalSS(driver: Driver, destination: Destination): Double {
            val streetLength: Int = destination.street.length
            val driverFullNameLength: Int = driver.fullName.length

            val isStreetEven = streetLength % 2 == 0;
            val additionalSSPercentage: Double =
                if (findGCD(streetLength, driverFullNameLength) > 1) 50.0 else 0.0

            var result: Double
            if (isStreetEven) {
                result = sumOfVowels(driver.fullName) * 1.5
            } else {
                result = sumOfConsonants(driver.fullName) * 1.0
            }
            result += (result / 100) * additionalSSPercentage
            return result
        }
    }
}

fun sumOfVowels(text: String): Int {
    return text.toCharArray().filter {
        vowels.contains(it.lowercaseChar())
    }.size
}

fun sumOfConsonants(text: String): Int {
    return text.toCharArray().filter {
        !vowels.contains(it.lowercaseChar()) && (it.lowercaseChar() in 'a'..'z')
    }.size
}

fun findGCD(numberOne: Int, numberTwo: Int): Int {
    return if (numberTwo == 0) numberOne else findGCD(numberTwo, numberOne % numberTwo)
}
