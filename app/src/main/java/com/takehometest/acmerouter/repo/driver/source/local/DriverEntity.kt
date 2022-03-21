package com.takehometest.acmerouter.repo.driver.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "driver")
data class DriverEntity(
    @PrimaryKey val id: Int? = -1,
    val fullName: String
)
