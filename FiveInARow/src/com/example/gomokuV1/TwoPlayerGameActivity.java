package com.example.gomokuV1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by dell on 6/16/2014.
 * TwoPlayerGameActivity class is response for the two players game mode of the application.
 * It works with the ChessView to implement the two players game
 */
public class TwoPlayerGameActivity extends Activity
{
    private HumanPlayer humanPlayer1; //player1 of the game
    private HumanPlayer humanPlayer2; //player2 of the game
    private ChessView chessView; //the view of the activity
    private DatabaseHelper databaseHelper;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        //Set the size of the screen
        Display screen = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        screen.getSize(size);
        GlobalData.setWIDTH(size.x);
        GlobalData.setHEIGHT(size.y);
        chessView = new ChessView(this);
        humanPlayer1 = getIntent().getParcelableExtra("HumanPlayer");
        humanPlayer2 = getIntent().getParcelableExtra("HumanPlayerOpponent");

        Player[] players = new Player[2];
        players[0] = humanPlayer1;
        players[1] = humanPlayer2;
        chessView.setPlayers(players);

        //Get the lasted match of the two players
        MatchResult matchResult1 = databaseHelper.getLastMatchResult(humanPlayer1.getName(),humanPlayer2.getName());
        MatchResult matchResult2 = databaseHelper.getLastMatchResult(humanPlayer2.getName(),humanPlayer1.getName());
        MatchResult matchResult = null;
        if (matchResult1 == null && matchResult2 == null)
            matchResult = null;
        else
        {
            if (matchResult1 == null)
                matchResult = matchResult2;
            else
            {
                if ( matchResult2 == null)
                    matchResult = matchResult1;
                else
                {
                    if (matchResult1.getId() > matchResult2.getId())
                        matchResult = matchResult1;
                    else
                        matchResult = matchResult2;
                }
            }
        }

        //switch between the sides in this game
        if (matchResult == null || matchResult.getBlackSide().equals(humanPlayer2.getName()))
        {
            humanPlayer1.setBlackOrWhiteSide(MarkAndFlag.BLACK);
            humanPlayer2.setBlackOrWhiteSide(MarkAndFlag.WHITE);
        }
        else
        {
            humanPlayer2.setBlackOrWhiteSide(MarkAndFlag.BLACK);
            humanPlayer1.setBlackOrWhiteSide(MarkAndFlag.WHITE);
        }
        setContentView(chessView);

    }



    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (chessView.getWantToQuit())
                {
                    if (chessView.getGameStatus() == MarkAndFlag.RUNNING)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(chessView.getContext());
                        builder.setTitle("Want to quit?");
                        builder.setMessage("The game is still going on. Do you want to quit now?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                chessView.setWantToQuit(false);
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    }
                    else
                    {
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                    }

                }
                if (chessView.getWantToRetry())
                {
                    this.finish();
                    this.startActivity(this.getIntent());

                } break;

            case MotionEvent.ACTION_UP:
                if (chessView.getValidPlacing())
                {
                    if (chessView.getIsBlackTurn())
                        chessView.setIsBlackTurn(false);
                    else
                        chessView.setIsBlackTurn(true);

                    chessView.setIsHumanTurn(true);

                    if (chessView.getGameStatus() == MarkAndFlag.BLACK_WON || chessView.getGameStatus() == MarkAndFlag.WHITE_WON)
                    {
                        String winnerName = findWhoWon(chessView.getGameStatus());
                        Toast.makeText(getApplicationContext(), winnerName + " has Won", Toast.LENGTH_LONG).show();
                        MatchResult matchResult = new MatchResult(databaseHelper.getMatchID(),humanPlayer1.getName(),humanPlayer2.getName(),winnerName,findWhoIsBlackSide());
                        databaseHelper.addMatchResult(matchResult);
                        calculateScore(winnerName);
                        databaseHelper.editPlayer(humanPlayer1);
                        databaseHelper.editPlayer(humanPlayer2);
                    }
                    if (chessView.getGameStatus() == MarkAndFlag.TIE)
                        Toast.makeText(getApplicationContext(), "Tie", Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
        return true;
    }


    //Find who win the game
    //@param int blackWonOrWhiteWon the winner's side
    // @return the winner's name
    public String findWhoWon(int blackWonOrWhiteWon)
    {

        if ((blackWonOrWhiteWon == MarkAndFlag.BLACK_WON && humanPlayer1.getBlackOrWhiteSide() == MarkAndFlag.BLACK) ||
                (blackWonOrWhiteWon == MarkAndFlag.WHITE_WON && humanPlayer1.getBlackOrWhiteSide() == MarkAndFlag.WHITE))
            return humanPlayer1.getName();
        else
            return humanPlayer2.getName();

    }


    //Return the name of the black side of the game
    public String findWhoIsBlackSide()
    {
        if (humanPlayer1.getBlackOrWhiteSide() == MarkAndFlag.BLACK)
            return humanPlayer1.getName();
        else
            return humanPlayer2.getName();
    }



    //Calculate the score for human players
    public void calculateScore(String winnerName)
    {
        int score1;
        int score2;
        if (humanPlayer1.getName().equals(winnerName))
        {
            score1 = humanPlayer1.getScore() + humanPlayer2.getScore()/10;
            score2 = humanPlayer2.getScore() - humanPlayer1.getScore()/10;
            if (score2 < 0)
                score2 = 0;
        }
        else
        {
            score1 = humanPlayer1.getScore() - humanPlayer2.getScore()/10;
            score2 = humanPlayer2.getScore() + humanPlayer1.getScore()/10;
            if (score1 < 0)
                score1 = 0;
        }

        humanPlayer1.setScore(score1);
        humanPlayer2.setScore(score2);

    }


}
