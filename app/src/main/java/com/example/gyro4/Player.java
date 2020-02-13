package com.example.gyro4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {

    private int screenWidth;
    private int screenHeight;
    private int width;
    private int height;
    private Bitmap sprite;
    private int x;
    private int y;
    private float dx;
    private float dy;

    private float leftSpeed;
    private float rightSpeed;
    private String direction;

    private boolean frozen;


    public Player(int screenWidth, int screenHeight, int x, int y, Bitmap bitmap) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.sprite = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        setPosition(x, y);
        this.dx = 0;
        this.dy = 0;
        this.leftSpeed = 0;
        this.rightSpeed = 0;
        this.direction = "";
        this.frozen = false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void freeze() {
        this.frozen = true;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public void updatePlayerGyroscope(float yAxisChange) {
        yAxisChange *= 50;
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
    }

    public boolean collides(Object o) {
        Popcorn p = (Popcorn) o;
        return p.getX() > x && p.getX() + p.getWidth() < x + width && p.getY() + p.getHeight() > y && p.getY() < y + p.getHeight() + 2;
    }


    public void update(float dt) {
//        if (this.currentYRotation < -1)
//            this.dx = -SPEED;
//        else if (this.currentYRotation > 1)
//            this.dx = SPEED;
//        else
//            this.dx = 0;

        if (this.x < 0) {
            this.dx = 0;
            this.x = 0;
        }
        if (this.x + this.width > this.screenWidth) {
            this.dx = 0;
            this.x = this.screenWidth - this.width;
        }

        if (!this.frozen) {
            if (direction.equals("right"))
                this.x += this.rightSpeed * dt;
            else if (direction.equals("left"))
                this.x += this.leftSpeed * dt;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(sprite, null, new Rect(x, y, x + width, y + height), paint);
    }
}