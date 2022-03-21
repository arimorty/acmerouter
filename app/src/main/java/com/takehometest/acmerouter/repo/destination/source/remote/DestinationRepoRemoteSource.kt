package com.takehometest.acmerouter.repo.destination.source.remote

import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.repo.common.source.remote.AcmeService
import com.takehometest.acmerouter.repo.destination.DestinationRepoRemoteSource
import javax.inject.Inject

/**
 * A [DestinationRepoRemoteSource] that returns data from a service.
 */
class DestinationRepoRemoteSourceImpl @Inject constructor(
    private val acmeService: AcmeService
) : DestinationRepoRemoteSource {

    override suspend fun getDestinations(): List<Destination> = map(acmeService.getAll().shipments)
}

fun map(from: List<String>): List<Destination> {
    return from.mapIndexed { index, it ->
        val streetPart: String = it.substringAfter(" ");
        val streetNumberPart: String = it.substringBefore(" ");
        Destination(id = index, street = streetPart, streetNumber = streetNumberPart)
    }
}
