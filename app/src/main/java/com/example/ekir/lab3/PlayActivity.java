package com.example.ekir.lab3;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;


public class PlayActivity extends Activity {
    DrawView drawView;

    @Override public void onPause() {
        super.onPause();
        drawView.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        setContentView(drawView);

    }

    @Override public void onResume() {
        super.onResume();
        drawView.resume();
    }

    public class DrawView extends SurfaceView implements Runnable {
        SurfaceHolder surface;
        Bitmap lion = null;
        Thread gameloop = null;
        volatile boolean game_paused = false;

        public DrawView(Context context) {
            super(context);
            surface = getHolder();
            lion = load_bitmap("packman3.png",context);
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

        public void run() {
            int x = 0;
            while(true) {
                if(game_paused) {
                    continue;
                }
                if (!surface.getSurface().isValid()) {
                    continue;
                }
                Canvas canvas = surface.lockCanvas();
                canvas.drawColor(Color.RED);
                canvas.drawBitmap(lion,null,new Rect(x,10,x+100,100),null);
                x++;
                surface.unlockCanvasAndPost(canvas);
            }
        }
    }
}
