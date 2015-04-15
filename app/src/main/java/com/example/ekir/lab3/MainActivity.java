package com.example.ekir.lab3;

import android.app.Application;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * This activity handles the main menu
 */
public class MainActivity extends ActionBarActivity {
    Button btn_play;
    Button btn_about;
    CheckBox chk_music;
    PackmanHuntApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = (PackmanHuntApplication)getApplication();
        app.init_music();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_play= (Button) findViewById(R.id.btn_play);
        btn_about= (Button) findViewById(R.id.btn_about);
        chk_music = (CheckBox) findViewById(R.id.chk_music);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        chk_music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                app.setMusic(isChecked);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }



}
