package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.gameElements.Investment;

import java.util.List;

@Dao
public interface InvestmentDao {
    @Query("SELECT * FROM investment")
    List<Investment> getInvestments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Investment investment);
}
