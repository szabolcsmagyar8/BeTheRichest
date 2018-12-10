package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.gameElements.Gambling;

import java.util.List;

@Dao
interface GamblingDao {
    @Query("SELECT * FROM gambling")
    List<Gambling> getGamblings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Gambling gambling);
}
