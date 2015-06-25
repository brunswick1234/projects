package com.example.gomokuV1;

/**
 * Created by dell on 6/6/2014.
 * DatabaseHelper stores and managers the data of two table in the game. One is Players table, another one is MatchResult table.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "ItemDB";
    public static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Player.CREATE_STATEMENT);
        db.execSQL(MatchResult.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Player.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MatchResult.TABLE_NAME);
        onCreate(db);
    }


    //Add player to players table
    public void addPlayer(Player player)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Player.COLUMN_NAME, player.getName());
        values.put(Player.COLUMN_MOTTO, player.getMotto());
        values.put(Player.COLUMN_PHOTO, player.getPhoto());
        values.put(Player.COLUMN_SCORE, player.getScore());
        db.insert(Player.TABLE_NAME, null, values);
        db.close();
    }

    //Add match result to MatchResult table
    public void addMatchResult(MatchResult matchResult)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MatchResult.COLUMN_ID,matchResult.getId());
        values.put(MatchResult.COLUMN_PLAYER1, matchResult.getPlayer1());
        values.put(MatchResult.COLUMN_PLAYER2,matchResult.getPlayer2());
        values.put(MatchResult.COLUMN_RESULT, matchResult.getResult());
        values.put(MatchResult.COLUMN_BLACK_SIDE, matchResult.getBlackSide());
        db.insert(MatchResult.TABLE_NAME, null, values);
        db.close();
    }

    //Get all human players form players table
    public ArrayList<HumanPlayer> getAllHumanPlayer()
    {
        ArrayList<HumanPlayer> humanPlayers = new ArrayList<HumanPlayer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Player.TABLE_NAME +" WHERE " + Player.COLUMN_SCORE + " >= 0 ", null);
        if (cursor.moveToFirst())
        {
            do
            {
                HumanPlayer humanPlayer = new HumanPlayer(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                humanPlayers.add(humanPlayer);
            } while (cursor.moveToNext());
        }


        cursor.close();

        return humanPlayers;

    }

    //Get the player with specified name from players table
    public Player getPlayer(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Player.TABLE_NAME +" WHERE " + Player.COLUMN_NAME + " = ?",new String[]{name});

        if(cursor.moveToFirst())
        {
            Player player = new Player(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            cursor.close();
            return player;
        }
        else
        {
            cursor.close();
            return null;
        }


    }

    //Get the latest matchResult with the names of specified player
    public MatchResult getLastMatchResult(String player1Name,String player2Name)
    {
        MatchResult matchResult = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MatchResult.TABLE_NAME +" WHERE " + MatchResult.COLUMN_PLAYER1 + " = ? AND " + MatchResult.COLUMN_PLAYER2 + " = ? ORDER BY " + MatchResult.COLUMN_ID + " DESC",new String[]{player1Name,player2Name});

        if(cursor.moveToFirst())
        {
            matchResult = new MatchResult(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        return matchResult;

    }


    //Get the AiPlayer with specified name from players table
    public AIPlayer getAIPlayer(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Player.TABLE_NAME +" WHERE " + Player.COLUMN_NAME + " = ?",new String[]{name});

        cursor.moveToFirst();
        AIPlayer player = new AIPlayer(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        cursor.close();
        return player;

    }


    //Edit the player in the players table
    public void editPlayer(Player player)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.COLUMN_MOTTO,player.getMotto());
        contentValues.put(Player.COLUMN_PHOTO,player.getPhoto());
        contentValues.put(Player.COLUMN_SCORE,player.getScore());
        db.update(Player.TABLE_NAME,contentValues,Player.COLUMN_NAME + " = ?",new String[] {player.getName()});
        db.close();
    }

    //Get the HumanPlayer with specified name from players table
    public HumanPlayer getHumanPlayer(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Player.TABLE_NAME +" WHERE " + Player.COLUMN_NAME + " = ?",new String[]{name});

        if(cursor.moveToFirst())
        {
            HumanPlayer humanPlayer = new HumanPlayer(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            cursor.close();
            return humanPlayer;
        }
        else
        {
            cursor.close();
            return null;
        }


    }


    //Get the mathID for the new entry of MatchResult table by counting the records existed in the current table, then increase the count number by 1
    public int getMatchID()
    {
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + MatchResult.TABLE_NAME, null);
        if (cursor.moveToFirst())
        {
            i = cursor.getInt(0);
        }
        i++;
        return i;
    }

    /*Get the result of MatchResult for one specified player
    * @return int[], the first value of the array is the total number of matches the player took, and the second value of array is the number of the match of the player won.
    */
    public int[] getPlayerMatchResult(String player1Name)
    {
        int totalNumber = 0;
        int winNumber = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + MatchResult.TABLE_NAME +
                " WHERE " + MatchResult.COLUMN_PLAYER1 + " = ? OR " + MatchResult.COLUMN_PLAYER2 + " = ? ",new String[]{player1Name,player1Name});

        if(cursor.moveToFirst())
        {
           totalNumber = cursor.getInt(0);
        }
        Cursor cursor1 = db.rawQuery("SELECT COUNT(*) FROM " + MatchResult.TABLE_NAME +
                " WHERE " + MatchResult.COLUMN_RESULT + " = ? ",new String[]{player1Name});
        if(cursor1.moveToFirst())
        {
            winNumber = cursor1.getInt(0);
        }

        int[] matchResultCount = new int[]{totalNumber,winNumber};
        return matchResultCount;

    }


    public int[] getAllMatchResults(String player1Name,String player2Name)
    {
        int[] matchResultScore = new int[2];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM (SELECT * FROM " + MatchResult.TABLE_NAME +" WHERE " + MatchResult.COLUMN_PLAYER1 + " = ? OR "
                + MatchResult.COLUMN_PLAYER2 + " = ? ) WHERE " + MatchResult.COLUMN_RESULT + " = ?",new String[]{player2Name,player2Name,player1Name});

        if(cursor.moveToFirst())
        {
            matchResultScore[0] = cursor.getInt(0);
        }
        else
        {
            matchResultScore[0] = 0;
        }

        Cursor cursor1 = db.rawQuery("SELECT COUNT(*) FROM (SELECT * FROM " + MatchResult.TABLE_NAME +" WHERE " + MatchResult.COLUMN_PLAYER1 + " = ? OR "
                + MatchResult.COLUMN_PLAYER2 + " = ? ) WHERE " + MatchResult.COLUMN_RESULT + " = ?",new String[]{player1Name,player1Name,player2Name});
        if(cursor1.moveToFirst())
        {
            matchResultScore[1] = cursor1.getInt(0);
        }
        else
        {
            matchResultScore[1] = 0;
        }

        return matchResultScore;


    }


}
