package com.example.gyro4;

import java.io.Serializable;

public class Achievement implements Serializable {
    private final static long serialVersionUID = 102L;

    private String name;
    private String description;
    private int progress;
    private int maxProgress;

    public Achievement(String name) {
        this.name = name;
        this.description = "No Desc.";
        progress = 0;
        maxProgress = 1;
    }

    public Achievement(String name, String description, int maxProgress) {
        this.name = name;
        this.description = description;
        this.maxProgress = maxProgress;
        progress = 0;
    }

    public void incrementProgress() {
        if (progress != maxProgress)
            progress++;
    }

    public boolean isComplete() {
        return  progress == maxProgress;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        if (progress != maxProgress)
            return name + " :\n" + description + "\n" + progress + " / " + maxProgress;
        else
            return name + " :\n" + description + "\nCOMPLETE";
    }
}
