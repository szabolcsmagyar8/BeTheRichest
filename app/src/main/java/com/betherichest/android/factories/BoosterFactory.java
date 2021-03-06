package com.betherichest.android.factories;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Booster;
import com.betherichest.android.mangers.JsonManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BoosterFactory {
    static final String jsonRoot = "boosters";

    public static List<Booster> getCreatedBoosters() {
        List<Booster> boosters = null;
        try {
            boosters = parseJsonToBooster();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Booster booster : boosters) {
            booster.setImageResourceFromString();
        }
        return boosters;
    }

    private static List<Booster> parseJsonToBooster() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = JsonManager.getJsonFromResource(R.raw.boosters);
        String arrayString = mapper.readTree(json).get(jsonRoot).toString();
        return Arrays.asList(mapper.readValue(arrayString, Booster[].class));
    }
}
