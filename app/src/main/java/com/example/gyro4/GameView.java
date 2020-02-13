package com.example.gyro4;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameView extends SurfaceView implements SensorEventListener, View.OnTouchListener, SurfaceHolder.Callback {

    private GameThread thread;
    private Context context;

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOnTouchListener(this);
        setFocusable(true);
        System.out.println(getWidth());

        setBackgroundColor(Color.TRANSPARENT);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);

        this.post(new Runnable() {
            @Override
            public void run() {
                setThread(new GameThread(getHolder(), getContext(), new Handler(){
                    @Override
                    public void publish(LogRecord record) {

                    }

                    @Override
                    public void flush() {

                    }

                    @Override
                    public void close() throws SecurityException {

                    }
                }, getThis().getRootView()));
                getThread().start();
            }
        });
    }

    public void setGyroscopeSensor(Sensor gyroscopeSensor, SensorManager sensorManager) {
        this.gyroscopeSensor = gyroscopeSensor;
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE && event.values[1] != 0) {
            //System.out.println("(" + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + ")");
            if (this.thread.getGameState() != null)
                this.thread.getGameState().getPlayer().updatePlayerGyroscope(event.values[1]);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.thread.getGameState().getPlayer().freeze();
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.thread.getGameState().getPlayer().unfreeze();
            return true;
        }
        return false;
    }

    public GameView getThis() {
        return this;
    }

    public Thread getThread() {
        return this.thread;
    }

    public void setThread(GameThread thread) {
        this.thread = thread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!thread.isAlive()) {
            thread = new GameThread(getHolder(), getContext(), new Handler(){
                @Override
                public void publish(LogRecord record) {

                }

                @Override
                public void flush() {

                }

                @Override
                public void close() throws SecurityException {

                }
            }, getThis().getRootView());
            thread.setRunning(true);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (thread.isAlive()) {
            thread.setRunning(false);
        }
    }

}