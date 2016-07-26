package com.noahdavidson.luckofthewest;

/**
 * Created by noahdavidson on 7/12/16.
 */
public class player {

    int gold = 500;
    int count = 0;
    int wager = 100;
    int winnings;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setWager(int wager) {
        this.wager = wager;
    }

    public int getWager() {
        return wager;
    }

    public void incWager(){
        this.wager += 10;
    }

    public void decWager(){
        this.wager -= 10;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    public int getWinnings() {
        return winnings;
    }
}
