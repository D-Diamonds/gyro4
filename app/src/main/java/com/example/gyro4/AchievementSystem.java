package com.example.gyro4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AchievementSystem implements Serializable {
    private final static long serialVersionUID = 101L;


    private static HashMap<Achievement, Boolean> achievements;


    public AchievementSystem() {
        achievements = new HashMap<>();
        achievements.put(new Achievement("Movie Time", "Catch 1 Popcorn", 1), false);
        achievements.put(new Achievement("Addicted", "Play 10 Games", 10), false);
        achievements.put(new Achievement("Super Hungry", "Catch 100 Popcorn", 100), false);
        achievements.put(new Achievement("Popcorn Connoisseur","Win without missing any Popcorn", 1), false);
    }

    public static HashMap<Achievement, Boolean> getAchievements() {
        return achievements;
    }

    public static Boolean getAchievementValue(String name) {
        for (Map.Entry<Achievement, Boolean> entry : achievements.entrySet()) {
            if (entry.getKey().getName().equals(name))
                return entry.getValue();
        }
        return null;
    }

    public static Achievement getAchievementKey(String name) {
        for (Map.Entry<Achievement, Boolean> entry : achievements.entrySet()) {
            if (entry.getKey().getName().equals(name))
                return entry.getKey();
        }
        return null;
    }

    public static void incAchProgress(String name) {
        getAchievementKey(name).incrementProgress();
        if (getAchievementKey(name).isComplete())
            changeToTrue(name);

    }

    public static void changeToTrue(String name) {
        achievements.put(getAchievementKey(name), true);
    }

    public String toString() {
        if (achievements != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<Achievement, Boolean> entry : achievements.entrySet()) {
                stringBuilder.append(entry.getKey()).append("\n").append((entry.getValue()) ? "Achieved" : "Missing").append("\n");
            }
            if (stringBuilder.toString().length() == 0)
                return "No Achievements";
            return stringBuilder.toString();
        }
        return "";
    }

}
