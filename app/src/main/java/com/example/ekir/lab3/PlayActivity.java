package com.example.ekir.lab3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;


/**
 * This activity handles the game itself
 */
public class PlayActivity extends Activity {
    PackmanHunt packmanHunt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packmanHunt = new PackmanHunt(this);
        setContentView(packmanHunt);
    }

    @Override public void onResume() {
        super.onResume();
        packmanHunt.resume();
    }

    @Override public void onPause() {
        super.onPause();
        packmanHunt.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        packmanHunt.destroy();
    }




}

