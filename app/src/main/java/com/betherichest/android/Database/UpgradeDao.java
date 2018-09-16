package com.betherichest.android.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.GameElements.Upgrade;

import java.util.List;

@Dao
public interface UpgradeDao {
    @Query("SELECT * FROM upgrade")
    List<Upgrade> getUpgrades();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Upgrade upgrade);
}
