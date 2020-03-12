package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/*
    The Achievement activity works by taking a saved Achievement System from the GameState and
    displaying each Achievement and its corresponding icon.
    Due to the use of a HashMap... the order of achievements changes everytime since a HashMap
    is not ordered... with more time I could add a value to the achievement that determines in which
    order to display it... Additionally there is an odd bug that when closing and reopening the app
    completely the save file is corrupted and casuses crashes. Overall while within the app you can
    save and continue dealing with achievements.
 */
public class AchievementsActivity extends AppCompatActivity {

    private DataSaver<AchievementSystem> dataSaver;
    private AchievementSystem achievementSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        createDataSaver();

        findViewById(R.id.returnBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // creates DataSaver
    public void createDataSaver() {
        dataSaver = new DataSaver<>(getApplicationContext(), "achievementData.obj");
        //achievementSystem = new AchievementSystem();
        achievementSystem = dataSaver.onStart();
        if (dataSaver.getDataPointer() != null) {
            System.out.println("Found system");
            System.out.println(achievementSystem.toString());
        }
        else if (achievementSystem == null) {
            achievementSystem = new AchievementSystem();
            System.out.println("Creating new achievement System");
            System.out.println(achievementSystem.toString());
        }
        else
            System.out.println("Moving on");

        buildViews();
    }

    public void buildViews() {
        ArrayList<String> achStr = new ArrayList<>();
        ArrayList<Integer> imageVals = new ArrayList<>();
        ArrayList<Boolean> compVals = new ArrayList<>();
        for (Map.Entry<Achievement, Boolean> achievement : achievementSystem.getAchievements().entrySet()) {
            achStr.add(achievement.getKey().toString());
            imageVals.add(achievement.getKey().getImageID());
            compVals.add(achievement.getValue());
        }


        // For time purposes of completing this project copy paste was used
        // Realistically I would make a function and pass in the 3 arrays and an array of the ids to do this with a loop
        ((TextView) findViewById(R.id.textach1)).setText(achStr.get(0));
        ((TextView) findViewById(R.id.textach1)).setTextColor((compVals.get(0)) ? Color.BLACK : Color.DKGRAY);
        ((TextView) findViewById(R.id.textach2)).setText(achStr.get(1));
        ((TextView) findViewById(R.id.textach2)).setTextColor((compVals.get(1)) ? Color.BLACK : Color.DKGRAY);
        ((TextView) findViewById(R.id.textach3)).setText(achStr.get(2));
        ((TextView) findViewById(R.id.textach3)).setTextColor((compVals.get(2)) ? Color.BLACK : Color.DKGRAY);
        ((TextView) findViewById(R.id.textach4)).setText(achStr.get(3));
        ((TextView) findViewById(R.id.textach4)).setTextColor((compVals.get(3)) ? Color.BLACK : Color.DKGRAY);

        ((ImageView) findViewById(R.id.ach1image)).setImageResource(imageVals.get(0));
        ((ImageView) findViewById(R.id.ach2image)).setImageResource(imageVals.get(1));
        ((ImageView) findViewById(R.id.ach3image)).setImageResource(imageVals.get(2));
        ((ImageView) findViewById(R.id.ach4image)).setImageResource(imageVals.get(3));

    }
}
