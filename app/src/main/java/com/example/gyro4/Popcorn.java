package com.example.gyro4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

@SuppressWarnings("WeakerAccess")
public class Popcorn {
    private int width;
    private int height;
    private Bitmap sprite;
    private int x;
    private int y;
    private float dx;
    private float dy;

    public Popcorn(int screenWidth, Bitmap bitmap, float dy) {
        this.sprite = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        setPosition((int)(Math.random() * (screenWidth - width)), -height);
        this.dx = 0;
        this.dy = dy;
    }

    private void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(float dt) {
            this.x += this.dx * dt;
            this.y += this.dy * dt;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(sprite, null, new Rect(x, y, (x + width), (y + height)), paint);
    }
}
