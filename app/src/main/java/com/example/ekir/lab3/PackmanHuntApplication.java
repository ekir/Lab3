package com.example.ekir.lab3;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

/**
 * This is the data shared between activities
 * Only the music player is shared so that different activities can start and stop it
 * Actually in the current implementation this is only used by the MainActivity
 * but more activities might use it later
 */
public class PackmanHuntApplication extends Application {
    private MediaPlayer MediaSpelaren = null;
    private boolean music=false;

    public void init_music() {
        if(MediaSpelaren!=null) {
            return;
        }
        MediaSpelaren = MediaPlayer.create(this, R.raw.french);
        MediaSpelaren.setLooping(true);
        MediaSpelaren.setVolume(0.1f, 0.1f);
        MediaSpelaren.start();
    }

    public void setMusic(boolean tmusic) {
        music = tmusic;
        if(music) {
           MediaSpelaren.start();
        } else {
           MediaSpelaren.pause();
        }
    }

    public boolean getMusic() {
        return music;
    }
}
