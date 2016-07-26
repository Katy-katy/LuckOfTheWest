package com.noahdavidson.luckofthewest;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer background_music;
    private MediaPlayer button_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start main menu music
        background_music = MediaPlayer.create(MainActivity.this, R.raw.western_theme_3);
        background_music.start();
        //Set button sound
        button_sound = MediaPlayer.create(MainActivity.this, R.raw.ricochet_credit_cedarstudios);
        //GET CUSTOM FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montague.ttf");
        //SET TITLE FONT
        TextView tx = (TextView)findViewById(R.id.menuTitle);
        tx.setTypeface(custom_font);
        //SET START BUTTON FONT
        Button startButton = (Button)findViewById(R.id.startButton);
        startButton.setTypeface(custom_font);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startGame(View view) {
        background_music.pause();
        button_sound.start();
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }


}
