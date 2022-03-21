package com.takehometest.acmerouter.repo.destination.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destination")
data class DestinationEntity(
    @PrimaryKey val id: Int? = -1,
    val street: String,
    val streetNumber: String,
    val assignedToDriverId: Int? = null,
    val dateAssignedToDriver: Long? = -1
)
