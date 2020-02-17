package com.example.gyro4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class GameView extends SurfaceView implements SensorEventListener, View.OnTouchListener {

    private GameThread thread;
    private Context context;



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
                setThread(new GameThread(getHolder(), getContext(), getThis().getRootView()));
                getThread().start();
            }
        });
    }

    public void setText(final int id, final String text) {
        ((Activity) this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) ((Activity) context).findViewById(id)).setText(text);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE && event.values[2] != 0) {
            if (this.thread.getGameState() != null)
                this.thread.getGameState().getPlayer().updatePlayerGyroscope(-event.values[2]);

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

    @Override
    public boolean performClick() {
        return super.performClick();
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


}