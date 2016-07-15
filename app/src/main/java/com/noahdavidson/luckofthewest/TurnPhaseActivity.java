package com.noahdavidson.luckofthewest;

/**
 * Created by noahdavidson on 7/14/16.
 */
public class TurnPhaseActivity {
    int totalDice;
    int lastCall =1;
    int lastDice = 1;
    boolean firstTurn = true;


    public int getTotalDice() {
        return totalDice;
    }

    public void setTotalDice(int totalDice) {
        this.totalDice = totalDice;
    }

    public void resetTotalDice(){
        this.totalDice = 25;
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
        this.firstTurn = firstTurn;
    }
}
