package com.example.gyro4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {

    private float screenWidth;
    private float screenHeight;
    private float radius;
    private float x;
    private float y;
    private float dx;
    private float dy;

    private float leftSpeed;
    private float rightSpeed;
    private String direction;

    private boolean frozen;

    public Player(float screenWidth, float screenHeight, float x, float y, float radius) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.radius = radius;
        setPosition(x, y);
        this.dx = 0;
        this.dy = 0;
        this.leftSpeed = 0;
        this.rightSpeed = 0;
        this.direction = "";
        this.frozen = false;
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

    public void freeze() {
        this.frozen = true;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public void updatePlayerGyroscope(float yAxisChange) {
        if (yAxisChange > 0) {
            if (direction.equals("left")) {
                rightSpeed = 0;
                leftSpeed = 0;
            }
            direction = "right";
            if (yAxisChange > rightSpeed)
                rightSpeed = yAxisChange;
        }
        if (yAxisChange < 0) {
            if (direction.equals("right")) {
                leftSpeed = 0;
                rightSpeed = 0;
            }
            direction = "left";
            if (Math.abs(yAxisChange) > Math.abs(leftSpeed))
                leftSpeed = yAxisChange;
        }
        System.out.println("Current direction: " + direction);
        System.out.println("Current right speed: " + rightSpeed);
        System.out.println("Current left speed: " + leftSpeed);
    }


    public void update(float dt) {
//        if (this.currentYRotation < -1)
//            this.dx = -SPEED;
//        else if (this.currentYRotation > 1)
//            this.dx = SPEED;
//        else
//            this.dx = 0;

        if (this.x - this.radius < 0) {
            this.dx = 0;
            this.x = this.radius;
        }
        if (this.x + this.radius > this.screenWidth) {
            this.dx = 0;
            this.x = this.screenWidth - this.radius;
        }

        if (!this.frozen) {
            if (direction.equals("right"))
                this.x += this.rightSpeed * dt;
            else if (direction.equals("left"))
                this.x += this.leftSpeed * dt;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        canvas.drawCircle(this.x, this.y, this.radius, paint);
    }
}