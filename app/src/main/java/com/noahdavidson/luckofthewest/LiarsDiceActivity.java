package com.noahdavidson.luckofthewest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by noahdavidson on 7/12/16.
 */


public class LiarsDiceActivity extends AppCompatActivity{

    private int [] totalDiceHands = new int[6];
    private int totalNumOfDice;

    private int []player1Dice = new int[]{0,0,0,0,0,0};
    private int player1NumOfDice;
    private int []player2Dice = new int[]{0,0,0,0,0,0};
    private int player2NumOfDice;
    private int []player3Dice = new int[]{0,0,0,0,0,0};
    private int player3NumOfDice;
    private int []player4Dice = new int[]{0,0,0,0,0,0};
    private int player4NumOfDice;
    private int []player5Dice = new int[]{0,0,0,0,0,0};
    private int player5NumOfDice;

    private int lastCall;
    private int lastDice;
    private boolean firstTurn = true;


    private static final String TAG = LiarsDiceActivity.class.getSimpleName();

    int[] matches_img = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,
            R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten,
            R.drawable.eleven,R.drawable.twelve,R.drawable.thirteen,R.drawable.fourteen,R.drawable.fifteen,
            R.drawable.sixteen,R.drawable.seventeen,R.drawable.eightteen,R.drawable.nineteen,R.drawable.twenty,
            R.drawable.twentyone,R.drawable.twentytwo,R.drawable.twentythree,R.drawable.twentyfour,R.drawable.twentyfive};

    int[] dice_img = new int[]{R.drawable.dice_one,R.drawable.dice_two,R.drawable.dice_three,R.drawable.dice_four,R.drawable.dice_five,
            R.drawable.dice_six};

    private int MATCH_BID;
    private int DICE_BID;

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

        //Set Match and Dice Image
        setMATCH_BID(1);
        setDICE_BID(1);
        setNumMatches(match_view, 0);
        setDiceBid(dice_view, 0);

        //SET ALL BUTTONS FOR LIARS DICE
        setButtons(match_up,match_down,dice_up,dice_down,call,liar,match_view, dice_view);

        //Set all players w/ 5 Dice
        resetNumPlayersDice();

        //Generate all player hands
        resetHands();

        //set total Dice variable
        //setTotalDiceHands();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setButtons(ImageButton match_up, ImageButton match_down,ImageButton dice_up,ImageButton dice_down,
                           Button call, Button liar, final ImageView match_view, final ImageView dice_view) {
        ////START BUTTON FUCNTIONALITY////
        if(call != null){
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MATCH_BID < getLastCall () || (MATCH_BID == getLastCall() && DICE_BID <= getLastDice())){
                        Context context = getApplicationContext();
                        CharSequence text = "Bet Higher";
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(context, text,duration).show();
                    }
                    setLastCall(MATCH_BID);
                    setLastDice(DICE_BID);
                    //Log.d(TAG, "call");
                }
            });

        }

        if(liar !=null){
            liar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFirstTurn() == true){
                    }else{
                        //if(tP.getLastCall() >= )
                    }
                    //Log.d(TAG, "liar");
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
                    //Log.d(TAG, ""+MATCH_BID);
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
                    //Log.d(TAG, ""+MATCH_BID);
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
                    //Log.d(TAG, ""+DICE_BID);
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
                    //Log.d(TAG, ""+DICE_BID);
                }
            });
        }
        /////END BUTTONS////////
    }

    ///// START LIARS DICE GAMING FUCNTIONS///////

    public void resetNumPlayersDice(){
        player1NumOfDice = 5;
        player2NumOfDice = 5;
        player3NumOfDice = 5;
        player4NumOfDice = 5;
        player5NumOfDice = 5;

        setTotalNumOfDice(25);
    }


    public void setMATCH_BID(int MATCH_BID) {
        this.MATCH_BID = MATCH_BID;
    }

    public int getMATCH_BID() {
        return MATCH_BID;
    }

    public void setDICE_BID(int DICE_BID) {
        this.DICE_BID = DICE_BID;
    }

    public int getDICE_BID() {
        return DICE_BID;
    }

    public void setNumMatches(ImageView view, int num){
        view.setImageResource(matches_img[num]);
    }

    public void setDiceBid(ImageView view, int num){
        view.setImageResource(dice_img[num]);
    }

    public int[] setTotalDiceHands (){
        int[] p1 = getPlayer1Dice();
        int[] p2 = getPlayer2Dice();
        int[] p3 = getPlayer3Dice();
        int[] p4 = getPlayer4Dice();
        int[] p5 = getPlayer5Dice();

        //Clear totalDiceHands value
        for(int i=0;i<6;i++){
            totalDiceHands[i]=0;
        }
        //Calculate Total Dice
        for(int i = 0; i < 6; i++){
            totalDiceHands[i] = p1[i] + p2[i] + p3[i] + p4[i] +p5[i];
        }
        return totalDiceHands;
    }

    public void resetHands(){
        //5 Players - 5 Dice - reset all hands to 0
        for(int i =0; i < 6;i++){
            player1Dice[i]=0;
            player2Dice[i]=0;
            player3Dice[i]=0;
            player4Dice[i]=0;
            player5Dice[i]=0;
        }

        //Generate all players hands
        if(getPlayer1NumOfDice() > 0){
            genHand(player1Dice,getPlayer1NumOfDice());
            /*
            for(int i=0;i<player1Dice.length;i++){
                Log.d(TAG,""+player1Dice[i]+",");
            }
            */
        }
        if(getPlayer2NumOfDice() > 0){
            genHand(player2Dice,getPlayer2NumOfDice());
            /*
            for(int i=0;i<player2Dice.length;i++){
                Log.d(TAG,""+player2Dice[i]+",");
            }
            */
        }
        if(getPlayer3NumOfDice() > 0){
            genHand(player3Dice,getPlayer3NumOfDice());
            /*
            for(int i=0;i<player3Dice.length;i++){
                Log.d(TAG,""+player3Dice[i]+",");
            }
            */
        }
        if(getPlayer4NumOfDice() > 0){
            genHand(player4Dice,getPlayer4NumOfDice());
            /*
            for(int i=0;i<player4Dice.length;i++){
                Log.d(TAG,""+player4Dice[i]+",");
            }
            */
        }
        if(getPlayer5NumOfDice() > 0){
            genHand(player5Dice,getPlayer5NumOfDice());
            /*
            for(int i=0;i<player5Dice.length;i++){
                Log.d(TAG,""+player5Dice[i]+",");
            }
            */
        }
    }

    public void genHand(int []hand, int totalDice){
        //generate hands based on # of dice left in hand
        for(int i=0; i < totalDice; i++){
            double random = Math.floor(((Math.random() *6) + 1));
            hand[(int)random - 1] += 1;
        }

    }

    public int[] gettotalDiceHands() {
        return totalDiceHands;
    }

    public int getTotalNumOfDice() {
        return totalNumOfDice;
    }

    public void setTotalNumOfDice(int totalNumOfDice) {
        this.totalNumOfDice = totalNumOfDice;
    }

    public int getLastCall() {
        return lastCall;
    }

    public void setLastCall(int lastCall) {
        this.lastCall = lastCall;
    }

    public int getLastDice() {
        return lastDice;
    }

    public void setLastDice(int lastDice) {
        this.lastDice = lastDice;
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        firstTurn = firstTurn;
    }
    //////END LIARS DICE FUNCTIONS////////


    ////////PLAYER FUCNTIONS///////
    //PLAYER 1 DATA FUNCTIONS
    public int[] getPlayer1Dice() {return player1Dice;}

    public int getPlayer1NumOfDice() {
        return player1NumOfDice;
    }

    public void setPlayer1NumOfDice(int player1NumOfDice) {
        this.player1NumOfDice = player1NumOfDice;
    }
    //END PLAYER 1 FUNCTIONS


    //PLAYER 2 DATA FUNCTIONS
    public int[] getPlayer2Dice() {return player2Dice;}

    public int getPlayer2NumOfDice() {
        return player2NumOfDice;
    }

    public void setPlayer2NumOfDice(int player2NumOfDice) {
        this.player2NumOfDice = player2NumOfDice;
    }
    //END PLAYER 2 FUNCTIONS


    //PLAYER 3 DATA FUNCTIONS
    public int[] getPlayer3Dice() {return player3Dice;}

    public int getPlayer3NumOfDice() {
        return player3NumOfDice;
    }

    public void setPlayer3NumOfDice(int player3NumOfDice) {
        this.player3NumOfDice = player3NumOfDice;
    }
    //END PLAYER 3 FUNCTIONS

    //PLAYER 4 DATA FUNCTIONS
    public int[] getPlayer4Dice() {return player4Dice;}

    public int getPlayer4NumOfDice() {
        return player4NumOfDice;
    }

    public void setPlayer4NumOfDice(int player4NumOfDice) {
        this.player4NumOfDice = player4NumOfDice;
    }
    //END PLAYER 4 FUNCTIONS

    //PLAYER 5 DATA FUNCTIONS
    public int[] getPlayer5Dice() {return player5Dice;}

    public int getPlayer5NumOfDice() {
        return player5NumOfDice;
    }

    public void setPlayer5NumOfDice(int player5NumOfDice) {
        this.player5NumOfDice = player5NumOfDice;
    }
    //END PLAYER 5 FUCNTIONS
    ///////END PLAYER FUNCTIONS/////
}


