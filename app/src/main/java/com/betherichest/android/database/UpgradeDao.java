package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.gameElements.Upgrade;

import java.util.List;

@Dao
public interface UpgradeDao {
    @Query("SELECT * FROM upgrade")
    List<Upgrade> getUpgrades();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Upgrade upgrade);
}
