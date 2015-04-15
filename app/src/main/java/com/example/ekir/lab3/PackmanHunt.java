package com.example.ekir.lab3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * This class describes the itself. It extends GameView which is my game engine
 */
class PackmanHunt extends GameView implements View.OnTouchListener {
    // The packman object
    Packman packman = new Packman();
    // Holds the graphics for packman
    Bitmap packman_image[] = new Bitmap[8];
    int success_sound;
    // Score in the game
    int score=0;
    // The time the game started
    long start_time;
    // Time the player have in the game
    final int game_time_limit=30;
    // Time player have to see the result screen before continuing
    final int end_force_wait=2;

    Paint paint = null;

    // Says if the game is running (otherwise it has finished and is in the end screen)
    Boolean packman_running = true;

    /**
     * Constructor for the game
     * @param context
     */
    public PackmanHunt(Context context) {
        super(context);
        // Loads the graphics
        for(int i=0;i<=7;i++) {
            packman_image[i]=load_bitmap("packman"+Integer.toString(i)+".png",context);
        }

        // Loads packman data
        packman.direction=3;
        packman.bounce_sound = load_sound(R.raw.bounce);
        packman.width=100;
        packman.height=100;
        packman.gameView=this;

        // Loads the success sound
        success_sound=load_sound(R.raw.success);

        // Init the paint
        paint = new Paint();
        paint.setTextSize(50);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);


        // Start the game time
        start_time = System.currentTimeMillis();

        // Set on touch listener
        this.setOnTouchListener(this);
    }

    /**
     * Handling the touch screen inputs
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // If we are not running then we are at end screen
        if(!packman_running) {
            if(elapsed_time() > game_time_limit+end_force_wait) {
                packman_running = true;
                score=0;
                start_time = System.currentTimeMillis();
                return true;
            }
        }
        // Otherwise we are in normal game
        int touch_x=(int)event.getX();
        int touch_y=(int)event.getY();
        if(touch_x>packman.x && touch_y>packman.y && touch_x<packman.x+packman.width && touch_y <packman.y+packman.height) {
            play_sound(success_sound);
            packman.rand_position();
            score=score+100;

        }
        return true;
    }

    /**
     * Calculate time elapsed in the game
     * @return time elapsed in seconds
     */
    public int elapsed_time() {
        return (int)(System.currentTimeMillis()-start_time)/1000;
    }

    /**
     * The game loop - called from the game engine (GameView)
     * @param canvas Canvas to paint on
     */
    @Override
    public void gameLoop(Canvas canvas) {

        if(elapsed_time() >= game_time_limit) {
            packman_running=false;
        }

        if(packman_running==false) {
            canvas.drawColor(Color.BLUE);
            canvas.drawText("Time's up. You got "+Integer.toString(score)+" score.",10,50,paint);
            if(elapsed_time() > game_time_limit+end_force_wait) {
                canvas.drawText("Touch to try again.",10,100,paint);
            }
            return;
        }

        // Clear background
        canvas.drawColor(Color.RED);

        // Draw score
        canvas.drawText("Score: "+Integer.toString(score)+". Time left: "+Long.toString(game_time_limit-elapsed_time()),10,50,paint);

        // Draw packman
        canvas.drawBitmap(packman_image[packman.direction], null, new Rect(packman.x, packman.y, packman.x + packman.width, packman.y + packman.height), null);
        packman.act();
    }

    /**
     * Packman class - describes the packman in the game
     */
    class Packman {
        // Packmans x, y, width, height, direction and speed
        public int x;
        public int y;
        public int width;
        public int height;
        public int direction=0;
        public int speed=5;
        // Random number generator
        private Random rand = new Random();
        // Reference to the game view, so that we can get width and height and play sounds
        GameView gameView;
        // Reference to the sound when bouncing on walls
        int bounce_sound;

        /**
         * The main loop method for packman
         */
        public void act() {
            // Move packman ddepending on his direction
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
            // Collision detect with the walls. If we bounce we randomize a new direction
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
        // Random direction
        public void rand_direction() {
            direction=rand.nextInt(7);
        }
        // Random position
        public void rand_position() {
            y=rand.nextInt(gameView.getHeight()-height);
            x=rand.nextInt(gameView.getWidth()-width);
        }
    }
}