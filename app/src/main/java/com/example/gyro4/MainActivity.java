package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/*
    This activity handles obtaining the player's username and accessing the game/leaderboards. The game cannot be entered
    unless a username of 0 < length <= 10 is entered.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;

    @Override
    public void onClick(View v) {
        int usernameLength = username.getText().toString().trim().length();
        if (v.getId() == R.id.startBtn && usernameLength > 0) {
            Intent intent = new Intent(this, GameActivity.class);
            System.out.println(username.getText().toString());
            intent.putExtra("username", username.getText().toString());
            startActivity(intent);
        }
        else if (v.getId() == R.id.startBtn && usernameLength == 0) {
            username.setError("Please enter a username.");
            username.setText("");
            return;
        }
        else if (v.getId() == R.id.leaderboardBtn)
            startActivity(new Intent(this, LeaderboardActivity.class));
        else if (v.getId() == R.id.achievementBtn)
            startActivity(new Intent(this, AchievementsActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        username.setText("");
    }

    // used to clear data during development
    private void clearData() {
        SharedPreferences preferences = getSharedPreferences("GyroData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameBox);
        username.setText("");

        findViewById(R.id.startBtn).setOnClickListener(this);
        findViewById(R.id.leaderboardBtn).setOnClickListener(this);
        findViewById(R.id.achievementBtn).setOnClickListener(this);
    }

}