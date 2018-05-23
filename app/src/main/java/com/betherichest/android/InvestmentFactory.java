package com.betherichest.android;

import java.util.HashMap;

/**
 * Created by Szabi on 2018. 03. 14..
 */

public class InvestmentFactory {
    private static void AddToMap(Investment investment, HashMap<Integer, Investment> map) {
        map.put(investment.getId(), investment);
    }

    public static HashMap<Integer, Investment> createInvestments() {
        HashMap<Integer, Investment> map = new HashMap<>();

        AddToMap(new Investment(
                "Lemonade stand",              //NAME
                10,                 //BASE PRICE
                0.1,                //BASE MONEY PER SECOND
                "Home made lemonade with fresh lemon is the best refreshing drink in the summer.", //DESCRIPTION
                R.drawable.lemonadestand,     //ICON,
                new int[] {2,2,2,3,3,5,8,10}

        ), map);
        AddToMap(new Investment(
                "Trampoline",
                80,
                0.4,
                "A cheap trampoline that children can use next to a playground for a small price.",
                R.drawable.trampoline,
                new int[] {2,2,2,3,3,5,5,8}
        ), map);
        AddToMap(new Investment(
                "Pedalo",
                400,
                1,
                "getInstance a used pedalo and rent it out on the beach, it's a lot of fun.",
                R.drawable.pedalo,
                new int[] {2,2,2,3,3,5,8}
        ), map);
        AddToMap(new Investment(
                "Bouncy Castle",
                940,
                2.2,
                "One of every children's favourite activities, place it in a park and enjoy the income.",
                R.drawable.bouncycastle,
                new int[] {2,2,2,3,3,3,5}
        ), map);
        AddToMap(new Investment(
                "Photo Studio",
                4400,
                5,
                "Professional environment for photographers.",
                R.drawable.photostudio,
                new int[] {2,2,2,3,3,5}
        ), map);
        AddToMap(new Investment(
                "Hot Dog Truck",
                12800,
                10,
                "When hungry people see this car, they cannot resist to buy a delicious hot-dog.",
                R.drawable.hotdogtruck,
                new int[] {2,2,2,3,3,5}
        ), map);
        AddToMap(new Investment(
                "Race Car Simulator",
                37000,
                25,
                "For those who are curious how it feels like driving a race car.",
                R.drawable.racecarsimulator,
                new int[] {2,2,2,2,2,5}
        ), map);
        AddToMap(new Investment(
                "Apartment",
                102000,
                50,
                "Buy apartments that you rent out to people.",
                R.drawable.apartment,
                new int[] {2,2,2,3,5}
        ), map);
        return map;
    }
}
