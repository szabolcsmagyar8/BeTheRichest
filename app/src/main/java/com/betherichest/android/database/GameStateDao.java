package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.betherichest.android.GameState;

import java.util.List;

@Dao
public interface GameStateDao {
    @Query("SELECT * FROM gamestate")
    List<GameState> getGameState();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(GameState gameState);
}

