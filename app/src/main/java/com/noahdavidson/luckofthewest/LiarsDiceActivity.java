package com.noahdavidson.luckofthewest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabWidget;

/**
 * Created by noahdavidson on 7/12/16.
 */
public class LiarsDiceActivity extends AppCompatActivity {

    private static final String TAG = LiarsDiceActivity.class.getSimpleName();
    int[] matches_img = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six};
    int[] dice_img = new int[]{R.drawable.dice_one,R.drawable.dice_two,R.drawable.dice_three,R.drawable.dice_four,R.drawable.dice_five,R.drawable.dice_six};

    int MATCH_BID = 1;
    int DICE_BID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liars_dice);

        final ImageView match_view = (ImageView) findViewById(R.id.numbers);
        final ImageView dice_view = (ImageView) findViewById(R.id.dice);

        ImageButton match_up = (ImageButton) findViewById(R.id.match_up);
        ImageButton match_down = (ImageButton) findViewById(R.id.match_down);
        ImageButton dice_up = (ImageButton) findViewById(R.id.dice_up);
        ImageButton dice_down = (ImageButton) findViewById(R.id.dice_down);

        setNumMatches(match_view, 0);
        setDiceBid(dice_view, 0);

        if(match_up != null) {
            match_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MATCH_BID += 1;
                    if (MATCH_BID == 7) MATCH_BID = 1;
                    switch (MATCH_BID){
                        case 1: setNumMatches(match_view,0); break;
                        case 2: setNumMatches(match_view,1); break;
                        case 3: setNumMatches(match_view,2); break;
                        case 4: setNumMatches(match_view,3); break;
                        case 5: setNumMatches(match_view,4); break;
                        case 6: setNumMatches(match_view,5); break;
                    }

                    Log.d(TAG,""+MATCH_BID);
                }
            });
        }

        if(match_down != null){
            match_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MATCH_BID-=1;
                    if (MATCH_BID == 0) MATCH_BID = 6;

                    switch (MATCH_BID){
                        case 1: setNumMatches(match_view,0); break;
                        case 2: setNumMatches(match_view,1); break;
                        case 3: setNumMatches(match_view,2); break;
                        case 4: setNumMatches(match_view,3); break;
                        case 5: setNumMatches(match_view,4); break;
                        case 6: setNumMatches(match_view,5); break;
                    }

                    Log.d(TAG,""+MATCH_BID);
                }
            });
        }

        if(dice_up != null){
            dice_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DICE_BID+=1;
                    if (DICE_BID == 7) DICE_BID = 1;

                    switch (DICE_BID){
                        case 1: setDiceBid(dice_view,0); break;
                        case 2: setDiceBid(dice_view,1); break;
                        case 3: setDiceBid(dice_view,2); break;
                        case 4: setDiceBid(dice_view,3); break;
                        case 5: setDiceBid(dice_view,4); break;
                        case 6: setDiceBid(dice_view,5); break;
                    }

                    Log.d(TAG,""+DICE_BID);
                }
            });
        }

        if(dice_down != null){
            dice_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DICE_BID-=1;
                    if (DICE_BID == 0) DICE_BID = 6;

                    switch (DICE_BID){
                        case 1: setDiceBid(dice_view,0); break;
                        case 2: setDiceBid(dice_view,1); break;
                        case 3: setDiceBid(dice_view,2); break;
                        case 4: setDiceBid(dice_view,3); break;
                        case 5: setDiceBid(dice_view,4); break;
                        case 6: setDiceBid(dice_view,5); break;
                    }

                    Log.d(TAG,""+DICE_BID);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setNumMatches(ImageView view, int num){
        view.setImageResource(matches_img[num]);
    }

    public void setDiceBid(ImageView view, int num){
        view.setImageResource(dice_img[num]);
    }
}


