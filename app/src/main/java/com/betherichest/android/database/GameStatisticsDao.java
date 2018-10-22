package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.gameElements.GameStatistics;

import java.util.List;

@Dao
public interface GameStatisticsDao {
    @Query("SELECT * FROM gamestatistics")
    List<GameStatistics> geGameStatistics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(GameStatistics gameStatistics);
}
