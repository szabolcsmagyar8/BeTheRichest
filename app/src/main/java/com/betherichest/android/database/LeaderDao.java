package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.gameElements.Leader;

import java.util.List;

@Dao
public interface LeaderDao {
    @Query("SELECT * FROM Leader")
    List<Leader> getLeaders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Leader leader);
}
