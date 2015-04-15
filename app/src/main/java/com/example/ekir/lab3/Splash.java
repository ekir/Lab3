package com.example.ekir.lab3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This activity handles the splash screen
 */
public class Splash extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int wait_time = 2000;
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {

                // make sure we close the splash screen so the user won't come back when it presses back key

                finish();
                Intent intent = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(intent);
            }

        }, wait_time);
    }
}
