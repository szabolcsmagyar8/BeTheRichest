package com.betherichest.android.connection;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class RequestItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String endPoint;
    @ColumnInfo
    private List<RequestParam> requestParams;
    @ColumnInfo
    private ActionType actionType;

    public RequestItem(int id, String endPoint, List<RequestParam> requestParams, ActionType actionType) {
        this.id = id;
        this.endPoint = endPoint;
        this.requestParams = requestParams;
        this.actionType = actionType;
    }

    @Ignore
    public RequestItem(String endPoint, List<RequestParam> requestParams, ActionType actionType) {
        this.endPoint = endPoint;
        this.requestParams = requestParams;
        this.actionType = actionType;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
    }

}
