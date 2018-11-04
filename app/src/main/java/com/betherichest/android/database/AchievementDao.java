package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.gameElements.Achievement;

import java.util.List;

@Dao
public interface AchievementDao {
    @Query("SELECT * FROM achievement")
    List<Achievement> getAchievements();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Achievement achievement);
}
