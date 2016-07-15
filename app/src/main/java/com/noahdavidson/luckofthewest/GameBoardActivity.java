package com.noahdavidson.luckofthewest;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.noahdavidson.luckofthewest.player;

/**
 * Created by noahdavidson on 7/6/16.
 */
public class GameBoardActivity extends AppCompatActivity {

    public static player user_player = new player();
    int goldCount = user_player.getGold();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);
        //SET ARROW ANIM
        startAnim();
        //SET FONT TYPE
        setFonts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    public void startBank(View view) {
        Intent intent = new Intent(this, BankActivity.class);
        startActivity(intent);
    }

    public void startSchool(View view) {
        Intent intent = new Intent(this, SchoolActivity.class);
        startActivity(intent);
    }

    public void startSaloon(View view) {
        Intent intent = new Intent(this, SaloonActivity.class);
        startActivity(intent);
    }

    public void startMine(View view) {
        Intent intent = new Intent(this, MineActivity.class);
        startActivity(intent);
    }


    public void startAnim (){
        //START ANIMATION
        ImageView mImageViewFilling1 = (ImageView) findViewById(R.id.imageview_animation_downarrow1);
        ((AnimationDrawable) mImageViewFilling1.getBackground()).start();
        ImageView mImageViewFilling2 = (ImageView) findViewById(R.id.imageview_animation_downarrow2);
        ((AnimationDrawable) mImageViewFilling2.getBackground()).start();
        ImageView mImageViewFilling3 = (ImageView) findViewById(R.id.imageview_animation_downarrow3);
        ((AnimationDrawable) mImageViewFilling3.getBackground()).start();
        ImageView mImageViewFilling4 = (ImageView) findViewById(R.id.imageview_animation_downarrow4);
        ((AnimationDrawable) mImageViewFilling4.getBackground()).start();
    }

    public void setFonts (){
        //GET FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Romantiques.ttf");
        //SET FONTS TO BUTTONS
        Button txBank = (Button)findViewById(R.id.bank_button);
        txBank.setTypeface(custom_font);
        Button txSchool = (Button)findViewById(R.id.schoolButton);
        txSchool.setTypeface(custom_font);
        Button txSaloon = (Button)findViewById(R.id.barButton);
        txSaloon.setTypeface(custom_font);
        Button txMine = (Button)findViewById(R.id.miningButton);
        txMine.setTypeface(custom_font);

        TextView txScore = (TextView)findViewById(R.id.Score);
        //txScore.setTypeface(custom_font);
        txScore.setText("$ " + goldCount);

        TextView txDay = (TextView)findViewById(R.id.Day);
        //txDay.setTypeface(custom_font);
        //txDay.setText("Day" + String.valueOf(txDay));
        txDay.setText("Day" );
    }

}
