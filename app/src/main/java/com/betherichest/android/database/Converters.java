package com.betherichest.android.database;

import android.arch.persistence.room.TypeConverter;

import com.betherichest.android.connection.ActionType;
import com.betherichest.android.connection.RequestParam;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static ActionType fromString(String value) {
        return value == null ? null : ActionType.valueOf(value);
    }

    @TypeConverter
    public static String actionTypeToString(ActionType actionType) {
        return actionType == null ? null : actionType.name();
    }

    @TypeConverter
    public static List<RequestParam> stringToRequestParamList(String value) {
        Type listType = new TypeToken<List<RequestParam>>() {
        }.getType();
        List<String> params = Arrays.asList(value.split("\\s*,\\s*"));
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String RequestParamListToString(List<RequestParam> requestParams) {
        return gson.toJson(requestParams);
    }
}

