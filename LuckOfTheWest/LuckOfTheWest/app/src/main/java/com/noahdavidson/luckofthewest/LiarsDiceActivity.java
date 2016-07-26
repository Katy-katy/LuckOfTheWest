package com.noahdavidson.luckofthewest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by noahdavidson on 7/12/16.
 */


public class LiarsDiceActivity extends AppCompatActivity{

    private static final String TAG = LiarsDiceActivity.class.getSimpleName();

    private MediaPlayer cup_sound;
    private MediaPlayer dice_sound;

    int[] matches_img = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,
            R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten,
            R.drawable.eleven,R.drawable.twelve,R.drawable.thirteen,R.drawable.fourteen,R.drawable.fifteen,
            R.drawable.sixteen,R.drawable.seventeen,R.drawable.eightteen,R.drawable.nineteen,R.drawable.twenty,
            R.drawable.twentyone,R.drawable.twentytwo,R.drawable.twentythree,R.drawable.twentyfour,R.drawable.twentyfive,
            R.drawable.liar};

    int[] dice_img = new int[]{R.drawable.dice_one,R.drawable.dice_two,R.drawable.dice_three,R.drawable.dice_four,R.drawable.dice_five,
            R.drawable.dice_six};

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

    ImageView[] AI_SHOW_CALL;

    ImageButton[] PLAYER_UI_SET_BID;

    Button[] PLAYER_UI_MAIN_ACTIONS;

    ImageView[] PLAYER_SHOW_CALL;

    ListView[] PLAYER_VIEW;

    ArrayList<Integer> inputQueue;

    private int MATCH_BID;
    private int DICE_BID;

    String end_State = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liars_dice);

        dice_sound = MediaPlayer.create(LiarsDiceActivity.this,R.raw.dice_credit_mrauralization);
        cup_sound = MediaPlayer.create(LiarsDiceActivity.this,R.raw.cup_credit_atlaslives);

        AI_SHOW_CALL = new ImageView[8];
        AI_SHOW_CALL[0] = (ImageView) findViewById(R.id.P2Match);
        AI_SHOW_CALL[1] = (ImageView) findViewById(R.id.P2Dice);
        AI_SHOW_CALL[2] = (ImageView) findViewById(R.id.P3Match);
        AI_SHOW_CALL[3] = (ImageView) findViewById(R.id.P3Dice);
        AI_SHOW_CALL[4] = (ImageView) findViewById(R.id.P4Match);
        AI_SHOW_CALL[5] = (ImageView) findViewById(R.id.P4Dice);
        AI_SHOW_CALL[6] = (ImageView) findViewById(R.id.P5Match);
        AI_SHOW_CALL[7] = (ImageView) findViewById(R.id.P5Dice);


        PLAYER_UI_SET_BID = new ImageButton[4];
        PLAYER_UI_SET_BID[0] = (ImageButton) findViewById(R.id.match_up);
        PLAYER_UI_SET_BID[1] = (ImageButton) findViewById(R.id.match_down);
        PLAYER_UI_SET_BID[2] = (ImageButton) findViewById(R.id.dice_up);
        PLAYER_UI_SET_BID[3] = (ImageButton) findViewById(R.id.dice_down);

        PLAYER_UI_MAIN_ACTIONS = new Button[3];
        PLAYER_UI_MAIN_ACTIONS[0] = (Button) findViewById(R.id.call_button);
        PLAYER_UI_MAIN_ACTIONS[1] = (Button) findViewById(R.id.liar_button);
        PLAYER_UI_MAIN_ACTIONS[2] = (Button) findViewById(R.id.show_dice_button);

        PLAYER_SHOW_CALL = new ImageView[2];
        PLAYER_SHOW_CALL[0] = (ImageView) findViewById(R.id.numbers);
        PLAYER_SHOW_CALL[1] = (ImageView) findViewById(R.id.dice);

        PLAYER_VIEW = new ListView[1];
        PLAYER_VIEW[0] = (ListView) findViewById(R.id.listView);

        inputQueue = new ArrayList<Integer>();

        //Set button vals to 1
        setMATCH_BID(1);
        setDICE_BID(1);
        //Set Match and Dice Image
        setNumMatches(PLAYER_SHOW_CALL[0], 0);
        setDiceBid(PLAYER_SHOW_CALL[1], 0);

        //set player to USER
        setPlayer(0);

        //SET ALL BUTTONS FOR LIARS DICE
        setButtons();

        //Set all players w/ 5 Dice
        resetNumPlayersDice();

        //Generate all player hands
        resetHands();

        //set global total Dice - all player's hands
        setTotalDiceHands();

        //start game
        startTurns();

        //showPopUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    public void enableButtons(){
        Log.d(TAG,"Enable UI");
        runOnUiThread(new Runnable() {
            public void run() {
                for (int i = 0; i < PLAYER_UI_SET_BID.length; i++) {
                    PLAYER_UI_SET_BID[i].setEnabled(true);
                }
                for (int i = 0; i < PLAYER_UI_MAIN_ACTIONS.length; i++) {
                    PLAYER_UI_MAIN_ACTIONS[i].setEnabled(true);
                }
            }
        });
    }

    public void disableButtons(){
        Log.d(TAG,"Disable UI");
        runOnUiThread(new Runnable() {
            public void run() {
                for(int i = 0; i < PLAYER_UI_SET_BID.length;i++){
                    PLAYER_UI_SET_BID[i].setEnabled(false);
                }
                for(int i = 0; i < PLAYER_UI_MAIN_ACTIONS.length;i++){
                    PLAYER_UI_MAIN_ACTIONS[i].setEnabled(false);
                }
            }
        });
    }

    //CHECKS FOR NEXT PLAYER'S TURN
    public int nextPlayerUp (int player){
        int nextPlayer = player + 1;
        while (nextPlayer < 5 && allPlayersNumDice[nextPlayer] == 0){
            nextPlayer++;
        }
        if(nextPlayer >= 5){
            nextPlayer = 0;
        }

        return nextPlayer;
    }

    //PAUSE THE GAME FOR ms
    private void waitForMs (int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clearAiHands (){
        runOnUiThread(new Runnable() {
            public void run() {
                    AI_SHOW_CALL[0].setImageResource(android.R.color.transparent);
                    AI_SHOW_CALL[1].setImageResource(android.R.color.transparent);

                    AI_SHOW_CALL[2].setImageResource(android.R.color.transparent);
                    AI_SHOW_CALL[3].setImageResource(android.R.color.transparent);

                    AI_SHOW_CALL[4].setImageResource(android.R.color.transparent);
                    AI_SHOW_CALL[5].setImageResource(android.R.color.transparent);

                    AI_SHOW_CALL[6].setImageResource(android.R.color.transparent);
                    AI_SHOW_CALL[7].setImageResource(android.R.color.transparent);
            }
        });
    }

    public void showAiLiar(){
        runOnUiThread(new Runnable() {
            public void run() {
                if (player == 1) {
                    Log.d(TAG, "AI 1 Liar");
                    AI_SHOW_CALL[0].setImageResource(matches_img[25]);
                    AI_SHOW_CALL[1].setImageResource(android.R.color.transparent);
                } else if (player == 2) {
                    Log.d(TAG, "AI 2 Liar");
                    AI_SHOW_CALL[2].setImageResource(matches_img[25]);
                    AI_SHOW_CALL[3].setImageResource(android.R.color.transparent);
                } else if (player == 3) {
                    Log.d(TAG, "AI 3 Liar");
                    AI_SHOW_CALL[4].setImageResource(matches_img[25]);
                    AI_SHOW_CALL[5].setImageResource(android.R.color.transparent);
                } else if (player == 4) {
                    Log.d(TAG, "AI 4 Liar");
                    AI_SHOW_CALL[6].setImageResource(matches_img[25]);
                    AI_SHOW_CALL[7].setImageResource(android.R.color.transparent);
                }
            }
        });
    }

    //DISPLAY LAST AI CALL
    public void showLastAiCall (){
        runOnUiThread(new Runnable() {
            public void run() {
                if (player == 1) {
                    Log.d(TAG,"show AI 1 CAll");
                    AI_SHOW_CALL[0].setImageResource(matches_img[getLastCall() - 1]);
                    AI_SHOW_CALL[1].setImageResource(dice_img[getLastDice() - 1]);
                } else if (player == 2) {
                    Log.d(TAG,"show AI 2 CAll");
                    AI_SHOW_CALL[2].setImageResource(matches_img[getLastCall() - 1]);
                    AI_SHOW_CALL[3].setImageResource(dice_img[getLastDice() - 1]);
                } else if (player == 3) {
                    Log.d(TAG,"show AI 3 CAll");
                    AI_SHOW_CALL[4].setImageResource(matches_img[getLastCall() - 1]);
                    AI_SHOW_CALL[5].setImageResource(dice_img[getLastDice() - 1]);
                } else if (player == 4) {
                    Log.d(TAG,"show AI 4 CAll");
                    AI_SHOW_CALL[6].setImageResource(matches_img[getLastCall() - 1]);
                    AI_SHOW_CALL[7].setImageResource(dice_img[getLastDice() - 1]);
                }
            }
        });

    }

    public void aiCall (){
        Log.d(TAG,"Ai call");

        int option  = (int)Math.floor(((Math.random() *10) + 1));

        int first = 0;

        for(int i = 0; i < 5;i++){
            if(allPlayersDice[player][i]>allPlayersDice[player][first])
                first = i;
        }

        int second =0;
        int cheat =0;

        for(int j =0; j < 5; j++){
            if(allPlayersDice[player][j] > allPlayersDice[player][second] && j != first)
                second = j;
            if(totalDiceHands_with_wilds[j]>totalDiceHands_with_wilds[cheat])
                cheat = j;
        }

        int random  = (int)Math.floor(((Math.random() *3) + 1));

        if(lastPlayer == -1){
            clearAiHands();
            waitForMs(500);
            if(totalNumOfDice >= 20){
                lastCall = 3;
                lastDice = second + 1;
            }else if(totalNumOfDice >=10 && totalNumOfDice < 20){
                switch (random){
                    case 1: lastCall = 2; break;
                    case 2: lastCall = 3; break;
                    case 3: lastCall = 4; break;
                }
                lastDice = second + 1;

            }else if (totalNumOfDice > 5 && totalNumOfDice < 10){
                switch (random){
                    case 1: lastCall = 1; break;
                    default:lastCall =2;
                }
                lastDice = first + 1;
            }else{
                lastCall = 1;
                lastDice = cheat + 1;
            }
        }else if(totalNumOfDice > 5){
            switch (option){
                case 1:
                case 2:
                case 3:
                case 4:
                    choose(first); break;
                case 5:
                case 6:
                    choose(second); break;
                case 7:
                case 8:
                    cheat(cheat); break;
                default:
                    lastCall = lastCall +1;
            }
        }else{
            switch (option){
                case 1:
                case 2:
                    choose(first); break;
                case 3:
                case 4:
                case 5:
                    cheat(cheat); break;
                default:
                    if(lastDice == 6){
                        choose(cheat);
                    }else{
                        lastDice += 1;
                    }
            }
        }
    }

    public void choose(int choice){
        if(totalNumOfDice >= 20){
            if(lastCall > totalNumOfDice/2 || (lastCall > 6 && allPlayersDice[player][lastDice-1] < 3)){
                liarCalled = true;
            }else{
                makeCall(choice);
            }

        }else if(totalNumOfDice >= 10){
            if((lastCall > 7 && totalNumOfDice < 15) || (lastCall > totalNumOfDice/2 && allPlayersDice[lastPlayer][lastDice-1]<2
                    && totalNumOfDice >= 15)){
                liarCalled = true;
            }else if(allPlayersDice[lastPlayer][lastDice-1] + allPlayersDice[player][lastDice-1] < lastCall/2
                    && lastCall > 5){
                liarCalled = true;
            }else{
                makeCall(choice);
            }
        }else if(totalNumOfDice > 5){
            if(lastCall > 5){
                liarCalled = true;
            }else if(allPlayersDice[lastPlayer][lastDice - 1] < 3 && lastCall > 3){
                liarCalled = true;
            }else{
                makeCall(choice);
            }
        }else{
            if(lastCall >= 3){
                liarCalled = true;
            }else if (allPlayersDice[lastPlayer][lastDice-1] < 1){
                liarCalled = true;
            }else{
                makeCall(choice);
            }
        }
    }

    public void cheat(int cheat){
       if (lastCall > totalDiceHands_with_wilds[lastDice - 1]){
           liarCalled = true;
       }else{
           makeCall(cheat);
       }
    }

    public void makeCall(int choice){
        if(lastDice - 1 < choice){
        }else{
            lastCall += 1;
        }
        lastDice = choice + 1;
    }

    public void liar (int[]totalDiceHands_with_wilds, int []allPlayersNumDice){
        Log.d(TAG,"liar");
        //show "player calls "lastplayer"
        //show lastcall, lasxtdice
        // show there are totaldicehands[lastDice -1],lastdice
        waitForMs(500);
        if(getLastCall() > totalDiceHands_with_wilds[getLastDice()-1]){
            allPlayersNumDice[lastPlayer]-=1;
            if(allPlayersNumDice[getLastPlayer()] != 0){
                setPlayer(getLastPlayer());
            }else{
                setPlayer(getPlayer());
            }
            runOnUiThread(new Runnable () {
                public void run() {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "PLAYER " + lastPlayer + " LOST 1 DICE", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            });
            waitForMs(500);

        }else if(getLastCall() <= totalDiceHands_with_wilds[getLastDice()-1]){
            allPlayersNumDice[getPlayer()]-=1;
            runOnUiThread(new Runnable () {
                public void run() {
                    if(player == 0){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "YOU LOST 1 DICE", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                    }else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "PLAYER " + player + "LOST 1 DICE", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                }
            });
            waitForMs(500);
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

        //Check if player loses
        checkLoser();

        //check if player wins
        checkWinner();

        clearAiHands();
        resetHands();
    }

    public void checkWinner(){
        if(allPlayersNumDice[1] == 0 &&
                allPlayersNumDice[2] == 0 &&
                    allPlayersNumDice[3] == 0 &&
                        allPlayersNumDice[4] == 0){
            winner = true;
            GameBoardActivity.user_player.setWinnings(GameBoardActivity.user_player.getWager() * 4);
            //showPopUp();

        }


    }

    public void checkLoser(){
        if(allPlayersNumDice[0] == 0){
            setLoser(true);
            GameBoardActivity.user_player.setWinnings(0);
            //showPopUp();
            //setPlayer(0);
            //setTotalNumOfDice(25);
        }
    }

    private void showPopUp() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        //Button playAgain = (Button)popupView.findViewById(R.id.playAgain);
        Button goBack = (Button)popupView.findViewById(R.id.goBack);
        final TextView sc = (TextView)popupView.findViewById(R.id.sc);
        runOnUiThread(new Runnable () {
            public void run() {
                sc.setText("$" + GameBoardActivity.user_player.getWinnings());
                sc.setTextSize(28);
                sc.setTextColor(Color.GREEN);
            }
        });
        GameBoardActivity.user_player.addGold( GameBoardActivity.user_player.getWinnings());

        goBack.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiarsDiceActivity.this ,GameBoardActivity.class);
                startActivity(intent);

            }});

        runOnUiThread(new Runnable () {
            public void run() {
                popupWindow.showAtLocation(AI_SHOW_CALL[2], Gravity.CENTER, 0, 0);
            }
        });
    }

    public void turn(){
        Log.d(TAG,"Next Player: " + player + " turn");

        if(getPlayer() == 0){
            Log.d(TAG,"user turn");
            Log.d(TAG,"Last player turn: " + lastPlayer);
            showUI();
            enableButtons();
            if(lastPlayer == -1){
                clearAiHands();
                waitForMs(500);
            }

            //WAIT FOR PLAYER INPUT//
            while(true){
                if(inputQueue != null)
                    if (inputQueue.isEmpty()) continue;

                if(inputQueue != null && !inputQueue.isEmpty()){
                    inputQueue.remove(0);
                    break;
                }
            }

        }else{
            Log.d(TAG,"AI turn: " + player);

            hideUI();
            disableButtons();
            setLiarCalled(false);
            aiCall();
            Log.d(TAG,"AI " + player + " Called Liar: " + liarCalled);
            if(!isLiarCalled()){
                showLastAiCall();
                waitForMs(500);
                lastPlayer = player;
                player = nextPlayerUp(player);
            }else{
                showAiLiar();
                waitForMs(500);
            }
        }
    }

    public void startTurns (){
        Log.d(TAG,"START TURNS");
        Runnable runnable = new Runnable() {
            public void run() {
                //Start Turns
                while (winner != true && loser != true) {
                    waitForMs(1000);
                    turn();

                    if (liarCalled) {
                                liar(totalDiceHands_with_wilds, allPlayersNumDice);
                    }
                    Log.d(TAG, "Winner: " + winner + ", Loser: " + loser);
                    waitForMs(500);
                }
                showPopUp();
            }
        };

        Thread myThread = new Thread (runnable);
        myThread.start();

    }

    public void turnButPressed(int buttonNumber){
        inputQueue.add(buttonNumber);
    }

    public void showUI(){
        Log.d(TAG,"Show UI");
        runOnUiThread(new Runnable() {
            public void run() {
                PLAYER_UI_SET_BID[0].setVisibility(View.VISIBLE);
                PLAYER_UI_SET_BID[1].setVisibility(View.VISIBLE);
                PLAYER_UI_SET_BID[2].setVisibility(View.VISIBLE);
                PLAYER_UI_SET_BID[3].setVisibility(View.VISIBLE);

                PLAYER_UI_MAIN_ACTIONS[0].setVisibility(View.VISIBLE);
                PLAYER_UI_MAIN_ACTIONS[1].setVisibility(View.VISIBLE);
                PLAYER_UI_MAIN_ACTIONS[2].setVisibility(View.VISIBLE);

                PLAYER_SHOW_CALL[0].setVisibility(View.VISIBLE);
                PLAYER_SHOW_CALL[1].setVisibility(View.VISIBLE);

                PLAYER_VIEW[0].setVisibility(View.VISIBLE);
            }
        });

    }


    public void hideUI(){
        Log.d(TAG,"Hide UI");
        runOnUiThread(new Runnable() {
            public void run() {

                PLAYER_UI_SET_BID[0].setVisibility(View.INVISIBLE);
                PLAYER_UI_SET_BID[1].setVisibility(View.INVISIBLE);
                PLAYER_UI_SET_BID[2].setVisibility(View.INVISIBLE);
                PLAYER_UI_SET_BID[3].setVisibility(View.INVISIBLE);

                PLAYER_UI_MAIN_ACTIONS[0].setVisibility(View.INVISIBLE);
                PLAYER_UI_MAIN_ACTIONS[1].setVisibility(View.INVISIBLE);
                PLAYER_UI_MAIN_ACTIONS[2].setVisibility(View.INVISIBLE);

                PLAYER_SHOW_CALL[0].setVisibility(View.INVISIBLE);
                PLAYER_SHOW_CALL[1].setVisibility(View.INVISIBLE);

                PLAYER_VIEW[0].setVisibility(View.INVISIBLE);
            }
        });
    }

    public void setButtons() {
        ////START BUTTON FUCNTIONALITY////
        if(PLAYER_UI_MAIN_ACTIONS[0] != null){
            PLAYER_UI_MAIN_ACTIONS[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MATCH_BID < getLastCall () || (MATCH_BID == getLastCall() && DICE_BID <= getLastDice())){
                        Context context = getApplicationContext();
                        CharSequence text = "Bet Higher";
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(context, text,duration).show();
                    }else {
                        setLastCall(MATCH_BID);
                        setLastDice(DICE_BID);

                        setLastPlayer(0);
                        player = nextPlayerUp(player);
                        setLiarCalled(false);

                        turnButPressed(0);
                    }
                    Log.d(TAG,"Call: "+lastCall +", Dice: " + lastDice);

                    //int nextPlayer = nextPlayerUp();
                    //setPlayer(nextPlayer);
                    //hideUI();

                    //startTurns();
                    //Log.d(TAG, "call");
                }
            });

        }

        if(PLAYER_UI_MAIN_ACTIONS[1] !=null){
            PLAYER_UI_MAIN_ACTIONS[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getLastPlayer() == -1){
                    }else{
                        setLiarCalled(true);
                        turnButPressed(1);
                    }
                    //Log.d(TAG, "liar");
                }
            });

        }

        if(PLAYER_UI_SET_BID[0] != null) {
            PLAYER_UI_SET_BID[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MATCH_BID += 1;
                    if (MATCH_BID == 26) MATCH_BID = 1;

                    runOnUiThread(new Runnable() {
                        public void run() {
                            switch (MATCH_BID){
                                case 1: setNumMatches(PLAYER_SHOW_CALL[0],0); break;
                                case 2: setNumMatches(PLAYER_SHOW_CALL[0],1); break;
                                case 3: setNumMatches(PLAYER_SHOW_CALL[0],2); break;
                                case 4: setNumMatches(PLAYER_SHOW_CALL[0],3); break;
                                case 5: setNumMatches(PLAYER_SHOW_CALL[0],4); break;
                                case 6: setNumMatches(PLAYER_SHOW_CALL[0],5); break;
                                case 7: setNumMatches(PLAYER_SHOW_CALL[0],6); break;
                                case 8: setNumMatches(PLAYER_SHOW_CALL[0],7); break;
                                case 9: setNumMatches(PLAYER_SHOW_CALL[0],8); break;
                                case 10: setNumMatches(PLAYER_SHOW_CALL[0],9); break;
                                case 11: setNumMatches(PLAYER_SHOW_CALL[0],10); break;
                                case 12: setNumMatches(PLAYER_SHOW_CALL[0],11); break;
                                case 13: setNumMatches(PLAYER_SHOW_CALL[0],12); break;
                                case 14: setNumMatches(PLAYER_SHOW_CALL[0],13); break;
                                case 15: setNumMatches(PLAYER_SHOW_CALL[0],14); break;
                                case 16: setNumMatches(PLAYER_SHOW_CALL[0],15); break;
                                case 17: setNumMatches(PLAYER_SHOW_CALL[0],16); break;
                                case 18: setNumMatches(PLAYER_SHOW_CALL[0],17); break;
                                case 19: setNumMatches(PLAYER_SHOW_CALL[0],18); break;
                                case 20: setNumMatches(PLAYER_SHOW_CALL[0],19); break;
                                case 21: setNumMatches(PLAYER_SHOW_CALL[0],20); break;
                                case 22: setNumMatches(PLAYER_SHOW_CALL[0],21); break;
                                case 23: setNumMatches(PLAYER_SHOW_CALL[0],22); break;
                                case 24: setNumMatches(PLAYER_SHOW_CALL[0],23); break;
                                case 25: setNumMatches(PLAYER_SHOW_CALL[0],24); break;
                            }
                        }
                    });
                    //Log.d(TAG, ""+MATCH_BID);
                }
            });
        }

        if(PLAYER_UI_SET_BID[1] != null){
            PLAYER_UI_SET_BID[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MATCH_BID-=1;
                    if (MATCH_BID == 0) MATCH_BID = 25;

                    runOnUiThread(new Runnable() {
                        public void run() {
                            switch (MATCH_BID) {
                                case 1:setNumMatches(PLAYER_SHOW_CALL[0], 0);break;
                                case 2:setNumMatches(PLAYER_SHOW_CALL[0], 1);break;
                                case 3:setNumMatches(PLAYER_SHOW_CALL[0], 2);break;
                                case 4:setNumMatches(PLAYER_SHOW_CALL[0], 3);break;
                                case 5:setNumMatches(PLAYER_SHOW_CALL[0], 4);break;
                                case 6:setNumMatches(PLAYER_SHOW_CALL[0], 5);break;
                                case 7:setNumMatches(PLAYER_SHOW_CALL[0], 6);break;
                                case 8:setNumMatches(PLAYER_SHOW_CALL[0], 7);break;
                                case 9:setNumMatches(PLAYER_SHOW_CALL[0], 8);break;
                                case 10:setNumMatches(PLAYER_SHOW_CALL[0], 9);break;
                                case 11:setNumMatches(PLAYER_SHOW_CALL[0], 10);break;
                                case 12:setNumMatches(PLAYER_SHOW_CALL[0], 11);break;
                                case 13:setNumMatches(PLAYER_SHOW_CALL[0], 12);break;
                                case 14:setNumMatches(PLAYER_SHOW_CALL[0], 13);break;
                                case 15:setNumMatches(PLAYER_SHOW_CALL[0], 14);break;
                                case 16:setNumMatches(PLAYER_SHOW_CALL[0], 15);break;
                                case 17:setNumMatches(PLAYER_SHOW_CALL[0], 16);break;
                                case 18:setNumMatches(PLAYER_SHOW_CALL[0], 17);break;
                                case 19:setNumMatches(PLAYER_SHOW_CALL[0], 18);break;
                                case 20:setNumMatches(PLAYER_SHOW_CALL[0], 19);break;
                                case 21:setNumMatches(PLAYER_SHOW_CALL[0], 20);break;
                                case 22:setNumMatches(PLAYER_SHOW_CALL[0], 21);break;
                                case 23:setNumMatches(PLAYER_SHOW_CALL[0], 22);break;
                                case 24:setNumMatches(PLAYER_SHOW_CALL[0], 23);break;
                                case 25:setNumMatches(PLAYER_SHOW_CALL[0], 24);break;
                            }
                        }
                    });
                    //Log.d(TAG, ""+MATCH_BID);
                }
            });
        }

        if(PLAYER_UI_SET_BID[2] != null){
            PLAYER_UI_SET_BID[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DICE_BID+=1;
                    if (DICE_BID == 7) DICE_BID = 1;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            switch (DICE_BID) {
                                case 1:setDiceBid(PLAYER_SHOW_CALL[1], 0);break;
                                case 2:setDiceBid(PLAYER_SHOW_CALL[1], 1);break;
                                case 3:setDiceBid(PLAYER_SHOW_CALL[1], 2);break;
                                case 4:setDiceBid(PLAYER_SHOW_CALL[1], 3);break;
                                case 5:setDiceBid(PLAYER_SHOW_CALL[1], 4);break;
                                case 6:setDiceBid(PLAYER_SHOW_CALL[1], 5);break;
                            }
                        }
                    });
                    //Log.d(TAG, ""+DICE_BID);
                }
            });
        }

        if(PLAYER_UI_SET_BID[3] != null){
            PLAYER_UI_SET_BID[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DICE_BID-=1;
                    if (DICE_BID == 0) DICE_BID = 6;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            switch (DICE_BID) {
                                case 1:setDiceBid(PLAYER_SHOW_CALL[1], 0);break;
                                case 2:setDiceBid(PLAYER_SHOW_CALL[1], 1);break;
                                case 3:setDiceBid(PLAYER_SHOW_CALL[1], 2);break;
                                case 4:setDiceBid(PLAYER_SHOW_CALL[1], 3);break;
                                case 5:setDiceBid(PLAYER_SHOW_CALL[1], 4);break;
                                case 6:setDiceBid(PLAYER_SHOW_CALL[1], 5);break;
                            }
                        }
                    });
                    //Log.d(TAG, ""+DICE_BID);
                }
            });
        }

        if (PLAYER_UI_MAIN_ACTIONS[2] != null) {
            PLAYER_UI_MAIN_ACTIONS[2].setOnClickListener(new View.OnClickListener() {
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

                            cup_sound.start();

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

            Log.d(TAG,"P0: "+playerDice[0]+","+playerDice[1]+","+playerDice[2]+","+playerDice[3]+","+playerDice[4]+","+playerDice[5]);

        }
        if(allPlayersNumDice[1] > 0){
            genHand(player1Dice,allPlayersNumDice[1]);

            Log.d(TAG,"P1: "+player1Dice[0]+","+player1Dice[1]+","+player1Dice[2]+","+player1Dice[3]+","+player1Dice[4]+","+player1Dice[5]);
        }
        if(allPlayersNumDice[2] > 0){
            genHand(player2Dice,allPlayersNumDice[2]);

            Log.d(TAG,"P2: "+player2Dice[0]+","+player2Dice[1]+","+player2Dice[2]+","+player2Dice[3]+","+player2Dice[4]+","+player2Dice[5]);
        }
        if(allPlayersNumDice[3] > 0){
            genHand(player3Dice,allPlayersNumDice[3]);

            Log.d(TAG,"P3: "+player3Dice[0]+","+player3Dice[1]+","+player3Dice[2]+","+player3Dice[3]+","+player3Dice[4]+","+player3Dice[5]);
        }
        if(allPlayersNumDice[4] > 0){
            genHand(player4Dice,allPlayersNumDice[4]);

            Log.d(TAG,"P4: "+player4Dice[0]+","+player4Dice[1]+","+player4Dice[2]+","+player4Dice[3]+","+player4Dice[4]+","+player4Dice[5]);
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


