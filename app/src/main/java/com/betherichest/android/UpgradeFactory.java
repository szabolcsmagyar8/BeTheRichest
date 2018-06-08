package com.betherichest.android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 2018.03.22..
 */

class InvestmentUpgradeRequirements {
    private int basePriceMultiplier;
    private int requiredRank;

    public int getBasePriceMultiplier() {
        return basePriceMultiplier;
    }

    public int getRequiredRank() {
        return requiredRank;
    }

    public InvestmentUpgradeRequirements(int basePriceMultiplier, int requiredRank) {
        this.basePriceMultiplier = basePriceMultiplier;
        this.requiredRank = requiredRank;
    }
}

class TapUpgradeRequirements {
    private double price;
    private int moneyPerTapMultiplier;

    public int getMoneyPerTapMultiplier() {
        return moneyPerTapMultiplier;
    }

    public double getPrice() {
        return price;
    }

    public TapUpgradeRequirements(double price, int moneyPerTapMultiplier) {
        this.price = price;
        this.moneyPerTapMultiplier = moneyPerTapMultiplier;
    }
}

public class UpgradeFactory {
//    private static int[] requiredRanksForInvestmentUpgrades = new int[]{1, 5, 10, 20, 30, 50, 70, 100};
//    private static int[] investmentBasePriceMultipliers = new int[]{10, 50, 500, 5000, 10000, 50000, 100000, 500000}; // upgrade price = investment base price*
//    private static Object[][] tapUpgrades = new Object[][]{{1000, 2}, {5000, 2}, {20000, 3}, {50000, 3}, {200000, 5}, {1000000, 5}, {5000000, 5}, {20000000, 8}};

    private static InvestmentUpgradeRequirements[] investmentUpgradeRequirements = new InvestmentUpgradeRequirements[]{
            new InvestmentUpgradeRequirements(10, 1),
            new InvestmentUpgradeRequirements(50, 5),
            new InvestmentUpgradeRequirements(500, 10),
            new InvestmentUpgradeRequirements(5000, 20),
            new InvestmentUpgradeRequirements(10000, 30),
            new InvestmentUpgradeRequirements(50000, 50),
            new InvestmentUpgradeRequirements(100000, 70),
            new InvestmentUpgradeRequirements(500000, 100)};

    private static TapUpgradeRequirements[] tapUpgradeRequirements = new TapUpgradeRequirements[]{
            new TapUpgradeRequirements(1000, 2),
            new TapUpgradeRequirements(5000, 2),
            new TapUpgradeRequirements(20000, 3),
            new TapUpgradeRequirements(50000, 3),
            new TapUpgradeRequirements(200000, 5),
            new TapUpgradeRequirements(1000000, 5),
            new TapUpgradeRequirements(5000000, 5),
            new TapUpgradeRequirements(20000000, 8)};

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
            int[] upgradeEffectMultipliersForActualInvestment = investment.getUpgradeEffectMultipliers();
            for (int i = 0; i < upgradeEffectMultipliersForActualInvestment.length; i++) {
                Upgrade upgrade = new InvestmentUpgrade(
                        "",
                        basePrice * investmentUpgradeRequirements[i].getBasePriceMultiplier(),
                        upgradeEffectMultipliersForActualInvestment[i],
                        investmentUpgradeRequirements[i].getRequiredRank(),
                        investment.getImageResource(),
                        10,
                        investment);
                addUpgrade(upgrade);
                investment.addRelevantUpgrade(upgrade);
            }
        }
        for (TapUpgradeRequirements tapUpgrade : tapUpgradeRequirements) {
            Upgrade upgrade = new TapUpgrade(
                    "",
                    tapUpgrade.getPrice(),
                    tapUpgrade.getMoneyPerTapMultiplier(),
                    R.drawable.click,
                    10);
            addUpgrade(upgrade);
        }
    }
}
