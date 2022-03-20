package com.takehometest.acmerouter.entity

import java.util.*

data class Destination(
    val street: String,
    val streetNumber: String,
    val assignedToDriverId: String? = null,
    val dateAssignedToDriver: Long? = -1
)

fun assignDriversToDestinations(drivers: List<Driver>, destinations: List<Destination>)
        : List<Destination> {
    val currentDate: Long = Date().time

    return destinations;
}

private fun calculateSS(driverFullName: Driver, destination: Destination): Int {
    /*val destinationCharLength: Int = destination.fullAddress.length;

    val isEven = destinationCharLength % 2 == 0;
    if (isEven) {
        return sumOfVowels(destination.fullAddress)
    } else {
        return sumOfConsonants(destination.fullAddress)
    }*/
    return 1
}

private fun sumOfVowels(phrase: String): Int {
    val multiplier: Double = 1.5

    return -1
}

private fun sumOfConsonants(phrase: String): Int {
    val multiplier: Double = 1.0

    return -1
}
