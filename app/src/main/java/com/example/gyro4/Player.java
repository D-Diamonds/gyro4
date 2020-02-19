package com.example.gyro4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

@SuppressWarnings("WeakerAccess")
public class Player {

    private int screenWidth;
    private int width;
    private int height;
    private Bitmap sprite;
    private int x;
    private int y;

    private enum Direction {
        LEFT, RIGHT
    }

    private float leftSpeed;
    private float rightSpeed;
    private Direction direction;

    private boolean frozen;


    public Player(int screenWidth, int x, int y, Bitmap bitmap) {
        this.screenWidth = screenWidth;
        this.sprite = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        setPosition(x, y);
        zeroSpeeds();
        this.frozen = false;
    }

    private void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void freeze() {
        this.frozen = true;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public void updatePlayerGyroscope(float zAxisChange) {
        zAxisChange *= 250;
        if (zAxisChange > 100) {
            if (direction == Direction.LEFT) {
                zeroSpeeds();
            }
            direction = Direction.RIGHT;
            if (zAxisChange > rightSpeed)
                rightSpeed = zAxisChange;
        }
        if (zAxisChange < -100) {
            if (direction == Direction.RIGHT) {
                zeroSpeeds();
            }
            direction = Direction.LEFT;
            if (Math.abs(zAxisChange) > Math.abs(leftSpeed))
                leftSpeed = zAxisChange;
        }
    }

    public boolean collides(Object o) {
        Popcorn p = (Popcorn) o;
        return p.getX() > x && p.getX() + p.getWidth() < x + width && p.getY() + p.getHeight() > y && p.getY() < y + p.getHeight() + 2;
    }

    private void zeroSpeeds() {
        leftSpeed = 0;
        rightSpeed = 0;
    }

    public void update(float dt) {
        if (this.x < 0) {
            zeroSpeeds();
            this.x = 0;
        }
        if (this.x + this.width > this.screenWidth) {
            zeroSpeeds();
            this.x = this.screenWidth - this.width;
        }

        if (!this.frozen) {
            if (direction == Direction.RIGHT)
                this.x += this.rightSpeed * dt;
            else if (direction == Direction.LEFT)
                this.x += this.leftSpeed * dt;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(sprite, null, new Rect(x, y, x + width, y + height), paint);
    }
}