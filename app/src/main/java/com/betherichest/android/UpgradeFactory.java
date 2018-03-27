package com.betherichest.android;

import java.util.HashMap;

/**
 * Created by Szabi on 2018.03.22..
 */

public class UpgradeFactory {
    private static void AddToMap(Upgrade upgrade, HashMap<Integer, Upgrade> map) {
        map.put(upgrade.getId(), upgrade);
    }

    public static HashMap<Integer, Upgrade> createUpgrades() {
        HashMap<Integer, Upgrade> map = new HashMap<>();

        AddToMap(new Upgrade(
                "first",
                10,
                R.drawable.lemonadestand,
                R.color.orange


        ), map);
        AddToMap(new Upgrade(
                "second",
                50,
                R.drawable.lemonadestand,
                R.color.darkRed

        ), map);
//        AddToMap(new Investment(
//                "Pedalo",
//                400,
//                1,
//                "getInstance a used pedalo and rent it out on the beach, it's a lot of fun.",
//                R.drawable.pedalo
//        ), map);
        return map;
    }
}
