package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

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
        if (dataSaver.getDataPointer() != null)
            System.out.println(achievementSystem.toString());
        if (achievementSystem == null && dataSaver.getDataPointer() == null) {
            achievementSystem = new AchievementSystem();
            System.out.println(achievementSystem.toString());
        }

        buildText();
    }

    public void buildText() {
            ArrayList<String> achStr = new ArrayList<>();
            for (Map.Entry<Achievement, Boolean> achievement : achievementSystem.getAchievements().entrySet()) {
                achStr.add(achievement.getKey().toString());
            }
            ((TextView) findViewById(R.id.textach1)).setText(achStr.get(0));
            ((TextView) findViewById(R.id.textach2)).setText(achStr.get(1));
            ((TextView) findViewById(R.id.textach3)).setText(achStr.get(2));
            ((TextView) findViewById(R.id.textach4)).setText(achStr.get(3));
    }
}
