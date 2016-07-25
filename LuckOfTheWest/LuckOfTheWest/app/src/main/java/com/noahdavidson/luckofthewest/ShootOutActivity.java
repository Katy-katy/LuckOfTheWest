package com.noahdavidson.luckofthewest;

import android.media.MediaPlayer;
import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by USER on 7/18/2016.
 */
public class ShootOutActivity extends AppCompatActivity {
    //Media player for sounds
    private MediaPlayer button_sounds[];
    private MediaPlayer background_sound;
    //Text variable
    private TextView directions;
    //Board variables
    private final static int BOARD_SIZE = 9;
    private char board_state[];
    private ImageButton board_buttons[];
    private int images[];
    private Button draw_button;
    private static final char EMPTY_SPACE = ' ';
    private static final char TAKEN_SPACE = 'O';
    //Player turn
    private int player_turn = 0;
    //Random number generator
    private Random rand_num;

    //Start game
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shootout);
        //Create number generator
        rand_num = new Random();
        //Create sound containers
        background_sound = MediaPlayer.create(ShootOutActivity.this, R.raw.whistling_credit_qubodup);
        background_sound.start();
        button_sounds = new MediaPlayer[BOARD_SIZE];
        button_sounds[0] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_1_credit_rock_savage);
        button_sounds[1] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_2_credit_rock_savage);
        button_sounds[2] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_3_credit_rock_savage);
        button_sounds[3] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_2_credit_rock_savage);
        button_sounds[4] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_1_credit_rock_savage);
        button_sounds[5] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_3_credit_rock_savage);
        button_sounds[6] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_3_credit_rock_savage);
        button_sounds[7] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_2_credit_rock_savage);
        button_sounds[8] = MediaPlayer.create(ShootOutActivity.this, R.raw.shot_1_credit_rock_savage);

        //Create image containers
        images = new int[BOARD_SIZE];
        images[0] = R.drawable.bullet_hole_0;
        images[1] = R.drawable.bullet_hole_1;
        images[2] = R.drawable.bullet_hole_2;
        images[3] = R.drawable.bullet_hole_3;
        images[4] = R.drawable.bullet_hole_4;
        images[5] = R.drawable.bullet_hole_5;
        images[6] = R.drawable.bullet_hole_6;
        images[7] = R.drawable.bullet_hole_7;
        images[8] = R.drawable.bullet_hole_8;

        //Create board
        board_state = new char[BOARD_SIZE];
        board_buttons = new ImageButton[BOARD_SIZE];
        board_buttons[0] = (ImageButton) findViewById(R.id.shotButton0);
        board_buttons[1] = (ImageButton) findViewById(R.id.shotButton1);
        board_buttons[2] = (ImageButton) findViewById(R.id.shotButton2);
        board_buttons[3] = (ImageButton) findViewById(R.id.shotButton3);
        board_buttons[4] = (ImageButton) findViewById(R.id.shotButton4);
        board_buttons[5] = (ImageButton) findViewById(R.id.shotButton5);
        board_buttons[6] = (ImageButton) findViewById(R.id.shotButton6);
        board_buttons[7] = (ImageButton) findViewById(R.id.shotButton7);
        board_buttons[8] = (ImageButton) findViewById(R.id.shotButton8);
        for (int i = 0; i < BOARD_SIZE; i++) {
            board_state[i] = EMPTY_SPACE;
            board_buttons[i].setImageResource(R.drawable.bullet_hole_blank);
            board_buttons[i].setEnabled(false);
        }
        draw_button = (Button) findViewById(R.id.draw_button);
        draw_button.setEnabled(true);
    }

    public void executeGame(View view) {
        //Direct the player
        directions = (TextView) findViewById(R.id.directions_text);
        directions.setText(R.string.shoot_out_directions);
        //Enable/Disable buttons
        draw_button.setEnabled(false);
        for (int i = 0; i < BOARD_SIZE; i++) {
            board_buttons[i].setEnabled(true);
        }
        Runnable runnable = new Runnable() {
            public void run() {
                do {
                    if (player_turn % 2 == 0) {
                        player_turn = 0;
                    }
                    if (player_turn == 0) {
                        aiMove();
                    }
                    //drawBoard();
                } while (!checkForLoser());
                resolve();
            }
        };
        Thread runGame = new Thread (runnable);
        runGame.start();
    }

    //End game
    protected void resolve() {
        if (player_turn == 1) {
            setDirectionsText(R.string.win);
        } else {
            setDirectionsText(R.string.lose);
        }
    }

    //Generate move for ai
    protected void aiMove() {
        waitForMs(2000);
        int move;
        do {
            move = rand_num.nextInt(BOARD_SIZE);
        } while (board_state[move] == TAKEN_SPACE);
        board_state[move] = TAKEN_SPACE;
        setButtonImage(move, images[move]);
        setButtonEnable(move, false);
        player_turn++;
    }

    //Check for 3-in-a-row
    protected boolean checkForLoser() {
        //Check columns
        for (int i = 0; i <= 2; i++) {
            if (board_state[i] == TAKEN_SPACE &&
                    board_state[i + 3] == TAKEN_SPACE &&
                    board_state[i + 6] == TAKEN_SPACE) {
                return true;
            }
        }
        //Check rows
        for (int i = 0; i <= 6; i += 3) {
            if (board_state[i] == TAKEN_SPACE &&
                    board_state[i + 1] == TAKEN_SPACE &&
                    board_state[i + 2] == TAKEN_SPACE) {
                return true;
            }
        }
        //Check diagonals
        if (board_state[0] == TAKEN_SPACE &&
                board_state[4] == TAKEN_SPACE &&
                board_state[8] == TAKEN_SPACE) {
            return true;
        }
        if (board_state[2] == TAKEN_SPACE &&
                board_state[4] == TAKEN_SPACE &&
                board_state[6] == TAKEN_SPACE) {
            return true;
        }
        return false;
    }

    //OnClick functions for buttons
    public void onClickButton0(View view) {
        board_state[0] = TAKEN_SPACE;
        setButtonImage(0, images[0]);
        setButtonEnable(0, false);
        player_turn++;
    }

    public void onClickButton1(View view) {
        board_state[1] = TAKEN_SPACE;
        setButtonImage(1, images[1]);
        setButtonEnable(1, false);
        player_turn++;
    }

    public void onClickButton2(View view) {
        board_state[2] = TAKEN_SPACE;
        setButtonImage(2, images[2]);
        setButtonEnable(2, false);
        player_turn++;
    }

    public void onClickButton3(View view) {
        board_state[3] = TAKEN_SPACE;
        setButtonImage(3, images[3]);
        setButtonEnable(3, false);
        player_turn++;
    }

    public void onClickButton4(View view) {
        board_state[4] = TAKEN_SPACE;
        setButtonImage(4, images[4]);
        setButtonEnable(4, false);
        player_turn++;
    }

    public void onClickButton5(View view) {
        board_state[5] = TAKEN_SPACE;
        setButtonImage(5, images[5]);
        setButtonEnable(5, false);
        player_turn++;
    }

    public void onClickButton6(View view) {
        board_state[6] = TAKEN_SPACE;
        setButtonImage(6, images[6]);
        setButtonEnable(6, false);
        player_turn++;
    }

    public void onClickButton7(View view) {
        board_state[7] = TAKEN_SPACE;
        setButtonImage(7, images[7]);
        setButtonEnable(7, false);
        player_turn++;
    }

    public void onClickButton8(View view) {
        board_state[8] = TAKEN_SPACE;
        setButtonImage(8, images[8]);
        setButtonEnable(8, false);
        player_turn++;
    }

    //Pause game for "ms" milliseconds
    private void waitForMs (int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Set src image values for buttons
    /*
    protected void drawBoard() {
        if (board_state[0] == TAKEN_SPACE) {
            setButtonImage(0, R.drawable.bullet_hole_0);
            setButtonEnable(0, false);
        }
        if (board_state[1] == TAKEN_SPACE) {
            setButtonImage(1, R.drawable.bullet_hole_1);
            setButtonEnable(1, false);
        }
        if (board_state[2] == TAKEN_SPACE) {
            setButtonImage(2, R.drawable.bullet_hole_2);
            setButtonEnable(2, false);
        }
        if (board_state[3] == TAKEN_SPACE) {
            setButtonImage(3, R.drawable.bullet_hole_3);
            setButtonEnable(3, false);
        }
        if (board_state[4] == TAKEN_SPACE) {
            setButtonImage(4, R.drawable.bullet_hole_4);
            setButtonEnable(4, false);
        }
        if (board_state[5] == TAKEN_SPACE) {
            setButtonImage(5, R.drawable.bullet_hole_5);
            setButtonEnable(5, false);
        }
        if (board_state[6] == TAKEN_SPACE) {
            setButtonImage(6, R.drawable.bullet_hole_6);
            setButtonEnable(6, false);
        }
        if (board_state[7] == TAKEN_SPACE) {
            setButtonImage(7, R.drawable.bullet_hole_7);
            setButtonEnable(7, false);
        }
        if (board_state[8] == TAKEN_SPACE) {
            setButtonImage(8, R.drawable.bullet_hole_8);
            setButtonEnable(8, false);
        }
    }
    */
    //Allows thread to change button images
    private void setButtonImage(final int buttonNum, final int image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                board_buttons[buttonNum].setImageResource(image);
                button_sounds[buttonNum].start();
            }
        });
        /*
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        int[] data = {buttonNum, image};
        bundle.putIntArray("setButtonImage", data);
        msg.setData(bundle);
        handler.sendMessage(msg);
        */
    }

    //Allows thread to enable/disable buttons
    private void setButtonEnable(final int buttonNum, final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                board_buttons[buttonNum].setEnabled(enable);
            }
        });
        /*
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        int boolNum;
        if (enable) {
            boolNum = 1;
        } else {
            boolNum = 0;
        }
        int data[] = {buttonNum, boolNum};
        bundle.putIntArray("setButtonEnable", data);
        msg.setData(bundle);
        handler.sendMessage(msg);
        */
    }

    //Allows thread to change text
    private void setDirectionsText(final int text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                directions.setText(text);
            }
        });
        /*
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        int data = text;
        bundle.putInt("setDirectionsText", data);
        msg.setData(bundle);
        handler.sendMessage(msg);
        */
    }
    /*
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.getData().get("setButtonImage") != null) {
                int[] data = msg.getData().getIntArray("setButtonImage");
                board_buttons[data[0]].setImageResource(data[1]);
                button_sounds[data[0]].start();
            } else if (msg.getData().get("setButtonEnable") != null) {
                int[] data = msg.getData().getIntArray("setButtonEnable");
                board_buttons[data[0]].setEnabled(!(data[1] == 0));
            } else if (msg.getData().get("setDirectionsText") != null) {
                int data = msg.getData().getInt("setDirectionsText");
                directions.setText(data);
            }
        }
    };
    */
}