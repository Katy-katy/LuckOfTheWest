package com.noahdavidson.luckofthewest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * Created by noahdavidson on 7/9/16.
 */
public class SaloonActivity extends AppCompatActivity {

    final Context context = this;

    int player_gold = GameBoardActivity.user_player.getGold();
    int wageMin = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon);

        Button button = (Button) findViewById(R.id.wagerButton);

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        public void run() {

                            if (player_gold < wageMin) {
                                Context context = getApplicationContext();
                                CharSequence text = "Come back with more loot partner";
                                int duration = Toast.LENGTH_SHORT;

                                Toast.makeText(context, text, duration).show();
                            } else {


                                final Dialog wage_box = new Dialog(SaloonActivity.this);
                                wage_box.setTitle("Wager");
                                wage_box.setContentView(R.layout.wage_dialog_layout);
                                wage_box.show();

                                final TextView wage_bid = (TextView) wage_box.findViewById(R.id.wage_amount);
                                Button inc_wager = (Button) wage_box.findViewById(R.id.inc_up);
                                Button dec_wager = (Button) wage_box.findViewById(R.id.dec_down);
                                Button close_wager = (Button) wage_box.findViewById(R.id.wager_cancel);
                                Button confirm_wager = (Button) wage_box.findViewById(R.id.wager_confirm);

                                wage_bid.setText("" + GameBoardActivity.user_player.getWager());

                                Thread t = new Thread() {

                                    @Override
                                    public void run() {
                                        try {
                                            while (!isInterrupted()) {
                                                Thread.sleep(500);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        wage_bid.setText("" + GameBoardActivity.user_player.getWager());
                                                    }
                                                });
                                            }
                                        } catch (InterruptedException e) {
                                        }
                                    }
                                };

                                t.start();

                                inc_wager.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //incWage();
                                        GameBoardActivity.user_player.incWager();
                                    }
                                });

                                dec_wager.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //decWage();
                                        GameBoardActivity.user_player.decWager();
                                    }
                                });

                                close_wager.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        wage_box.dismiss();
                                    }
                                });


                                confirm_wager.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        GameBoardActivity.user_player.setGold((player_gold - GameBoardActivity.user_player.getWager()));
                                        wage_box.dismiss();
                                        Intent intent = new Intent(SaloonActivity.this, LiarsDiceActivity.class);
                                        startActivity(intent);
                                    }
                                });
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
}
