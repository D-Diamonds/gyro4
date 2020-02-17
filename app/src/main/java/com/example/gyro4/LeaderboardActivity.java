package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/*
    This activity works by taking in the saved leaderboard data and sorting it from highest score to lowest score.
    If the player list overflows, it becomes scrollable.
 */

public class LeaderboardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.returnBtn)
            finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        findViewById(R.id.returnBtn).setOnClickListener(this);

        TextView leaderboards = findViewById(R.id.leaderboard);

        Comparator<Map.Entry<String, Integer>> leaderboardComparator = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        };

        SharedPreferences preferences = getSharedPreferences("GyroData", Context.MODE_PRIVATE);
        @SuppressWarnings("unchecked")
        Map<String, Integer> data = (Map<String, Integer>) preferences.getAll();
        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(data.entrySet());
        Collections.sort(listOfEntries, leaderboardComparator);

        StringBuilder leaderboardString = new StringBuilder();
        int placeCounter = 1;
        for (Map.Entry<String, Integer> entry : listOfEntries) {
            leaderboardString.append(placeCounter).append(". ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            placeCounter++;
        }
        leaderboards.setText(leaderboardString);
    }
}