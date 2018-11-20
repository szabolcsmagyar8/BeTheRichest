package com.betherichest.android.factories;

import android.graphics.Color;

import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.upgrade.GamblingUpgrade;
import com.betherichest.android.gameElements.upgrade.GamblingUpgradeConfig;
import com.betherichest.android.gameElements.upgrade.GlobalIncrementUpgrade;
import com.betherichest.android.gameElements.upgrade.GlobalIncrementUpgradeConfig;
import com.betherichest.android.gameElements.upgrade.InvestmentUpgrade;
import com.betherichest.android.gameElements.upgrade.InvestmentUpgradeConfig;
import com.betherichest.android.gameElements.upgrade.TapUpgrade;
import com.betherichest.android.gameElements.upgrade.TapUpgradeConfig;
import com.betherichest.android.gameElements.upgrade.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class UpgradeFactory {
    private static InvestmentUpgradeConfig[] investmentUpgradeConfigs = new InvestmentUpgradeConfig[]{
            new InvestmentUpgradeConfig(10, 1),             // 1
            new InvestmentUpgradeConfig(50, 5),             // 2
            new InvestmentUpgradeConfig(500, 10),           // 3
            new InvestmentUpgradeConfig(5000, 20),          // 4
            new InvestmentUpgradeConfig(10000, 30),         // 5
            new InvestmentUpgradeConfig(50000, 50),         // 6
            new InvestmentUpgradeConfig(100000, 70),        // 7
            new InvestmentUpgradeConfig(500000, 100)};      // 8

    private static TapUpgradeConfig[] tapUpgradeConfigs = new TapUpgradeConfig[]{
            new TapUpgradeConfig(1000, 2, 100),
            new TapUpgradeConfig(5000, 2, 1000),
            new TapUpgradeConfig(20000, 3, 2500),
            new TapUpgradeConfig(50000, 3, 5000),
            new TapUpgradeConfig(200000, 5, 8000),
            new TapUpgradeConfig(1000000, 5, 12000),
            new TapUpgradeConfig(5000000, 5, 20000),
            new TapUpgradeConfig(20000000, 8, 30000),
    };

    private static GlobalIncrementUpgradeConfig[] globalIncrementConfigs = new GlobalIncrementUpgradeConfig[]{
            new GlobalIncrementUpgradeConfig(100000, 0.1),
            new GlobalIncrementUpgradeConfig(500000, 0.5),
            new GlobalIncrementUpgradeConfig(3000000, 3),
            new GlobalIncrementUpgradeConfig(15000000, 10),
            new GlobalIncrementUpgradeConfig(80000000, 30),
            new GlobalIncrementUpgradeConfig(500000000, 100),
            new GlobalIncrementUpgradeConfig(3000000000d, 500),
            new GlobalIncrementUpgradeConfig(25000000000d, 2500),
    };

    private static GamblingUpgradeConfig[] gamblingUpgradeConfigs = new GamblingUpgradeConfig[]{
            new GamblingUpgradeConfig(33300, 5, 10),
            new GamblingUpgradeConfig(666600, 8, 50),
            new GamblingUpgradeConfig(9900000, 10, 100),
            new GamblingUpgradeConfig(55500000, 12, 300),
            new GamblingUpgradeConfig(888800000, 15, 800),
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

    private static void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
    }

    public static List<Upgrade> getCreatedUpgrades(List<Investment> investments) {
        createInvestmentUpgrades(investments);
        createTapUpgrades();
        createGlobalIncrementUpgrades();
        createGamblingUpgrades();

        return upgrades;
    }

    private static void createGamblingUpgrades() {
        for (int i = 0; i < gamblingUpgradeConfigs.length; i++) {
            Upgrade upgrade = new GamblingUpgrade(
                    gamblingUpgradeConfigs[i].getPrice(),
                    gamblingUpgradeConfigs[i].getGamblingMoneyMultiplier(),
                    gamblingUpgradeConfigs[i].getRequiredGambling(),
                    colors[i]);
            addUpgrade(upgrade);
        }
    }

    private static void createGlobalIncrementUpgrades() {
        for (int i = 0; i < globalIncrementConfigs.length; i++) {
            Upgrade upgrade = new GlobalIncrementUpgrade(
                    globalIncrementConfigs[i].getPrice(),
                    globalIncrementConfigs[i].getMoneyPerTapMultiplier(),
                    colors[i]);
            addUpgrade(upgrade);
        }
    }

    private static void createTapUpgrades() {
        for (int i = 0; i < tapUpgradeConfigs.length; i++) {
            Upgrade upgrade = new TapUpgrade(
                    tapUpgradeConfigs[i].getPrice(),
                    tapUpgradeConfigs[i].getMoneyPerTapMultiplier(),
                    tapUpgradeConfigs[i].getClickRequired(),
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