package com.noahdavidson.luckofthewest;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GET CUSTOM FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Romantiques.ttf");
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
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }


}
