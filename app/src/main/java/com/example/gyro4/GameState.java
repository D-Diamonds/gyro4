package com.example.gyro4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

import java.util.ArrayList;


@SuppressWarnings("WeakerAccess")
public class GameState {

    private int screenWidth;
    private int screenHeight;
    private View gameView;
    private Context context;
    private Player player;

    private ArrayList<Popcorn> popcorn;
    private Bitmap spriteBitmap;
    private float popcornSpawnTimer = 0;

    private int score = 0;
    private int missed = 0;
    private double spawnSpeed;
    private float popcornSpeed;
    private int level = 1;

    private Bitmap[] popcornSprite;

    private float gameTimer = 0;
    private String username;

    private boolean playing;

    private final int GAME_TIME = 2;


    public GameState(View view, Context context) {
        this.context = context;
        gameView = view.findViewById(R.id.gameView);
        
        screenWidth = gameView.getWidth();
        screenHeight = gameView.getHeight();
        popcornSprite = new Bitmap[]{BitmapFactory.decodeResource(context.getResources(), R.drawable.popcorn_1), BitmapFactory.decodeResource(context.getResources(), R.drawable.popcorn_2), BitmapFactory.decodeResource(context.getResources(), R.drawable.popcorn_3)};
        spriteBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_bucket);
        player = new Player(screenWidth,screenWidth / 2 - spriteBitmap.getWidth() / 2, screenHeight - spriteBitmap.getHeight() * 4 / 3, spriteBitmap);
        popcorn = new ArrayList<>();
        username = ((GameActivity) context).getUsername();
        playing = true;
        spawnSpeed = 1.5;
        popcornSpeed = 200;
    }

    public Player getPlayer() {
        return player;
    }

    private void incrementScore() {
        score++;
        ((GameView) gameView).setText(R.id.score, "Popcorn collected: \n" + score);
    }

    private void incrementMissed() {
        missed++;
        ((GameView) gameView).setText(R.id.missed, "Popcorn missed: \n" + missed);
    }

    private void updateTime(float dt) {
        gameTimer += dt;
        ((GameView) gameView).setText(R.id.timer, "Time remaining: " + (int) (GAME_TIME - gameTimer));
    }

    private void endGame() {
        playing = false;
        SharedPreferences preferences = context.getSharedPreferences("GyroData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int pastScore = preferences.getInt(username, -1);
        if (score > pastScore)
            editor.putInt(username, score);
        editor.apply();

        ((GameActivity) context).gameOver(score, (GameActivity) context);
    }

    public void update(float dt) {
        if (playing) {
            updateTime(dt);
            player.update(dt);
        }
        if (playing && gameTimer > GAME_TIME) {
            endGame();
        }
        if (((int) gameTimer) / 10  == level) {
            level++;
            spawnSpeed *= .8;
            popcornSpeed *= 1.1;
        }

        popcornSpawnTimer += dt;
        if (popcornSpawnTimer > spawnSpeed) {
            popcorn.add(new Popcorn(screenWidth, popcornSprite[(int) (Math.random() * popcornSprite.length)], popcornSpeed));
            popcornSpawnTimer = 0;
        }

        for (int i = 0; i < popcorn.size(); i++) {
            popcorn.get(i).update(dt);
            if (playing) {
                if (player.collides(popcorn.get(i))) {
                    incrementScore();
                    popcorn.remove(i);
                    i--;
                    continue;
                }
                if (popcorn.get(i).getY() > screenHeight - spriteBitmap.getHeight()) {
                    incrementMissed();
                    popcorn.remove(i);
                    i--;
                }
            }
        }
    }


    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (playing)
            player.draw(canvas, paint);

        for (Popcorn popcorn : popcorn)
            popcorn.draw(canvas, paint);
    }
}