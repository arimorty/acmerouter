package com.takehometest.acmerouter.repo.common.source.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.takehometest.acmerouter.repo.destination.source.local.DestinationDao;
import com.takehometest.acmerouter.repo.destination.source.local.DestinationEntity;
import com.takehometest.acmerouter.repo.driver.source.local.DriverEntity;
import com.takehometest.acmerouter.repo.driver.source.local.DriverDao;

@Database(entities = {DriverEntity.class, DestinationEntity.class}, version = AcmeDatabase.VERSION)
public abstract class AcmeDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract DriverDao getDriverDao();

    public abstract DestinationDao getDestinationDao();
}
