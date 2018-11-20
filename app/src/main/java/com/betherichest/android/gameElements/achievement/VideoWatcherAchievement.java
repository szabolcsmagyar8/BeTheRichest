package com.betherichest.android.gameElements.achievement;

import com.betherichest.android.App;
import com.betherichest.android.R;

public class VideoWatcherAchievement extends Achievement {
    private double requiredVideo;

    public VideoWatcherAchievement(String name, double requiredVideo, Object reward) {
        super(name, String.format("Watch %s ad videos", App.NF.format(requiredVideo)), reward, App.convertThousandsToSIUnit(requiredVideo, true) + "x");
        this.requiredVideo = requiredVideo;
        this.imageResource = R.drawable.video;
    }

    public double getRequiredVideo() {
        return requiredVideo;
    }
}
