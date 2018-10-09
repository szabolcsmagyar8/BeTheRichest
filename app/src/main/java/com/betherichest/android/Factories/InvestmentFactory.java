package com.betherichest.android.Factories;

import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.JsonManager;
import com.betherichest.android.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class InvestmentFactory {
    public static List<Investment> getCreatedInvestments() {
        List<Investment> investments = null;
        try {
            investments = parseJsonToInvestments();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Investment investment : investments) {
            investment.setImageResourceFromString();
        }
        return investments;
    }

    private static final String jsonRoot = "investments";

    private static List<Investment> parseJsonToInvestments() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = JsonManager.getJsonFromResource(R.raw.investments);
        String arrayString = mapper.readTree(json).get(jsonRoot).toString();
        return Arrays.asList(mapper.readValue(arrayString, Investment[].class));
    }
}
