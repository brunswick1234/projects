package com.example.gomokuV1;

/**
 * Created by dell on 6/10/2014.
 * MarkAndFlag class stores some constant integers which stands for various flags and marks,
 * and it makes the code in SingleGameActivity, TwoPlayerActivity, ChessView, Player and AiPlayer classes more easy to understand.
 */
public class MarkAndFlag
{
    public static final int BLACK_WON = 1;
    public static final int WHITE_WON = 2;
    public static final int RUNNING = 0;
    public static final int TIE = 3;

    public static final int OPEN = 1;
    public static final int CLOSED = 2;


    public static final int ALIVE = 3;
    public static final int HALF_ALIVE = 4;
    public static final int DEAD = 5;

    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public static final int FIVE = 200000;
    public static final int OPEN_FOUR = 500;
    public static final int TWO_HALF_OPEN_FOUR = 500;
    public static final int HALF_OPEN_FOUR_AND_OPEN_THREE = 500;


}
