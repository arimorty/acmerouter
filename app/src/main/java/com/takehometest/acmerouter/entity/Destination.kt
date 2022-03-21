package com.takehometest.acmerouter.entity

data class Destination(
    val id: Int? = -1,
    val street: String,
    val streetNumber: String,
    val assignedToDriverId: Int? = null,
    val dateAssignedToDriver: Long? = -1
)

fun assignDriversToDestinations(drivers: List<Driver>, destinations: List<Destination>)
        : List<Destination> {

    return destinations;
}

