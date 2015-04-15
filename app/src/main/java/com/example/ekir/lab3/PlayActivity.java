package com.example.ekir.lab3;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
        drawView.setOnTouchListener(drawView);
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
        public int width;
        public int height;
        public int direction=0;
        public int speed=5;
        private Random rand = new Random();
        GameView gameView;
        int bounce_sound;
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

            if(x<0) {
                x=0;
                rand_direction();
                gameView.play_sound(bounce_sound);
            }
            if(y<0) {
                y=0;
                rand_direction();
                gameView.play_sound(bounce_sound);
            }
            if(x>gameView.getWidth()-width) {
                x=gameView.getWidth()-width;
                rand_direction();
                gameView.play_sound(bounce_sound);
            }
            if(y>gameView.getHeight()-height) {
                y=gameView.getHeight()-height;
                rand_direction();
                gameView.play_sound(bounce_sound);
            }

        }
        public void rand_direction() {
            direction=rand.nextInt(7);
        }
        public void rand_position() {
            y=rand.nextInt(gameView.getHeight()-height);
            x=rand.nextInt(gameView.getWidth()-width);
        }
    }

    class PackmanHunt extends GameView implements View.OnTouchListener {
        Packman packman = new Packman();
        Bitmap packman_image[] = new Bitmap[8];
        int success_sound;
        int score=0;
        long start_time;
        final int game_time_limit=30;
        final int end_force_wait=2;
        Paint paint = null;
        Boolean packman_running = true;

        public PackmanHunt(Context context) {
            super(context);
            for(int i=0;i<=7;i++) {
                packman_image[i]=load_bitmap("packman"+Integer.toString(i)+".png",context);
            }
            packman.direction=3;
            success_sound=load_sound(R.raw.success);
            packman.bounce_sound = load_sound(R.raw.bounce);
            packman.width=100;
            packman.height=100;
            packman.gameView=this;
            start_time = System.currentTimeMillis();

            paint = new Paint();
            paint.setTextSize(50);
            paint.setFakeBoldText(true);
            paint.setColor(Color.BLACK);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(!packman_running) {
                if(elapsed_time() > game_time_limit+end_force_wait) {
                    packman_running = true;
                    score=0;
                    start_time = System.currentTimeMillis();
                    return true;
                }
            }
            int touch_x=(int)event.getX();
            int touch_y=(int)event.getY();
            if(touch_x>packman.x && touch_y>packman.y && touch_x<packman.x+packman.width && touch_y <packman.y+packman.height) {
                play_sound(success_sound);
                packman.rand_position();
                score=score+100;

            }
            return true;
        }

        public int elapsed_time() {
            return (int)(System.currentTimeMillis()-start_time)/1000;
        }

        @Override
        public void gameLoop(Canvas canvas) {

            if(elapsed_time() >= game_time_limit) {
                packman_running=false;
            }

            if(packman_running==false) {
                canvas.drawColor(Color.BLUE);
                canvas.drawText("Times up. You got "+Integer.toString(score)+" score.",10,50,paint);
                if(elapsed_time() > game_time_limit+end_force_wait) {
                    canvas.drawText("Touch to try again.",10,100,paint);
                }
                return;
            }

            // Clear background
            canvas.drawColor(Color.RED);

            // Draw score
            canvas.drawText("Score "+Integer.toString(score)+" Game time "+Long.toString(game_time_limit-elapsed_time()),10,50,paint);

            // Draw packman
            canvas.drawBitmap(packman_image[packman.direction], null, new Rect(packman.x, packman.y, packman.x + packman.width, packman.y + packman.height), null);
            packman.act();
        }
    }


}

