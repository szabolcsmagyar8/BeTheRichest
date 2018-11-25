package com.betherichest.android.connection;

import java.util.Map;

public class RequestItem {
    private String endPoint;
    private Map<String, Object> requestParams;
    private ActionType actionType;

    public RequestItem(String endPoint, Map<String, Object> requestParams, ActionType actionType) {

        this.endPoint = endPoint;
        this.requestParams = requestParams;
        this.actionType = actionType;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
