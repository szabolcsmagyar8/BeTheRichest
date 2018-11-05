package com.betherichest.android.gameElements.achievement;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;

import com.betherichest.android.gameElements.GameElement;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Achievement extends GameElement implements Serializable {
    @Ignore
    static int currentId = 0;

    @ColumnInfo(name = "dateOfAcquiring")
    protected Date dateOfAcquiring;

    @ColumnInfo(name = "unlocked")
    protected boolean unlocked = false;

    @Ignore
    protected Object reward;

    public Achievement(String name, String description, Object reward) {
        this.id = currentId++;
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public Achievement(int id, Date dateOfAcquiring, boolean unlocked) {
        this.id = id;
        this.dateOfAcquiring = dateOfAcquiring;
        this.unlocked = unlocked;
    }

    protected Achievement(Parcel in) {
        unlocked = in.readByte() != 0;
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
