package com.takehometest.acmerouter.repo.destination.source.local

import androidx.room.*

@Dao
interface DestinationDao {

    @Query("SELECT * FROM destination")
    fun getDestinations(): List<DestinationEntity>

    @Query("SELECT * FROM destination WHERE assignedToDriverId = :driverId")
    suspend fun getDestinationByDriver(driverId: Int): DestinationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDestinations(destinations: List<DestinationEntity>)
}
