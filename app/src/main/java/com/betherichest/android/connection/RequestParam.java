package com.betherichest.android.connection;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RequestParam {
    private static int currentId = 0;
    @PrimaryKey
    private int id;
    @ColumnInfo
    private String key;
    @ColumnInfo
    private String value;

    public RequestParam(String key, String value) {
        this.id = currentId++;
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
