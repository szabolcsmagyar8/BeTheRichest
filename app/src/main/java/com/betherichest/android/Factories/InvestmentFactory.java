package com.betherichest.android.Factories;

import com.betherichest.android.App;
import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
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

    private static List<Investment> parseJsonToInvestments() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = getJsonFromResource();
        String arrayString = mapper.readTree(json).get("investments").toString();
        return Arrays.asList(mapper.readValue(arrayString, Investment[].class));
    }

    private static String getJsonFromResource() {
        String json;
        try {
            InputStream stream = App.getContext().getResources().openRawResource(R.raw.investments);
            int size = 0;
            try {
                size = stream.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
