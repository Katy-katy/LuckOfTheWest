package com.noahdavidson.luckofthewest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by noahdavidson on 7/9/16.
 */
public class SchoolActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Button button12;

    private Button[] buttons = { button1, button2, button3, button4, button5, button6, button7, button8, button9, button10,
            button11, button12 };

    private int[] card = {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6};
    private boolean[] result = {false, false, false, false, false, false, false, false, false, false, false, false};
    private boolean done = false;
    private boolean haveOpenCard = false;
    private int openCard1 = 100;
    private int openCard2 = 200;

    private TextView timerValue;
    private long startTime = 30000L;
    private Handler customHandler = new Handler();

    //long timeInMilliseconds = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 30000L;
    long updatedTime = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        //GET CUSTOM FONT
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Romantiques.ttf");
        //SET TITLE FONT
        TextView tx = (TextView)findViewById(R.id.welcomToSchool);
        tx.setTypeface(custom_font);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);

        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        buttons[4] = button5;
        buttons[5] = button6;
        buttons[6] = button7;
        buttons[7] = button8;
        buttons[8] = button9;
        buttons[9] = button10;
        buttons[10] = button11;
        buttons[11] = button12;

        for (Button but : buttons) {
            but.setBackgroundResource(R.drawable.card_back);
        }

        timerValue = (TextView) findViewById(R.id.timer);
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

        //set a list of pictures pair at random
        Random rnd = new Random();
        for (int i = card.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = card[index];
            card[index] = card[i];
            card[i] = a;
        }

        for (Button but : buttons){
            but.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    if (haveOpenCard == false){
                        haveOpenCard = true;
                        openCard1 = idToIndex(v.getId());
                        String picture = "card" + card[openCard1];
                        int id = getResources().getIdentifier(picture, "drawable", getPackageName());
                        v.setBackgroundResource(id);
                    }
                    else{
                        openCard2 = idToIndex(v.getId());
                        String picture = "card" + card[openCard2];
                        int id = getResources().getIdentifier(picture, "drawable", getPackageName());
                        v.setBackgroundResource(id);

                        if (card[openCard1] == card[openCard2]){
                            buttons[openCard2].postDelayed(new Runnable() { public void run() { buttons[openCard2].setVisibility(View.INVISIBLE); } }, 500);
                            buttons[openCard1].postDelayed(new Runnable() { public void run() { buttons[openCard1].setVisibility(View.INVISIBLE); } }, 500);
                            haveOpenCard = false;
                            result[openCard1] = true;
                            result[openCard2] = true;
                            //to check if we are done
                            if (gameOver()) {
                                //stop timer
                                timeSwapBuff += timeInMilliseconds;
                                customHandler.removeCallbacks(updateTimerThread);

                                //show pop-up view with score and options
                                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                                View popupView = layoutInflater.inflate(R.layout.popup, null);
                                final PopupWindow popupWindow = new PopupWindow(
                                        popupView,
                                        ActionBar.LayoutParams.WRAP_CONTENT,
                                        ActionBar.LayoutParams.WRAP_CONTENT);
                                Button playAgain = (Button)popupView.findViewById(R.id.playAgain);
                                Button goBack = (Button)popupView.findViewById(R.id.goBack);
                                TextView sc = (TextView)popupView.findViewById(R.id.sc);
                                sc.setText("$"+ updatedTime);
                                sc.setTextSize(28);

                                goBack.setOnClickListener(new Button.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(),GameBoardActivity.class);
                                        startActivity(intent);

                                    }});

                                playAgain.setOnClickListener(new Button.OnClickListener(){

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(SchoolActivity.this,SchoolActivity.class);
                                        startActivity(intent);
                                    }});
                                popupWindow.showAtLocation(button5, Gravity.CENTER, 0, 0);
                            }
                        }
                        else {
                            buttons[openCard2].postDelayed(new Runnable() {
                                public void run() {
                                    buttons[openCard2].setBackgroundResource(R.drawable.card_back);
                                }
                            }, 500);
                        }
                    }
                }
            });
        }
    }

    private int idToIndex(int c_id){
        int index;
        if(c_id == R.id.button1) index = 0;
        else if (c_id == R.id.button2) index = 1;
        else if (c_id == R.id.button3) index = 2;
        else if (c_id == R.id.button4) index = 3;
        else if (c_id == R.id.button5) index = 4;
        else if (c_id == R.id.button6) index = 5;
        else if (c_id == R.id.button7) index = 6;
        else if (c_id == R.id.button8) index = 7;
        else if (c_id == R.id.button9) index = 8;
        else if (c_id == R.id.button10) index = 9;
        else if (c_id == R.id.button11) index = 10;
        else  index = 11;

        return index;
    }

    private boolean gameOver(){
        boolean res = true;
        for (boolean c : result){
            if (c == false){
                res = false;
                break;
            }
        }
        return res;
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff - timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };
}
