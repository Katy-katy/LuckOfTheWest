package com.noahdavidson.luckofthewest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.Toast;

import com.noahdavidson.luckofthewest.TurnPhaseActivity;
import com.noahdavidson.luckofthewest.DiceData;

/**
 * Created by noahdavidson on 7/12/16.
 */


public class LiarsDiceActivity extends AppCompatActivity{

    TurnPhaseActivity tP = new TurnPhaseActivity();
    DiceData P1 = new DiceData();
    DiceData P2 = new DiceData();
    DiceData P3 = new DiceData();
    DiceData P4 = new DiceData();
    DiceData P5 = new DiceData();


    static int totalDice[] = new int[6];


    private static final String TAG = LiarsDiceActivity.class.getSimpleName();
    int[] matches_img = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,
            R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten,
            R.drawable.eleven,R.drawable.twelve,R.drawable.thirteen,R.drawable.fourteen,R.drawable.fifteen,
            R.drawable.sixteen,R.drawable.seventeen,R.drawable.eightteen,R.drawable.nineteen,R.drawable.twenty,
            R.drawable.twentyone,R.drawable.twentytwo,R.drawable.twentythree,R.drawable.twentyfour,R.drawable.twentyfive};

    int[] dice_img = new int[]{R.drawable.dice_one,R.drawable.dice_two,R.drawable.dice_three,R.drawable.dice_four,R.drawable.dice_five,
            R.drawable.dice_six};

    public static int MATCH_BID = 1;
    public static int DICE_BID = 1;

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

        Button call = (Button) findViewById(R.id.call_button);
        Button liar = (Button) findViewById(R.id.liar_button);

        setNumMatches(match_view, 0);
        setDiceBid(dice_view, 0);

        if(call != null){
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MATCH_BID < tP.getLastCall () || (MATCH_BID == tP.getLastCall() && DICE_BID <= tP.getLastDice())){
                        Context context = getApplicationContext();
                        CharSequence text = "Bet Higher";
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(context, text,duration).show();
                    }
                    tP.setLastCall(MATCH_BID);
                    tP.setLastDice(DICE_BID);
                }
            });
        }

        if(liar !=null){
            liar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tP.isFirstTurn() == true){
                    }else{
                        //if(tP.getLastCall() >= )
                    }
                }
            });
        }


        if(match_up != null) {
            match_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MATCH_BID += 1;
                    if (MATCH_BID == 26) MATCH_BID = 1;

                        runOnUiThread(new Runnable() {
                        public void run() {
                            switch (MATCH_BID){
                                case 1: setNumMatches(match_view,0); break;
                                case 2: setNumMatches(match_view,1); break;
                                case 3: setNumMatches(match_view,2); break;
                                case 4: setNumMatches(match_view,3); break;
                                case 5: setNumMatches(match_view,4); break;
                                case 6: setNumMatches(match_view,5); break;
                                case 7: setNumMatches(match_view,6); break;
                                case 8: setNumMatches(match_view,7); break;
                                case 9: setNumMatches(match_view,8); break;
                                case 10: setNumMatches(match_view,9); break;
                                case 11: setNumMatches(match_view,10); break;
                                case 12: setNumMatches(match_view,11); break;
                                case 13: setNumMatches(match_view,12); break;
                                case 14: setNumMatches(match_view,13); break;
                                case 15: setNumMatches(match_view,14); break;
                                case 16: setNumMatches(match_view,15); break;
                                case 17: setNumMatches(match_view,16); break;
                                case 18: setNumMatches(match_view,17); break;
                                case 19: setNumMatches(match_view,18); break;
                                case 20: setNumMatches(match_view,19); break;
                                case 21: setNumMatches(match_view,20); break;
                                case 22: setNumMatches(match_view,21); break;
                                case 23: setNumMatches(match_view,22); break;
                                case 24: setNumMatches(match_view,23); break;
                                case 25: setNumMatches(match_view,24); break;
                            }
                        }
                    });
                }
            });
        }


        if(match_down != null){
            match_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MATCH_BID-=1;
                    if (MATCH_BID == 0) MATCH_BID = 25;

                    runOnUiThread(new Runnable() {
                        public void run() {
                            switch (MATCH_BID) {
                                case 1:setNumMatches(match_view, 0);break;
                                case 2:setNumMatches(match_view, 1);break;
                                case 3:setNumMatches(match_view, 2);break;
                                case 4:setNumMatches(match_view, 3);break;
                                case 5:setNumMatches(match_view, 4);break;
                                case 6:setNumMatches(match_view, 5);break;
                                case 7:setNumMatches(match_view, 6);break;
                                case 8:setNumMatches(match_view, 7);break;
                                case 9:setNumMatches(match_view, 8);break;
                                case 10:setNumMatches(match_view, 9);break;
                                case 11:setNumMatches(match_view, 10);break;
                                case 12:setNumMatches(match_view, 11);break;
                                case 13:setNumMatches(match_view, 12);break;
                                case 14:setNumMatches(match_view, 13);break;
                                case 15:setNumMatches(match_view, 14);break;
                                case 16:setNumMatches(match_view, 15);break;
                                case 17:setNumMatches(match_view, 16);break;
                                case 18:setNumMatches(match_view, 17);break;
                                case 19:setNumMatches(match_view, 18);break;
                                case 20:setNumMatches(match_view, 19);break;
                                case 21:setNumMatches(match_view, 20);break;
                                case 22:setNumMatches(match_view, 21);break;
                                case 23:setNumMatches(match_view, 22);break;
                                case 24:setNumMatches(match_view, 23);break;
                                case 25:setNumMatches(match_view, 24);break;
                            }
                        }
                    });
                }
            });
        }

        if(dice_up != null){
            dice_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DICE_BID+=1;
                    if (DICE_BID == 7) DICE_BID = 1;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            switch (DICE_BID) {
                                case 1:setDiceBid(dice_view, 0);break;
                                case 2:setDiceBid(dice_view, 1);break;
                                case 3:setDiceBid(dice_view, 2);break;
                                case 4:setDiceBid(dice_view, 3);break;
                                case 5:setDiceBid(dice_view, 4);break;
                                case 6:setDiceBid(dice_view, 5);break;
                            }
                        }
                    });
                }
            });
        }

        if(dice_down != null){
            dice_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DICE_BID-=1;
                    if (DICE_BID == 0) DICE_BID = 6;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            switch (DICE_BID) {
                                case 1:setDiceBid(dice_view, 0);break;
                                case 2:setDiceBid(dice_view, 1);break;
                                case 3:setDiceBid(dice_view, 2);break;
                                case 4:setDiceBid(dice_view, 3);break;
                                case 5:setDiceBid(dice_view, 4);break;
                                case 6:setDiceBid(dice_view, 5);break;
                            }
                        }
                    });
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


