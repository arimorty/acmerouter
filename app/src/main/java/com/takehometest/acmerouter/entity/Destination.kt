package com.takehometest.acmerouter.entity

data class Destination(
    val street: String,
    val streetNumber: String,
    val assignedToDriverId: String? = null,
    val dateAssignedToDriver: Long? = -1
)

fun assignDriversToDestinations(drivers: List<Driver>, destinations: List<Destination>)
        : List<Destination> {

    return destinations;
}

