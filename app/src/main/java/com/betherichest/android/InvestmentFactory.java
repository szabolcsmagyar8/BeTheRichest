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
                R.drawable.lemonadestand      //ICON
        ), map);
        AddToMap(new Investment(
                "Trampoline",
                80,
                0.4,
                "A cheap trampoline that children can use next to a playground for a small price.",
                R.drawable.trampoline
        ), map);
        AddToMap(new Investment(
                "Pedalo",
                400,
                1,
                "getInstance a used pedalo and rent it out on the beach, it's a lot of fun.",
                R.drawable.pedalo
        ), map);
        return map;
    }
}
