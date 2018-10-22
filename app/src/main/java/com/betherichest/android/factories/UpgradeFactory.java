package com.betherichest.android.factories;

import android.graphics.Color;

import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.InvestmentUpgrade;
import com.betherichest.android.gameElements.InvestmentUpgradeConfig;
import com.betherichest.android.gameElements.TapUpgrade;
import com.betherichest.android.gameElements.TapUpgradeConfig;
import com.betherichest.android.gameElements.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class UpgradeFactory {
    private static InvestmentUpgradeConfig[] investmentUpgradeConfigs = new InvestmentUpgradeConfig[]{
            new InvestmentUpgradeConfig(10, 1),
            new InvestmentUpgradeConfig(50, 5),
            new InvestmentUpgradeConfig(500, 10),
            new InvestmentUpgradeConfig(5000, 20),
            new InvestmentUpgradeConfig(10000, 30),
            new InvestmentUpgradeConfig(50000, 50),
            new InvestmentUpgradeConfig(100000, 70),
            new InvestmentUpgradeConfig(500000, 100)};

    private static TapUpgradeConfig[] tapUpgradeConfigs = new TapUpgradeConfig[]{
            new TapUpgradeConfig(1000, 2),
            new TapUpgradeConfig(5000, 2),
            new TapUpgradeConfig(20000, 3),
            new TapUpgradeConfig(50000, 3),
            new TapUpgradeConfig(200000, 5),
            new TapUpgradeConfig(1000000, 5),
            new TapUpgradeConfig(5000000, 5),
            new TapUpgradeConfig(20000000, 8),
    };

    private static int[] colors = new int[]{
            Color.parseColor("#ffffff"),   // Simple White
            Color.parseColor("#fff700"),   // Uncommon Yellow
            Color.parseColor("#90bcff"),   // Common Lightblue
            Color.parseColor("#32ff00"),   // Rare Green
            Color.parseColor("#ffa200"),   // Special Orange
            Color.parseColor("#e10000"),   // Legendary Red
            Color.parseColor("#0048ed"),   // Epic Blue
            Color.parseColor("#6e00c4"),   // Mysterious Purple
    };

    private static List<Upgrade> upgrades = new ArrayList<>();

    public static List<Upgrade> getCreatedUpgrades() {
        return upgrades;
    }

    private static void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
    }

    public static void createUpgrades(List<Investment> investments) {
        createInvestmentUpgrades(investments);

        createTapUpgrades();
    }

    private static void createTapUpgrades() {
        for (int i = 0; i < tapUpgradeConfigs.length; i++) {
            Upgrade upgrade = new TapUpgrade(
                    tapUpgradeConfigs[i].getPrice(),
                    tapUpgradeConfigs[i].getMoneyPerTapMultiplier(),
                    colors[i]);
            addUpgrade(upgrade);
        }
    }

    private static void createInvestmentUpgrades(List<Investment> investments) {
        for (Investment investment : investments) {  // for every investment there are several upgrades
            long basePrice = investment.getBasePrice();
            int[] upgradeEffectMultipliersForActualInvestment = investment.getUpgradeEffectMultipliers();

            for (int i = 0; i < upgradeEffectMultipliersForActualInvestment.length; i++) {  // investments have different number of upgrades
                Upgrade upgrade = new InvestmentUpgrade(
                        basePrice * investmentUpgradeConfigs[i].getBasePriceMultiplier(),
                        upgradeEffectMultipliersForActualInvestment[i],
                        investmentUpgradeConfigs[i].getRequiredLevel(),
                        investment.getImageResource(),
                        colors[colors.length - upgradeEffectMultipliersForActualInvestment.length + i],
                        investment);

                addUpgrade(upgrade);
                investment.addRelevantUpgrade(upgrade);
            }
        }
    }
}