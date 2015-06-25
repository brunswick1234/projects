package com.example.gomokuV1;

/**
 * Created by dell on 6/9/2014.
 * MatchResult stands for one match in the game.
 */
public class MatchResult
{
    public static final String TABLE_NAME = "Match_Results";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLAYER1 = "player1";
    public static final String COLUMN_PLAYER2 = "player";
    public static final String COLUMN_RESULT = "result";
    public static final String COLUMN_BLACK_SIDE = "black_side";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    COLUMN_PLAYER1 + " TEXT NOT NULL, " +
                    COLUMN_PLAYER2 + " TEXT NOT NULL, " +
                    COLUMN_RESULT + " INTEGER NOT NULL, " +
                    COLUMN_BLACK_SIDE + " STRING NOT NULL" +
                    ")";

    private int id;
    private String player1;  //The name of the first player of the game. If it's a Ai against human game, the ai will be the player1.
    private String player2; //The name of the second player. If it's a Ai against human game, the human will be the player2.
    private String result;  //The name of the winner of the match.
    private String blackSide; //The name of the black side of the match.

    public MatchResult(int id, String player1, String player2, String result, String blackSide)
    {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
        this.blackSide = blackSide;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setPlayer1(String player1)
    {
        this.player1 = player1;
    }

    public String getPlayer1()
    {
        return player1;
    }

    public void setPlayer2(String player2)
    {
        this.player2 = player2;
    }

    public String getPlayer2()
    {
        return player2;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getResult()
    {
        return result;
    }

    public void setBlackSide(String blackSide)
    {
        this.blackSide = blackSide;
    }

    public String getBlackSide()
    {
        return blackSide;
    }


}
