package com.example.gyro4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.View;

@SuppressWarnings("WeakerAccess")
public class GameThread extends Thread implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private GameState gameState;
    private long lastTime;
    private boolean running;

    public GameThread(SurfaceHolder holder, Context context, View view) {
        this.surfaceHolder = holder;
        this.paint = new Paint();
        this.gameState = new GameState(view, context);
        this.lastTime = System.nanoTime();
        running = true;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        try {
            if (Thread.interrupted())
                throw new InterruptedException();
            while (!Thread.currentThread().isInterrupted() && running) {
                long time = System.nanoTime();

                Canvas canvas = this.surfaceHolder.lockCanvas();
                float dt = ((time - this.lastTime) / 1000000000f);
                if (canvas != null) {
                    this.gameState.update(dt);
                    this.gameState.draw(canvas, this.paint);
                }
                this.surfaceHolder.unlockCanvasAndPost(canvas);
                this.lastTime = time;
            }
        }
        catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!isAlive()) {
            setRunning(true);
            start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (isAlive()) {
            setRunning(false);
        }
    }
}