package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.startBtn && username.getText().toString().trim().length() > 0) {
            Intent intent = new Intent(this, gameActivity.class);
            System.out.println(username.getText().toString());
            intent.putExtra("username", username.getText().toString());
            startActivity(intent);
        }
        else if (v.getId() == R.id.startBtn && username.getText().toString().trim().length() == 0) {
            username.setError("Please enter a username.");
            return;
        }
        if (v.getId() == R.id.leaderboardBtn)
            startActivity(new Intent(this, leaderboardActivity.class));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameBox);
        username.setText("");

        findViewById(R.id.startBtn).setOnClickListener(this);
        findViewById(R.id.leaderboardBtn).setOnClickListener(this);
    }

}


