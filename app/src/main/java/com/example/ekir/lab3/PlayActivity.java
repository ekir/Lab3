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



    class PackmanHunt extends GameView {
        int x_speed = 1;
        public PackmanHunt(Context context) {
            super(context);
        }

        @Override
        public void dogameloop(Canvas canvas) {
            canvas.drawColor(Color.RED);
            canvas.drawBitmap(lion,null,new Rect(x,10,x+100,100),null);
            x=x+x_speed;
            if(x>canvas.getWidth()) {
                x_speed=-1;

            }
            if(x<0) {
                x_speed=1;
            }
        }
    }


}

