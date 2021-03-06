package com.betherichest.android.gameElements.achievement;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.betherichest.android.gameElements.GameElement;

import java.util.Date;

@Entity
public class Achievement extends GameElement {
    @Ignore
    static int currentId = 0;
    @ColumnInfo(name = "dateOfAcquiring")
    protected Date dateOfAcquiring;
    @ColumnInfo(name = "unlocked")
    protected boolean unlocked = false;
    @Ignore
    protected Object reward;
    @Ignore
    protected String text;

    public Achievement(String name, String description, Object reward, String text) {
        this.id = currentId++;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.text = text;
    }

    public Achievement(int id, Date dateOfAcquiring) {
        this.id = id;
        this.dateOfAcquiring = dateOfAcquiring;
        this.unlocked = unlocked;
    }

    public Date getDateOfAcquiring() {
        return dateOfAcquiring;
    }

    public Object getReward() {
        return reward;
    }

    public String getText() {
        return text;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setDateOfAcquiring(Date dateOfAcquiring) {
        this.dateOfAcquiring = dateOfAcquiring;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void unLock() {
        unlocked = true;
        dateOfAcquiring = new Date();
    }
}
