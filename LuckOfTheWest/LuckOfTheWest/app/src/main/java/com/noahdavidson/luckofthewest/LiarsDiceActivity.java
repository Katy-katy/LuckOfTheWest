package com.noahdavidson.luckofthewest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by noahdavidson on 7/12/16.
 */


public class LiarsDiceActivity extends AppCompatActivity{

    private int [] totalDiceHands = new int[6];
    private int [] totalDiceHands_with_wilds = new int[6];
    private int totalNumOfDice;

    private int []playerDice = new int[]{0,0,0,0,0,0};
    private int []player1Dice = new int[]{0,0,0,0,0,0};
    private int []player2Dice = new int[]{0,0,0,0,0,0};
    private int []player3Dice = new int[]{0,0,0,0,0,0};
    private int []player4Dice = new int[]{0,0,0,0,0,0};

    private int[][] allPlayersDice = new int[][]{playerDice,player1Dice,player2Dice,player3Dice,player4Dice};
    private int[] allPlayersNumDice = new int[]{0,0,0,0,0};

    private int lastCall;
    private int lastDice;
    private boolean liarCalled = false;
    private boolean winner = false;
    private boolean loser = false;
    private int player = 0;
    private int lastPlayer = -1;

    private static final String TAG = LiarsDiceActivity.class.getSimpleName();

    //PLAYER HAND DIALOG BOX

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

        final ImageView P2M = (ImageView) findViewById(R.id.P2Match);
        final ImageView P2D = (ImageView) findViewById(R.id.P2Dice);
        final ImageView P3M = (ImageView) findViewById(R.id.P3Match);
        final ImageView P3D = (ImageView) findViewById(R.id.P3Dice);
        final ImageView P4M = (ImageView) findViewById(R.id.P4Match);
        final ImageView P4D = (ImageView) findViewById(R.id.P4Dice);
        final ImageView P5M = (ImageView) findViewById(R.id.P5Match);
        final ImageView P5D = (ImageView) findViewById(R.id.P5Dice);

        final ImageButton match_up = (ImageButton) findViewById(R.id.match_up);
        final ImageButton match_down = (ImageButton) findViewById(R.id.match_down);
        final ImageButton dice_up = (ImageButton) findViewById(R.id.dice_up);
        final ImageButton dice_down = (ImageButton) findViewById(R.id.dice_down);

        final Button call = (Button) findViewById(R.id.call_button);
        final Button liar = (Button) findViewById(R.id.liar_button);
        final Button show_dice = (Button) findViewById(R.id.roll_button);

        final ListView playerUI = (ListView) findViewById(R.id.listView);

        //Set Match and Dice Image
        setMATCH_BID(1);
        setDICE_BID(1);
        setNumMatches(match_view, 0);
        setDiceBid(dice_view, 0);
        setPlayer(0);


        //SET ALL BUTTONS FOR LIARS DICE
        setButtons(match_up,match_down,dice_up,dice_down,call,liar,match_view, dice_view, show_dice,playerUI);

        //Set all players w/ 5 Dice
        resetNumPlayersDice();

        //Generate all player hands
        resetHands();

        //set total Dice variable
        setTotalDiceHands();

        /*
        //Start Turns
        while(winner != true && loser != true){
            turn(player,lastPlayer,lastCall,lastDice,totalDiceHands_with_wilds,totalNumOfDice,allPlayersDice,allPlayersNumDice,liarCalled,
                    match_up, match_down,dice_up,dice_down,call,liar,match_view, dice_view, show_dice, playerUI,P2M,P2D,P3M,P3D,P4M,P4D,P5M,P5D);

            if(liarCalled){
                liar(player,lastPlayer,lastCall,lastDice,totalDiceHands_with_wilds,totalNumOfDice,allPlayersNumDice,liarCalled);
            }
        }
        */


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showLastPlayerCall (final ImageView P2M,final ImageView P2D, final ImageView P3M,final ImageView P3D,
                                    final ImageView P4M,final ImageView P4D, final ImageView P5M,final ImageView P5D){
        Log.d(TAG,"show last player's call");
        if(getLastPlayer() == 1){
            P2M.setImageResource(matches_img[getLastCall()-1]);
            P2D.setImageResource(dice_img[getLastDice()-1]);
        }else if(getLastPlayer() == 2){
            P3M.setImageResource(matches_img[getLastCall()-1]);
            P3D.setImageResource(dice_img[getLastDice()-1]);
        }else if(getLastPlayer() == 3){
            P4M.setImageResource(matches_img[getLastCall()-1]);
            P4D.setImageResource(dice_img[getLastDice()-1]);
        }else if(getLastPlayer() == 4){
            P5M.setImageResource(matches_img[getLastCall()-1]);
            P5D.setImageResource(dice_img[getLastDice()-1]);
        }

    }

    public void aiCall (){
        Log.d(TAG,"Ai call");
        int first = 0;
        for(int i = 0; i < 5; i++){
            if(allPlayersDice[getPlayer()][i] > allPlayersDice[getPlayer()][first]){
                first = i;
            }
        }
        int second = 0;
        for(int j =0;j<5;j++){
            if(allPlayersDice[getPlayer()][j] > allPlayersDice[getPlayer()][second] && j!=first){
                second = j;
            }
        }
        int cheat = 0;
        setLastCall(2);
        setLastDice(2);
    }

    public void liar (int player, int lastPlayer, int lastCall, int lastDice, int[]totalDiceHands_with_wilds, int totalNumOfDice,
                      int []allPlayersNumDice,boolean liarCalled){
        Log.d(TAG,"liar");
        //show "player calls "lastplayer"
        //show lastcall, lasxtdice
        // show there are totaldicehands[lastDice -1],lastdice

        if(getLastCall() > totalDiceHands_with_wilds[getLastDice()-1]){
            allPlayersNumDice[getLastPlayer()]-=1;
            if(allPlayersNumDice[getLastPlayer()] != 0){
                setPlayer(getLastPlayer());
            }else{
                setPlayer(getPlayer());
            }
        }else if(getLastCall() <= totalDiceHands_with_wilds[getLastDice()-1]){
            allPlayersNumDice[getPlayer()]-=1;
            if(allPlayersNumDice[getPlayer()] != 0){
                setPlayer(getPlayer());
            }else{
                int i = getPlayer();
                while(i < 5 && allPlayersNumDice[i] == 0){
                    i++;
                }
                if(i==5){
                    setPlayer(0);
                }else{
                    setPlayer(i);
                }
            }
        }
        setLastPlayer(-1);
        setLastCall(0);
        setLastDice(0);
        setTotalNumOfDice(getTotalNumOfDice()-1);
        setLiarCalled(false);

        if(allPlayersNumDice[0] == 0){
            setLoser(true);
            //setPlayer(0);
            //setTotalNumOfDice(25);
        }
        if(allPlayersNumDice[1] == 0 &&
                allPlayersNumDice[2] == 0 &&
                allPlayersNumDice[3] == 0 &&
                allPlayersNumDice[4] == 0){
            setWinner(true);
        }
        resetHands();
    }

    public void turn(int player, int lastPlayer, int lastCall, int lastDice, int[]totalDiceHands_with_wilds,int totalNumOfDice,
                     int[][]allPlayersDice,int [] allPlayersNumDice, boolean liarCalled,final ImageButton match_up,final ImageButton match_down,final ImageButton dice_up,final ImageButton dice_down,
                     final Button call, final Button liar, final ImageView match_view, final ImageView dice_view, final Button show_dice, final ListView playerUI,final ImageView P2M,final ImageView P2D,
                     final ImageView P3M,final ImageView P3D,final ImageView P4M,final ImageView P4D, final ImageView P5M,final ImageView P5D){
        Log.d(TAG,"turn");
        if(getPlayer() == 0){
            Log.d(TAG,"players turn");
            showUI(match_up, match_down,dice_up,dice_down,call,liar,match_view,dice_view,show_dice,playerUI);
            if(getLastPlayer() != -1){
                //SHOW LASTPLAYER, LASTCALL, LASTDICE
                showLastPlayerCall(P2M,P2D,P3M,P3D,P4M,P4D,P5M,P5D);
                //Enable liar
                liar.setEnabled(true);
            }
        }else{
            Log.d(TAG,"AI turn");
            hideUI(match_up, match_down,dice_up,dice_down,call,liar,match_view,dice_view,show_dice,playerUI);
            aiCall();
            if(!isLiarCalled()){
                showLastPlayerCall(P2M,P2D,P3M,P3D,P4M,P4D,P5M,P5D);
            }
        }
    }

    public void showUI(final ImageButton match_up,final ImageButton match_down,final ImageButton dice_up,final ImageButton dice_down,
                       final Button call, final Button liar, final ImageView match_view, final ImageView dice_view, final Button show_dice, final ListView playerUI){

        match_up.setVisibility(View.VISIBLE);
        match_down.setVisibility(View.VISIBLE);
        dice_up.setVisibility(View.VISIBLE);
        dice_down.setVisibility(View.VISIBLE);
        call.setVisibility(View.VISIBLE);
        liar.setVisibility(View.VISIBLE);
        match_view.setVisibility(View.VISIBLE);
        dice_view.setVisibility(View.VISIBLE);
        show_dice.setVisibility(View.VISIBLE);
        playerUI.setVisibility(View.VISIBLE);

    }


    public void hideUI(final ImageButton match_up,final ImageButton match_down,final ImageButton dice_up,final ImageButton dice_down,
                       final Button call, final Button liar, final ImageView match_view, final ImageView dice_view, final Button show_dice, final ListView playerUI){

        match_up.setVisibility(View.GONE);
        match_down.setVisibility(View.GONE);
        dice_up.setVisibility(View.GONE);
        dice_down.setVisibility(View.GONE);
        call.setVisibility(View.GONE);
        liar.setVisibility(View.GONE);
        match_view.setVisibility(View.GONE);
        dice_view.setVisibility(View.GONE);
        show_dice.setVisibility(View.GONE);
        playerUI.setVisibility(View.GONE);

    }

    public void setButtons(final ImageButton match_up,final ImageButton match_down,final ImageButton dice_up,final ImageButton dice_down,
                           final Button call, final Button liar, final ImageView match_view, final ImageView dice_view, final Button show_dice, final ListView playerUI) {
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
                    setLastPlayer(0);
                    int i = 1;
                    while (i < 5 && allPlayersNumDice[i] == 0){
                        i++;
                    }
                    setPlayer(i);
                    hideUI(match_up,match_down,dice_up,dice_down,call,liar,match_view,dice_view,show_dice,playerUI);

                    //Log.d(TAG, "call");
                }
            });

        }

        if(liar !=null){
            liar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getLastPlayer() == -1){
                    }else{
                        setLiarCalled(true);
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

        if (show_dice != null) {
            show_dice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        public void run() {

                            //INITIALIZE SHOW HAND DIALOG BOX
                            final Dialog show_hand_box = new Dialog(LiarsDiceActivity.this);
                            show_hand_box.setTitle("Dice Hand");
                            show_hand_box.setContentView(R.layout.show_hand_layout);
                            show_hand_box.show();

                            //PLAYER HAND VIEW VARIABLES
                            ImageView dice1 = (ImageView) show_hand_box.findViewById(R.id.dice1);
                            ImageView dice2 = (ImageView) show_hand_box.findViewById(R.id.dice2);
                            ImageView dice3 = (ImageView) show_hand_box.findViewById(R.id.dice3);
                            ImageView dice4 = (ImageView) show_hand_box.findViewById(R.id.dice4);
                            ImageView dice5 = (ImageView) show_hand_box.findViewById(R.id.dice5);
                            final ImageView[] player_hand_view = new ImageView[]{dice1, dice2, dice3, dice4, dice5};

                            int dCount = 0;
                            while (dCount < allPlayersNumDice[0]) {
                                for (int i = 0; i < 6; i++) {
                                    if (playerDice[i] > 0) {
                                        for (int j = 0; j < playerDice[i]; j++) {
                                            if (player_hand_view != null) {
                                                player_hand_view[dCount].setImageResource(dice_img[i]);
                                                dCount++;
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    });
                }
            });
        }
        /////END BUTTONS////////
    }

    ///// START LIARS DICE GAMING FUCNTIONS///////


    public int[] getAllPlayersDice(int i) {
        return allPlayersDice[i];
    }

    public void resetNumPlayersDice(){
        for(int i =0;i< 5;i++){
            allPlayersNumDice[i] = 5;
        }

        setTotalNumOfDice(25);
    }

    public void setTotalNumOfDice(int totalNumOfDice) {
        this.totalNumOfDice = totalNumOfDice;
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
        int[] p1 = getplayerDice();
        int[] p2 = getplayer1Dice();
        int[] p3 = getplayer2Dice();
        int[] p4 = getplayer3Dice();
        int[] p5 = getplayer4Dice();

        //Clear totalDiceHands value
        for(int i=0;i<6;i++){
            totalDiceHands[i]=0;
        }
        //Calculate Total Dice
        for(int i = 0; i < 6; i++){
            totalDiceHands[i] = p1[i] + p2[i] + p3[i] + p4[i] +p5[i];
        }
        if(totalDiceHands[0] > 0){
            totalDiceHands_with_wilds[0] = totalDiceHands[0];
            for(int i = 1; i <6;i++){
                totalDiceHands_with_wilds[i] += (totalDiceHands[0] + totalDiceHands[i]);
            }

        }

        Log.d(TAG,"Total: "+totalDiceHands[0]+","+totalDiceHands[1]+","+totalDiceHands[2]+","+totalDiceHands[3]+","+totalDiceHands[4]+","+totalDiceHands[5]);

        Log.d(TAG,"Total with Wilds: "+totalDiceHands_with_wilds[0]+","+totalDiceHands_with_wilds[1]+","+totalDiceHands_with_wilds[2]+","+totalDiceHands_with_wilds[3]+","+totalDiceHands_with_wilds[4]+","+totalDiceHands_with_wilds[5]);
        return totalDiceHands;
    }

    public void resetHands(){
        //5 Players - 5 Dice - reset all hands to 0
        for(int i =0; i < 6;i++){
            playerDice[i]=0;
            player1Dice[i]=0;
            player2Dice[i]=0;
            player3Dice[i]=0;
            player4Dice[i]=0;
        }

        //Generate all players hands
        if(allPlayersNumDice[0] > 0){
            genHand(playerDice,allPlayersNumDice[0]);

            Log.d(TAG,"P1: "+playerDice[0]+","+playerDice[1]+","+playerDice[2]+","+playerDice[3]+","+playerDice[4]+","+playerDice[5]);

        }
        if(allPlayersNumDice[1] > 0){
            genHand(player1Dice,allPlayersNumDice[1]);

            Log.d(TAG,"P2: "+player1Dice[0]+","+player1Dice[1]+","+player1Dice[2]+","+player1Dice[3]+","+player1Dice[4]+","+player1Dice[5]);
        }
        if(allPlayersNumDice[2] > 0){
            genHand(player2Dice,allPlayersNumDice[2]);

            Log.d(TAG,"P3: "+player2Dice[0]+","+player2Dice[1]+","+player2Dice[2]+","+player2Dice[3]+","+player2Dice[4]+","+player2Dice[5]);
        }
        if(allPlayersNumDice[3] > 0){
            genHand(player3Dice,allPlayersNumDice[3]);

            Log.d(TAG,"P4: "+player3Dice[0]+","+player3Dice[1]+","+player3Dice[2]+","+player3Dice[3]+","+player3Dice[4]+","+player3Dice[5]);
        }
        if(allPlayersNumDice[4] > 0){
            genHand(player4Dice,allPlayersNumDice[4]);

            Log.d(TAG,"P5: "+player4Dice[0]+","+player4Dice[1]+","+player4Dice[2]+","+player4Dice[3]+","+player4Dice[4]+","+player4Dice[5]);
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

    public int getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(int lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setLiarCalled(boolean liarCalled) {
        this.liarCalled = liarCalled;
    }

    public boolean isLiarCalled() {
        return liarCalled;
    }

    public void setLoser(boolean loser) {
        this.loser = loser;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    //////END LIARS DICE FUNCTIONS////////


    ////////PLAYER FUCNTIONS///////
    //PLAYER 1 DATA FUNCTIONS
    public int[] getplayerDice() {return playerDice;}

    //END PLAYER 1 FUNCTIONS


    //PLAYER 2 DATA FUNCTIONS
    public int[] getplayer1Dice() {return player1Dice;}

    //END PLAYER 2 FUNCTIONS


    //PLAYER 3 DATA FUNCTIONS
    public int[] getplayer2Dice() {return player2Dice;}

    //END PLAYER 3 FUNCTIONS

    //PLAYER 4 DATA FUNCTIONS
    public int[] getplayer3Dice() {return player3Dice;}

    //END PLAYER 4 FUNCTIONS

    //PLAYER 5 DATA FUNCTIONS
    public int[] getplayer4Dice() {return player4Dice;}

    //END PLAYER 5 FUCNTIONS


    ///////END PLAYER FUNCTIONS/////
}


