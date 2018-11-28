package com.betherichest.android.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.betherichest.android.connection.RequestItem;

import java.util.List;

@Dao
interface RequestItemDao {
    @Query("SELECT * FROM requestitem")
    List<RequestItem> getRequestItems();

    @Insert
    void insertAll(RequestItem requestItem);

    @Delete
    void deleteRequestItem(RequestItem... requestItems);

}
