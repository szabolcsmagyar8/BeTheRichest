package com.betherichest.android.Factories;

import com.betherichest.android.App;
import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InvestmentFactory {
    private static List<Investment> investments = new ArrayList<>();

    static {    // initializer
        createInvestments();
        try {
            parseJSONtoInvestments();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseJSONtoInvestments() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = getJsonFromResource();
        JsonNode jsonNode = mapper.readTree(json);
        String arrayString = jsonNode.get("investments").toString();
        List<Investment> invs = Arrays.asList(mapper.readValue(arrayString, Investment[].class));
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

    public static List<Investment> getCreatedInvestments() {
        return investments;
    }

    private static void addInvestment(Investment investment) {
        investments.add(investment);
    }

    private static void createInvestments() {
        addInvestment(new Investment(
                "Lemonade stand",              //NAME
                10,                 //BASE PRICE
                0.1,                //BASE MONEY PER SECOND
                "Home made lemonade with fresh lemon is the best refreshing drink in the summer.", //DESCRIPTION
                R.drawable.lemonadestand,     //ICON,
                new int[]{2, 2, 2, 3, 3, 5, 8, 10}    //UPGRADE EFFECT MULTIPLIERS
        ));
        addInvestment(new Investment(
                "Trampoline",
                80,
                0.4,
                "A cheap trampoline that children can use next to a playground for a small price.",
                R.drawable.trampoline,
                new int[]{2, 2, 2, 3, 3, 5, 5, 8}
        ));
        addInvestment(new Investment(
                "Pedalo",
                400,
                1,
                "Get a used pedalo and rent it out on the beach, it's a lot of fun.",
                R.drawable.pedalo,
                new int[]{2, 2, 2, 3, 3, 5, 8}
        ));
        addInvestment(new Investment(
                "Bouncy Castle",
                940,
                2.2,
                "One of every children's favourite activities, place it in a park and enjoy the income.",
                R.drawable.bouncycastle,
                new int[]{2, 2, 2, 3, 3, 3, 5}
        ));
        addInvestment(new Investment(
                "Photo Studio",
                4400,
                5,
                "Professional environment for photographers.",
                R.drawable.photostudio,
                new int[]{2, 2, 2, 3, 3, 5}
        ));
        addInvestment(new Investment(
                "Hot Dog Truck",
                12800,
                10,
                "When hungry people see this car, they cannot resist to buy a delicious hot-dog.",
                R.drawable.hotdogtruck,
                new int[]{2, 2, 2, 3, 3, 5}
        ));
        addInvestment(new Investment(
                "Race Car Simulator",
                37000,
                25,
                "For those who are curious how it feels like driving a race car.",
                R.drawable.racecarsimulator,
                new int[]{2, 2, 2, 2, 2, 5}
        ));
        addInvestment(new Investment(
                "Apartment",
                102000,
                50,
                "Buy apartments that you rent out to people.",
                R.drawable.apartment,
                new int[]{2, 2, 2, 3, 5}
        ));
    }
}
