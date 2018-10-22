package com.betherichest.android.gameElements;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Achievement extends GameElement implements Observer {
    private Date dateOfAcquiring;
    private Object reward;
    private List<Object> requirements;
    private boolean unlocked = false;

    public Achievement(String name, String description, Object reward, List<Object> requirements) {
        this.description = description;
        this.name = name;
        this.reward = reward;
        this.requirements = requirements;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
