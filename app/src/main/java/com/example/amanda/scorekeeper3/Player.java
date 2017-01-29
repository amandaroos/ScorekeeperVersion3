package com.example.amanda.scorekeeper3;

/**
 * Created by Amanda on 1/5/2017.
 */

public class Player {

    String mName;
    int mScore = 0;

    public Player(String name){
        mName = name;
    }

    public String getName() {return mName;}

    public int getScore() {return mScore;}

    public void addScore(int value) {
        mScore = mScore + value;
    }

    public String toString () {return mName;}

}
