package com.takehometest.acmerouter.repo.destination.source

import android.util.Log
import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.repo.destination.DestinationRepoLocalSource
import com.takehometest.acmerouter.repo.destination.DestinationRepoRemoteSource
import javax.inject.Inject

/**
 * A blank [DestinationRepoLocalSource] that simply returns empty data and doesn't save anything
 * to storage.
 */
class DestinationRepoRemoteSourceImpl
@Inject constructor() : DestinationRepoRemoteSource {
    override suspend fun getDestinations(): List<Destination> = generateDestinations()
}

private fun generateDestinations(): List<Destination> {
    val items = mutableListOf<Destination>()
    items.add(Destination(street = "one street", streetNumber = "1"))
    items.add(Destination(street = "two street", streetNumber = "2"))
    Log.d("Items: ", items.toString())//TODO: abstract out logging
    return items
}
