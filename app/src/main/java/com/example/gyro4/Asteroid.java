package com.example.gyro4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Asteroid {

    private float screenWidth;
    private float screenHeight;
    private float radius;
    private float x;
    private float y;
    private float dx;
    private float dy;



    public Asteroid(float screenWidth, float screenHeight, float radius) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.radius = radius;
        setPosition((float) (Math.random() * (screenWidth - radius)) + radius, screenHeight + radius);
        this.dx = 0;
        this.dy = -10;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }

    public void update(float dt) {
            this.x += this.dx * dt;
            this.y += this.dy * dt;
        }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawCircle(this.x, this.y, this.radius, paint);
    }
}
