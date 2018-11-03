package com.betherichest.android.gameElements;

import java.util.Date;

public class Achievement extends GameElement {
    protected Date dateOfAcquiring;
    protected Object reward;
    protected boolean unlocked = false;

    public Achievement(String name, String description, Object reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public Date getDateOfAcquiring() {
        return dateOfAcquiring;
    }

    public Object getReward() {
        return reward;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unLock() {
        unlocked = true;
    }
}
