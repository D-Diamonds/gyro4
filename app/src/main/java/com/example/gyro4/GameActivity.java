package com.example.gyro4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

/*
    This activity handles the game state. The game works by providing the player with 60 seconds to collect as much of the
    falling popcorn as possible. The popcorn must be caught in a certain "bounding box" of the sprite for a collection to be
    added. When the time runs out an alert is displayed with the player's score and an option to either play again
    or return to the MainActivity.

    Note: *Gyroscope has funky behaviour on motions that are too "hard" so expect odd movement of the player
    if you slam the phone.

    Once the GameState ends an option for the user to share their score is displayed as seen in
    gameOVer() with an Alertdialog
 */

public class GameActivity extends AppCompatActivity {

    private String username;

    public String getUsername() {
        return username;
    }

    public void gameOver(final int score, final GameActivity gameActivity) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(gameActivity);
                builder.setMessage("Congrats on collecting " + score + " popcorn!\nWould you like to play again?");
                builder.setCancelable(false);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ((GameThread)((GameView) findViewById(R.id.gameView)).getThread()).setRunning(false);
                                recreate();
                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ((GameThread)((GameView) findViewById(R.id.gameView)).getThread()).setRunning(false);
                                finish();
                            }
                        });
                builder.setNeutralButton("Share",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ((GameThread)((GameView) findViewById(R.id.gameView)).getThread()).setRunning(false);
                                Intent sendData = new Intent(Intent.ACTION_SEND);
                                sendData.setType("text/plain");
                                sendData.putExtra(Intent.EXTRA_TEXT, "I collected " + score + " popcorn in the Gyro Popcorn game!");
                                startActivity(Intent.createChooser(sendData, "Share via"));
                                finish();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popcorn);

        GameView gameView = findViewById(R.id.gameView);
        username = getIntent().getStringExtra("username");

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        sensorManager.registerListener(gameView, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);

    }
}
