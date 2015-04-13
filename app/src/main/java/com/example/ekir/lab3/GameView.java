package com.example.ekir.lab3;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ekir on 2015-04-13.
 */
public abstract class GameView extends SurfaceView implements Runnable {
    SurfaceHolder surface;
    Bitmap lion = null;
    volatile boolean game_paused = false;
    volatile boolean game_running = true;
    int x = 0;

    public GameView(Context context) {
        super(context);
        surface = getHolder();
        lion = load_bitmap("packman3.png",context);
        Thread gameloop = null;
        gameloop = new Thread(this);
        gameloop.start();
    }

    Bitmap load_bitmap(String filename,Context context) {
        Bitmap result = null;
        try {
            AssetManager assets = context.getAssets();
            InputStream istream = assets.open(filename);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            result = BitmapFactory.decodeStream(istream,null,options);
            istream.close();
            return result;
        } catch (IOException e) {
            return null;
        }
    }

    public void resume() {
        game_paused = false;
    }

    public void pause() {
        game_paused = true;
    }

    public void destroy() {
        game_running = false;
    }

    public void run() {
        while(game_running) {
            if(game_paused) {
                continue;
            }
            if (!surface.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surface.lockCanvas();
            dogameloop(canvas);
            surface.unlockCanvasAndPost(canvas);
        }
    }
    public abstract void dogameloop(Canvas canvas);
}