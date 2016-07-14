package com.noahdavidson.luckofthewest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabWidget;

/**
 * Created by noahdavidson on 7/12/16.
 */
public class LiarsDiceActivity extends AppCompatActivity {

    int[] matches_img = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six};
    int[] number_matches = new int[]{1,2,3,4,5,6};

    int[] dice_img = new int[]{R.drawable.dice_one,R.drawable.dice_two,R.drawable.dice_three,R.drawable.dice_four,R.drawable.dice_five,R.drawable.dice_six};
    int[] num_dice = new int[]{1,2,3,4,5,6};

    int MATCH_BID;
    int DICE_BID;

    ImageView match_view = (ImageView) findViewById(R.id.numbers);
    ImageView dice_view = (ImageView) findViewById(R.id.numbers);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liars_dice);

        ImageButton match_up = (ImageButton) findViewById(R.id.match_up);
        ImageButton match_down = (ImageButton) findViewById(R.id.match_down);
        ImageButton dice_up = (ImageButton) findViewById(R.id.dice_up);
        ImageButton dice_down = (ImageButton) findViewById(R.id.dice_down);

        setNumMatches(0);
        setDiceBid(0);

        match_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MATCH_BID+=1;
                if(MATCH_BID == 6) MATCH_BID = 0;
                    if (MATCH_BID == 0){
                        setNumMatches(0);
                    }else if (MATCH_BID == 1){
                        setNumMatches(1);
                    }else if (MATCH_BID == 2){
                        setNumMatches(2);
                    }else if (MATCH_BID == 3){
                        setNumMatches(3);
                    }else if (MATCH_BID == 4){
                        setNumMatches(4);
                    }else if (MATCH_BID == 5){
                        setNumMatches(5);
                    }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setNumMatches(int num){
        match_view.setImageResource(matches_img[num]);
        MATCH_BID = number_matches[num];
    }

    public void setDiceBid(int num){
        dice_view.setImageResource(dice_img[num]);
        DICE_BID = num_dice[num];
    }
}


