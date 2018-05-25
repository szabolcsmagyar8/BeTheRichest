package com.betherichest.android;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Szabi on 2018.03.22..
 */

public class UpgradeFactory {
    static int currentId = 0;
    static int[] priceMultipliers = new int[]{10, 50, 500, 5000, 10000, 50000, 100000, 500000};

    private static void AddToMap(Upgrade upgrade, HashMap<Integer, Upgrade> map) {
        map.put(upgrade.getId(), upgrade);
    }

    public static HashMap<Integer, Upgrade> createUpgrades(List<Investment> investments) {
        HashMap<Integer, Upgrade> map = new HashMap<>();

        for (Investment investment : investments) {
            long basePrice = investment.getBasePrice();
            int[] upgradeEffectMultipliers = investment.getUpgradeEffectMultipliers();
            for (int i = 0; i < upgradeEffectMultipliers.length; i++) {
                Upgrade upgrade = new Upgrade(
                        "",
                        basePrice * priceMultipliers[i],
                        upgradeEffectMultipliers[i],
                        investment.getImageResource(),
                        10,
                        UpgradeCategory.InvestmentUpgrade,
                        investment);
                AddToMap(upgrade, map);
                investment.addRelevantUpgrade(upgrade);
        }
        }
        return map;
    }
}
