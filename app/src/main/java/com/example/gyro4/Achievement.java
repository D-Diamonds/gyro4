package com.example.gyro4;

import java.io.Serializable;

public class Achievement implements Serializable {
    private final static long serialVersionUID = 102L;

    private String name;
    private String description;
    private int progress;
    private int maxProgress;
    private int imageID;

    public Achievement(String name) {
        this.name = name;
        this.description = "No Desc.";
        progress = 0;
        maxProgress = 1;
    }

    public Achievement(String name, String description, int maxProgress, int imageID) {
        this.name = name;
        this.description = description;
        this.maxProgress = maxProgress;
        progress = 0;
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
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
