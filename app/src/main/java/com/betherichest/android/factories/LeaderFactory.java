package com.betherichest.android.factories;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Leader;
import com.betherichest.android.mangers.JsonManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderFactory {
    public static List<Leader> getCreatedLeaders() {
        List<Leader> leaders = null;
        try {
            leaders = parseJsonToLeaders();
            leaders.toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        leaders.add(new Leader("Player", 0, true));

        return leaders;
    }

    private static final String jsonRoot = "leaders";

    private static List<Leader> parseJsonToLeaders() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = JsonManager.getJsonFromResource(R.raw.leaders);
        String arrayString = mapper.readTree(json).get(jsonRoot).toString();
        return new ArrayList<>(Arrays.asList(mapper.readValue(arrayString, Leader[].class)));
    }
}
