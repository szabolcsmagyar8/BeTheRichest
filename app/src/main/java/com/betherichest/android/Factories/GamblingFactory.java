package com.betherichest.android.Factories;

import com.betherichest.android.GameElements.Gambling;
import com.betherichest.android.JsonManager;
import com.betherichest.android.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GamblingFactory {
    static final String jsonRoot = "gamblings";

    public static List<Gambling> getCreatedGamblings() {
        List<Gambling> gamblings = null;
        try {
            gamblings = parseJsonToGamblings();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Gambling gambling : gamblings) {
            gambling.setImageResourceFromString();
        }
        return gamblings;
    }

    private static List<Gambling> parseJsonToGamblings() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = JsonManager.getJsonFromResource(R.raw.gamblings);
        String arrayString = mapper.readTree(json).get(jsonRoot).toString();
        return Arrays.asList(mapper.readValue(arrayString, Gambling[].class));
    }
}
