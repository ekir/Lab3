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
import java.util.Random;


public class PlayActivity extends Activity {
    PackmanHunt drawView;

    @Override public void onPause() {
        super.onPause();
        drawView.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new PackmanHunt(this);
        setContentView(drawView);

    }

    @Override public void onResume() {
        super.onResume();
        drawView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawView.destroy();
    }

    class Packman {
        public int x;
        public int y;
        public int direction=0;
        public int speed=5;
        private Random rand = new Random();

        public void act() {
            switch(direction) {
                case 0:
                    y=y-speed;
                    break;
                case 1:
                    x=x+speed;
                    y=y-speed;
                    break;
                case 2:
                    x=x+speed;
                    break;
                case 3:
                    x=x+speed;
                    y=y+speed;
                    break;
                case 4:
                    y=y+speed;
                    break;
                case 5:
                    x=x-speed;
                    y=y+speed;
                    break;
                case 6:
                    x=x-speed;
                    break;
                case 7:
                    y=y-speed;
                    x=x-speed;
                    break;
            }
        }

        public void rand_direction() {
            direction=rand.nextInt(7);
        }
    }

    class PackmanHunt extends GameView {
        Packman packman = new Packman();
        Bitmap packman_image[] = new Bitmap[8];

        public PackmanHunt(Context context) {
            super(context);
            for(int i=0;i<=7;i++) {
                packman_image[i]=load_bitmap("packman"+Integer.toString(i)+".png",context);
            }
            packman.direction=3;
        }

        @Override
        public void dogameloop(Canvas canvas) {
            canvas.drawColor(Color.RED);
            canvas.drawBitmap(packman_image[packman.direction],null,new Rect(packman.x,packman.y,packman.x+100,packman.y+100),null);

            packman.act();

            if(packman.x>canvas.getWidth()-100) {
                packman.x=canvas.getWidth()-100;
                packman.rand_direction();
            }
            if(packman.y>canvas.getHeight()-100) {
                packman.y=canvas.getHeight()-100;
                packman.rand_direction();
            }
            if(packman.x<0) {
                packman.x=0;
                packman.rand_direction();
            }
            if(packman.y<0) {
                packman.y=0;
                packman.rand_direction();
            }

        }
    }


}

