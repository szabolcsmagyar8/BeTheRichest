package com.betherichest.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Szabi on 2018.03.22..
 */

public class UpgradeFactory {
    private static int currentId = 0;
    private static int[] priceMultipliers = new int[]{10, 50, 500, 5000, 10000, 50000, 100000, 500000};
    private static int[] requiredRanks = new int[]{1, 5, 10, 20, 30, 50, 70, 100};

    private static List<Upgrade> upgrades = new ArrayList<>();

    public static List<Upgrade> getCreatedUpgrades() {
        return upgrades;
    }

    private static void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
    }

    public static void createUpgrades(List<Investment> investments) {
        for (Investment investment : investments) {
            long basePrice = investment.getBasePrice();
            int[] upgradeEffectMultipliers = investment.getUpgradeEffectMultipliers();
            for (int i = 0; i < upgradeEffectMultipliers.length; i++) {
                Upgrade upgrade = new Upgrade(
                        "",
                        basePrice * priceMultipliers[i],
                        upgradeEffectMultipliers[i],
                        requiredRanks[i],
                        investment.getImageResource(),
                        10,
                        UpgradeCategory.InvestmentUpgrade,
                        investment);
                addUpgrade(upgrade);
                investment.addRelevantUpgrade(upgrade);
            }
        }
    }
}
