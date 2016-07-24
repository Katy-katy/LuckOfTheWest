package com.noahdavidson.luckofthewest;

import android.os.Bundle;
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
    //Text variable
    private TextView directions;
    //Board variables
    private final static int BOARD_SIZE = 9;
    private char board_state[];
    private ImageButton board_buttons[];
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
        //Create board
        board_state = new char[BOARD_SIZE];
        board_buttons = new ImageButton[BOARD_SIZE];
        board_buttons[0] = (ImageButton) findViewById(R.id.shotButton0);
        board_buttons[1] = (ImageButton )findViewById(R.id.shotButton1);
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
        //Create number generator
        rand_num = new Random();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                do {
                    if (player_turn%2 == 0) {
                        player_turn = 0;
                    }
                    if (player_turn == 0) {
                        aiMove();
                    }
                    drawBoard();
                } while (!checkForLoser());
                resolve();
            }
        };

        Thread myThread = new Thread (runnable);
        myThread.start();
    }
    //End game
    protected void resolve() {
        if (player_turn == 1) {
            directions.setText(R.string.win);
        }
        else {
            directions.setText(R.string.lose);
        }
        draw_button.setText(R.string.exit);
    }
    //Generate move for ai
    protected int aiMove() {
        int move;
        do {
            move = rand_num.nextInt(BOARD_SIZE);
        } while (board_state[move] == TAKEN_SPACE);
        board_state[move] = TAKEN_SPACE;
        board_buttons[move].setEnabled(false);
        player_turn++;
        return move;
    }
    //Check for 3-in-a-row
    protected boolean checkForLoser() {
        //Check columns
        for (int i = 0; i <= 2; i++) {
            if (board_state[i] == TAKEN_SPACE &&
                    board_state[i+3] == TAKEN_SPACE &&
                    board_state[i+6] == TAKEN_SPACE) {
                return true;
            }
        }
        //Check rows
        for (int i = 0; i <= 6; i += 3) {
            if (board_state[i] == TAKEN_SPACE &&
                    board_state[i+1] == TAKEN_SPACE &&
                    board_state[i+2] == TAKEN_SPACE) {
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
    public void onClickButton0 (View view) {
        board_state[0] = TAKEN_SPACE;
        board_buttons[0].setEnabled(false);
        player_turn++;
    }
    public void onClickButton1 (View view) {
        board_state[1] = TAKEN_SPACE;
        board_buttons[1].setEnabled(false);
        player_turn++;
    }
    public void onClickButton2 (View view) {
        board_state[2] = TAKEN_SPACE;
        board_buttons[2].setEnabled(false);
        player_turn++;
    }
    public void onClickButton3 (View view) {
        board_state[3] = TAKEN_SPACE;
        board_buttons[3].setEnabled(false);
        player_turn++;
    }
    public void onClickButton4 (View view) {
        board_state[4] = TAKEN_SPACE;
        board_buttons[4].setEnabled(false);
        player_turn++;
    }
    public void onClickButton5 (View view) {
        board_state[5] = TAKEN_SPACE;
        board_buttons[5].setEnabled(false);
        player_turn++;
    }
    public void onClickButton6 (View view) {
        board_state[6] = TAKEN_SPACE;
        board_buttons[6].setEnabled(false);
        player_turn++;
    }
    public void onClickButton7 (View view) {
        board_state[7] = TAKEN_SPACE;
        board_buttons[7].setEnabled(false);
        player_turn++;
    }
    public void onClickButton8 (View view) {
        board_state[8] = TAKEN_SPACE;
        board_buttons[8].setEnabled(false);
        player_turn++;
    }
    //Set src image values for buttons
    protected void drawBoard() {

                if (board_state[0] == TAKEN_SPACE) {
                    board_buttons[0].setImageResource(R.drawable.bullet_hole_0);
                }
                if (board_state[1] == TAKEN_SPACE) {
                    board_buttons[1].setImageResource(R.drawable.bullet_hole_1);
                }
                if (board_state[2] == TAKEN_SPACE) {
                    board_buttons[2].setImageResource(R.drawable.bullet_hole_2);
                }
                if (board_state[3] == TAKEN_SPACE) {
                    board_buttons[3].setImageResource(R.drawable.bullet_hole_3);
                }
                if (board_state[4] == TAKEN_SPACE) {
                    board_buttons[4].setImageResource(R.drawable.bullet_hole_4);
                }
                if (board_state[5] == TAKEN_SPACE) {
                    board_buttons[5].setImageResource(R.drawable.bullet_hole_5);
                }
                if (board_state[6] == TAKEN_SPACE) {
                    board_buttons[6].setImageResource(R.drawable.bullet_hole_6);
                }
                if (board_state[7] == TAKEN_SPACE) {
                    board_buttons[7].setImageResource(R.drawable.bullet_hole_7);
                }
                if (board_state[8] == TAKEN_SPACE) {
                    board_buttons[8].setImageResource(R.drawable.bullet_hole_8);
                }
    }
}
