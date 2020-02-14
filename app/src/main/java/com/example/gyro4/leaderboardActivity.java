package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class leaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        TextView leaderboards = findViewById(R.id.leaderboard);

        String leaderboardString = "";

        SharedPreferences preferences = getSharedPreferences("GyroData", Context.MODE_PRIVATE);
        Map<String, Integer> data = (Map<String, Integer>) preferences.getAll();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            leaderboardString += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        leaderboards.setText(leaderboardString);
    }
}
