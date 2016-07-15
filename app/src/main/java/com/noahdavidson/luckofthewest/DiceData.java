package com.noahdavidson.luckofthewest;

/**
 * Created by noahdavidson on 7/14/16.
 */

import java.util.Random;

public class DiceData {

    int playerTotal = 5;
    int playerDice[] = new int[]{0,0,0,0,0,0};

    public int getPlayerTotal() {
        return playerTotal;
    }

    public void setPlayerTotal(int playerTotal) {
        this.playerTotal = playerTotal;
    }

    public void resetHand(){
        for(int i = 0; i<6;i++){
            playerDice[i] = 0;
        }
        generateHand(getPlayerTotal());
    }

    public void generateHand(int playerTotal){
        for(int i=0;i<playerTotal;i++){
            double random = Math.floor(((Math.random() * 6) + 1 ));
            playerDice[(int)random]+=1;
        }
    }
}
