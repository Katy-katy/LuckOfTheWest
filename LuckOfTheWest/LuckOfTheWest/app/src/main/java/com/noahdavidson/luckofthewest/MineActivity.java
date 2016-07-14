package com.noahdavidson.luckofthewest;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MineActivity extends AppCompatActivity {

    final int NUM_MINEBUTTONS = 6;

    Button startButton;
    Button[] mineBut;


    TextView statusText;
    TextView scoreText;

    ArrayList<Integer> answerSeq;
    ArrayList<Integer> inputQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        startButton = (Button)findViewById(R.id.startButton);
        mineBut = new Button[NUM_MINEBUTTONS];
        mineBut[0] = (Button)findViewById(R.id.mineButton1);
        mineBut[1] = (Button)findViewById(R.id.mineButton2);
        mineBut[2] = (Button)findViewById(R.id.mineButton3);
        mineBut[3] = (Button)findViewById(R.id.mineButton4);
        mineBut[4] = (Button)findViewById(R.id.mineButton5);
        mineBut[5] = (Button)findViewById(R.id.mineButton6);

        statusText = (TextView)findViewById(R.id.statusText);
        scoreText = (TextView)findViewById(R.id.scoreText);

        answerSeq = new ArrayList<Integer>();
        inputQueue = new ArrayList<Integer>();

        /* Button listeners are registered */
        startButton.setOnClickListener(ocl);
        for (int i=0; i<NUM_MINEBUTTONS; i++) {
            mineBut[i].setOnClickListener(ocl);
        }
    }


    private Button.OnClickListener ocl = new Button.OnClickListener() {
        public void onClick (View v) {
            switch(v.getId())
            {
                case R.id.startButton:
                    executeGame(v);
                    break;
                case R.id.mineButton1:
                    mineButPressed(v, 0);
                    break;
                case R.id.mineButton2:
                    mineButPressed(v, 1);
                    break;
                case R.id.mineButton3:
                    mineButPressed(v, 2);
                    break;
                case R.id.mineButton4:
                    mineButPressed(v, 3);
                    break;
                case R.id.mineButton5:
                    mineButPressed(v, 4);
                    break;
                case R.id.mineButton6:
                    mineButPressed(v, 5);
                    break;
                default:
                    /* Unknown error occurred */
                    break;
            }
        }
    };

    protected void executeGame (View v) {
        Runnable runnable = new Runnable() {
            public void run() {
                /* Initial setup */
                int score = 0;
                Random random = new Random();
                enableStartButton(false);
                setScoreText("Score: 0");
                answerSeq.clear();
                setStatusText("Go!");
                waitForMs(1500);

                /* If the player gets wrong, the while loop will end */
                while (true) {
                    setStatusText("Watch carefully.");
                    answerSeq.add(random.nextInt(NUM_MINEBUTTONS));

                    enableMineButtons(false);
                    int totalSize = answerSeq.size();
                    for (int i=0; i<totalSize; i++) {
                        blinkButton(answerSeq.get(i));
                    }
                    enableMineButtons(true);

                    /* Wait user to press series of buttons for this round. */
                    /* The result is stored in the variable userWinForRound. */
                    setStatusText("It's your turn!");
                    int index_modelAnswer = 0;
                    int modelAnswer = answerSeq.get(0);
                    boolean userWinForRound = false;
                    while (true) {
                        /* Wait until user press any button */
                        if (inputQueue.isEmpty()) continue;
                        /* Get the data of the button */
                        int userAnswer = inputQueue.get(0);
                        inputQueue.remove(0);
                        /* If the button that user pressed is correct, */
                        if (userAnswer == modelAnswer) {
                            index_modelAnswer++;
                            /* User wins! */
                            if (index_modelAnswer == totalSize) {
                                userWinForRound = true;
                                break;
                            }
                            /* Initialize next correct answer */
                            modelAnswer = answerSeq.get(index_modelAnswer);
                        }
                        /* else, the button that user pressed is not correct */
                        else {
                            userWinForRound = false;
                            break;
                        }
                    }

                    /* Add score if user successfully answered */
                    if (userWinForRound) {
                        score++;
                        setScoreText("Score: " + score);
                    }
                    else {
                        setStatusText("You lose.");
                        enableMineButtons(false);
                        enableStartButton(true);
                        break;
                    }
                    waitForMs(300);
                }

            }
        };

        enableMineButtons(true);

        Thread myThread = new Thread (runnable);
        myThread.start();


    }

    private void setStatusText (String s) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("setStatusText", s);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private void setScoreText (String s) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("setScoreText", s);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private void setButtonColor (int buttonNum, int color) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        int[] data = {buttonNum, color};
        bundle.putIntArray("setButtonColor", data);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private void enableStartButton (boolean enabled) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putBoolean("enableStartButton", enabled);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private void enableMineButtons (boolean enabled) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putBoolean("enableMineButtons", enabled);
        msg.setData(bundle);
        handler.sendMessage(msg);

    }


    private void waitForMs (int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            if (msg.getData().get("setStatusText") != null) {
                String data = msg.getData().getString("setStatusText");
                statusText.setText(data);
            }
            else if (msg.getData().get("setScoreText") != null) {
                String data = msg.getData().getString("setScoreText");
                scoreText.setText(data);
            }
            else if (msg.getData().get("setButtonColor") != null) {
                int[] data = msg.getData().getIntArray("setButtonColor");
                mineBut[data[0]].setBackgroundColor(data[1]);
            }
            else if (msg.getData().get("enableStartButton") != null) {
                boolean enabled = msg.getData().getBoolean("enableStartButton");
                startButton.setEnabled(enabled);
            }
            else if (msg.getData().get("enableMineButtons") != null) {
                boolean enabled = msg.getData().getBoolean("enableMineButtons");
                for (int i=0; i<NUM_MINEBUTTONS; i++) {
                    mineBut[i].setEnabled(enabled);
                }
            }
        }
    };

    protected void mineButPressed (View v, int buttonNumber) {
        statusText.setText(String.valueOf(buttonNumber));
        inputQueue.add(buttonNumber);
    }


    private void blinkButton (int n) {
        setButtonColor(n, Color.RED);
        //mineBut[n].setBackgroundColor(0xdd8d3d);
        waitForMs(400);
        setButtonColor(n, Color.WHITE);
        waitForMs(400);
        //mineBut[n].setBackgroundColor(0xd7d7d7);
    }

}