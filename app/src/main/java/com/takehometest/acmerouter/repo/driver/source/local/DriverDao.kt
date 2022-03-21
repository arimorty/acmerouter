package com.takehometest.acmerouter.repo.driver.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DriverDao {

    @Query("SELECT * FROM driver")
    fun getDrivers(): List<DriverEntity>

    @Query("SELECT * FROM driver WHERE id = :driverId")
    suspend fun getDriverById(driverId: Int): DriverEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDrivers(drivers: List<DriverEntity>)
}
