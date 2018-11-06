package com.betherichest.android.factories;

import com.betherichest.android.gameElements.Leader;

import java.util.ArrayList;
import java.util.List;

public class LeaderFactory {

    public static List<Leader> getLeaders() {
        List<Leader> leaders = new ArrayList<>();
        leaders.add(new Leader("Bill Gates", 86000000000d, 1.4));
        leaders.add(new Leader("Warren Buffet", 55000000000d, 1.18));
        leaders.add(new Leader("Mark Zuckerberg", 30000000000d, 1.55));
        leaders.add(new Leader("Gerry Cooper", 9400000000d, 1.25));
        leaders.add(new Leader("Giorgio Armani", 3000000000d, 1.11));
        leaders.add(new Leader("Amanico Ortega", 500000000, 1.19));
        leaders.add(new Leader("Jeff Bezos", 80000000, 1.08));
        leaders.add(new Leader("David Kock", 4000000, 1.17));
        leaders.add(new Leader("Jack Ma", 650000, 1.1));
        leaders.add(new Leader("Paul Allen", 76000, 1.04));
        leaders.add(new Leader("Donald Bren", 3200, 1.21));
        leaders.add(new Leader("Elon Musk", 800, 3.87));
        leaders.add(new Leader("Kristof Kulcsar", 50, 15));

        return leaders;
    }
}
