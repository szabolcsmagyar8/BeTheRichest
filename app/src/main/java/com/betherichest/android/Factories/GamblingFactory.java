package com.betherichest.android.Factories;

import com.betherichest.android.GameElements.Gambling;
import com.betherichest.android.R;

import java.util.ArrayList;
import java.util.List;

public class GamblingFactory {

    public static List<Gambling> getCreatedGamblings() {
        List<Gambling> gamblings = new ArrayList<>();
        gamblings.add(
                new Gambling(
                        "Scratch Off",
                        100,
                        200,
                        1000,
                        30,
                        R.drawable.scratchoff));
        gamblings.add(
                new Gambling(
                        "Horse Race",
                        500,
                        1000,
                        5000,
                        16,
                        R.drawable.horserace));
        gamblings.add(
                new Gambling(
                        "Slot Machine",
                        5000,
                        20000,
                        100000,
                        1,
                        R.drawable.slotmachine));
        return gamblings;
    }

}
