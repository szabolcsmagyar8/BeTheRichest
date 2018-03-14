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
                "Get a used pedalo and rent it out on the beach, it's a lot of fun.",
                R.drawable.pedalo
        ), map);
//        AddToMap(new Investment(
//                "Bouncy Castle",
//                940,
//                2.2,
//                "One of every children's favourite activities, place it in a park and enjoy the income.",
//                R.drawable.bouncycastle
//        ), map);
//        AddToMap(new Investment(
//                "Photo Studio",
//                4400,
//                5,
//                "Professional environment for photographers.",
//                R.drawable.photostudio
//        ), map);
//        AddToMap(new Investment(
//                "Hot Dog Truck",
//                12800,
//                10,
//                "When hungry people see this car, they cannot resist to buy a delicious hot-dog.",
//                R.drawable.hotdogtruck
//        ), map);
//        AddToMap(new Investment(
//                "Race Car Simulator",
//                37000,
//                25,
//                "For those who are curious how it feels like driving a race car.",
//                R.drawable.racecarsimulator
//        ), map);
//        AddToMap(new Investment(
//                "Apartment",
//                102000,
//                50,
//                "Buy apartments that you rent out to people.",
//                R.drawable.apartment
//        ), map);
//        AddToMap(new Investment(
//                "Family House",
//                319000,
//                110,
//                "Buy houses that you sell.",
//                R.drawable.familyhouse
//        ), map);
//        AddToMap(new Investment(
//                "Yacht",
//                868000,
//                230,
//                "",
//                R.drawable.yacht
//        ), map);
//        AddToMap(new Investment(
//                "Mine Truck",
//                3150000,
//                550,
//                "",
//                R.drawable.minetruck
//        ), map);
//        AddToMap(new Investment(
//                "Private Airplane",
//                9150000,
//                1200,
//                "",
//                R.drawable.airplane
//        ), map);
//        AddToMap(new Investment(
//                "Luxury Real Estate",
//                19000000,
//                2080,
//                "",
//                R.drawable.luxuryrealestate
//        ), map);
//        AddToMap(new Investment(
//                "Casino",
//                35722000,
//                3750,
//                "Manage a casino to get a lot of money from rich risk takers.",
//                R.drawable.casino
//        ), map);
//        AddToMap(new Investment(
//                "Office Building",
//                79990000,
//                6100,
//                "",
//                R.drawable.officebuilding
//        ), map);
//        AddToMap(new Investment(
//                "Amusement park",
//                165800000,
//                10500,
//                "An amusement park attracts thousands of visitors daily.",
//                R.drawable.funfair
//        ), map);
//        AddToMap(new Investment(
//                "Hotel Chain",
//                443000000,
//                28200,
//                "Tourist have to stay somewhere. Let's be it one of your hotels.",
//                R.drawable.hotelchain
//        ), map);
//        AddToMap(new Investment(
//                "Oil Rig",
//                925000000d,
//                50000,
//                "Dig deep enough in your garden, and find a valuable material, called oil.",
//                R.drawable.oilrig
//        ), map);
//        AddToMap(new Investment(
//                "Nuclear Power Plant",
//                5550000000d,
//                200000,
//                "Provide the power for a whole city.",
//                R.drawable.nuclearplant
//        ), map);

        return map;
    }
}
