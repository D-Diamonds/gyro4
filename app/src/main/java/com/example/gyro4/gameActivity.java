package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class gameActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    private GameView gameView;
    private String username;

    public String getUsername() {
        return username;
    }

    @Override
    public void onBackPressed() {
        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popcorn);

        gameView = findViewById(R.id.gameView);
        username = getIntent().getStringExtra("username");

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        gameView.setGyroscopeSensor(this.gyroscopeSensor, this.sensorManager);
    }
}
