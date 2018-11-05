package com.betherichest.android.factories;

import com.betherichest.android.gameElements.Leader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderFactory {

    public static List<Leader> getLeaders() {
        List<Leader> leaders = new ArrayList<Leader>();
        leaders.add(new Leader("Bill Gates", 8000000));
        leaders.add(new Leader("Warren Buffer", 4000000));
        leaders.add(new Leader("Mark Zuckerberg", 2000000));
        leaders.add(new Leader("Gerry Cooper", 1000000));
        leaders.add(new Leader("Magyar Szabolcs", 6570000));
        leaders.add(new Leader("Giorgio Armani", 1000000));
        leaders.add(new Leader("Mester", 4555100));

        Collections.sort(leaders, new Comparator<Leader>() {
            @Override
            public int compare(Leader o1, Leader o2) {
                return Double.compare(o2.getMoney(), o1.getMoney());
            }
        });
        return leaders;
    }
}
