package com.example.gyro4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.gyro4.R;

import java.util.ArrayList;

public class GameState {

    private int screenWidth;
    private int screenHeight;
    private View view;
    private Context context;
    private Player player;

    private ArrayList<Asteroid> asteroids;
    private float asteroidSpawnTimer = 0;


    public GameState(View view, Context context) {
        this.view = view;
        this.context = context;
        this.screenWidth = view.findViewById(R.id.gameView).getWidth();
        this.screenHeight = view.findViewById(R.id.gameView).getHeight();
        this.player = new Player(this.screenWidth, this.screenHeight, this.screenWidth / 2 - 5, this.screenHeight / 2 - 5, 50);

    }

    public void reset() {

    }

    public Player getPlayer() {
        return this.player;
    }

    public void update(float dt) {
        asteroidSpawnTimer += dt;
        if (asteroidSpawnTimer > 5) {
            asteroids.add(new Asteroid(this.screenWidth, this.screenHeight, 25));
            asteroidSpawnTimer = 0;
        }

        for (Asteroid asteroid : asteroids)
            asteroid.update(dt);

        this.player.update(dt);
    }


    public void draw(Canvas canvas, Paint paint) {
        canvas.drawARGB(255, 255, 255, 255);
        this.player.draw(canvas, paint);

        for (Asteroid asteroid : asteroids)
            asteroid.draw(canvas, paint);
    }
}