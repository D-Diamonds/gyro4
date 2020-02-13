package com.example.gyro4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

public class GameState {

    private int screenWidth;
    private int screenHeight;
    private View view;
    private Context context;
    private Player player;

    private ArrayList<Popcorn> popcorns;
    private Bitmap spriteBmap;
    private float popcornSpawnTimer = 0;

    private Bitmap[] popcornSprite;


    public GameState(View view, Context context) {
        this.view = view;
        this.context = context;
        this.screenWidth = view.findViewById(R.id.gameView).getWidth();
        this.screenHeight = view.findViewById(R.id.gameView).getHeight();
        popcornSprite = new Bitmap[]{BitmapFactory.decodeResource(context.getResources(), R.drawable.popcorn_1), BitmapFactory.decodeResource(context.getResources(), R.drawable.popcorn_2), BitmapFactory.decodeResource(context.getResources(), R.drawable.popcorn_3)};
        spriteBmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_bucket);
        this.player = new Player(this.screenWidth, this.screenHeight, this.screenWidth / 2 - spriteBmap.getWidth() / 2, this.screenHeight - spriteBmap.getHeight() * 4 / 3, spriteBmap);
        this.popcorns = new ArrayList<>();

    }

    public Player getPlayer() {
        return this.player;
    }

    public void update(float dt) {
        popcornSpawnTimer += dt;
        if (popcornSpawnTimer > 1.5) {
            popcorns.add(new Popcorn(this.screenWidth, this.screenHeight, popcornSprite[(int) (Math.random() * popcornSprite.length)]));
            popcornSpawnTimer = 0;
        }

        for (int i = 0; i < popcorns.size(); i++) {
            popcorns.get(i).update(dt);
            if (player.collides(popcorns.get(i))) {
                popcorns.remove(i);
                i--;
                continue;
            }
            if (popcorns.get(i).getY() > this.screenHeight - spriteBmap.getHeight()) {
                popcorns.remove(i);
                i--;
                continue;
            }
        }

        this.player.update(dt);
    }


    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        this.player.draw(canvas, paint);

        for (Popcorn popcorn : popcorns)
            popcorn.draw(canvas, paint);
    }
}