package com.example.gomokuV1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.graphics.Point;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by dell on 6/8/2014.
 * SingleGameActivity class is response for the single game mode of the application.
 * It works with the ChessView to implement the single game
 */
public class SingleGameActivity extends Activity
{
    private ChessView chessView;  //view of this activity
    private int[][] chess;  //an two dimensions array that store the information of the chess
    private AIPlayer aiPlayer; // AI in the single game
    private HumanPlayer humanPlayer; //Human player in the single game
    private DatabaseHelper databaseHelper; //databaseHelper of the class


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
        chess = chessView.getChess();

        aiPlayer = getIntent().getParcelableExtra("AIPlayer");
        aiPlayer.setJustStart(true);
        humanPlayer = getIntent().getParcelableExtra("HumanPlayer");
        Player[] players = new Player[2];
        players[0] = aiPlayer;
        players[1] = humanPlayer;
        chessView.setPlayers(players);

        //Get the lasted matchResult between the two players
        MatchResult matchResult = databaseHelper.getLastMatchResult(aiPlayer.getName(), humanPlayer.getName());
        //int[] matchResultScore = databaseHelper.getAllMatchResults(aiPlayer.getName(), humanPlayer.getName());

        //If two player haven't play against each other before, the AI will be the black side, if they have played against other before, switch side in this game
        if (matchResult == null || matchResult.getBlackSide().equals(humanPlayer.getName()))
        {
            aiPlayer.setBlackOrWhiteSide(MarkAndFlag.BLACK);
            int[] stonePosition = aiPlayer.placeStone(chess);
            chessView.setChess(stonePosition[0], stonePosition[1], aiPlayer.getBlackOrWhiteSide());
            chessView.setIsHumanTurn(true);
            chessView.setIsBlackTurn(false);
        } else
        {
            aiPlayer.setBlackOrWhiteSide(MarkAndFlag.WHITE);
        }
        setContentView(chessView);


    }


    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: //Handle the action_down movement
                if (chessView.getWantToQuit())  //If the human player want to quit
                {
                    if (chessView.getGameStatus() == MarkAndFlag.RUNNING) //If the game is still running
                    {
                        //Build a dialog to ask user if he/she want to quit now
                        AlertDialog.Builder builder = new AlertDialog.Builder(chessView.getContext());
                        builder.setTitle("Want to quit?");
                        builder.setMessage("If you quit now, you will lose the game. Are you sure you still want to quit?");

                        //Quit the game and back to MainActivity
                        builder.setPositiveButton("Yes, I give in", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                MatchResult matchResult = new MatchResult(databaseHelper.getMatchID(), aiPlayer.getName(),
                                        humanPlayer.getName(), aiPlayer.getName(), findWhoIsBlackSide());
                                databaseHelper.addMatchResult(matchResult);
                                calculateScore(aiPlayer.getName());
                                databaseHelper.editPlayer(humanPlayer);
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);

                            }
                        });

                        //Cancel quit
                        builder.setNegativeButton("Never", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                chessView.setWantToQuit(false);
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    } else
                    {
                        //If the game is over, and user press the quit button, just back to MainActivity
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }

                }

                if (chessView.getWantToRetry())  //If the player want to retry the game when the game is over
                {
                    this.finish();
                    this.startActivity(this.getIntent());

                }
                break;

            case MotionEvent.ACTION_UP:  //Handle the action_up movement

                if (chessView.getValidPlacing()) //If it's a valid placing stone
                {
                    if (chessView.getGameStatus() == MarkAndFlag.RUNNING)
                    {
                        int[] stonePosition = aiPlayer.placeStoneTwoStep(chess); //Get the AI's placing position
                        chessView.setChess(stonePosition[0], stonePosition[1], aiPlayer.getBlackOrWhiteSide()); //Update the chess
                        chessView.invalidate(); //Redraw the ChessView

                        //If AI won
                        if (chessView.hasWon(aiPlayer.getBlackOrWhiteSide()))
                            if (aiPlayer.getBlackOrWhiteSide() == MarkAndFlag.BLACK)
                                chessView.setGameStatus(MarkAndFlag.BLACK_WON);
                            else
                                chessView.setGameStatus(MarkAndFlag.WHITE_WON);

                        chessView.setIsHumanTurn(true); //After ai placing, it's human's turn
                    }

                    //If the some one won the game
                    if (chessView.getGameStatus() == MarkAndFlag.BLACK_WON || chessView.getGameStatus() == MarkAndFlag.WHITE_WON)
                    {
                        String winnerName = findWhoWon(chessView.getGameStatus()); //find who won
                        Toast.makeText(getApplicationContext(), winnerName + " has Won", Toast.LENGTH_LONG).show();

                        //Update matchResult table and players table
                        MatchResult matchResult = new MatchResult(databaseHelper.getMatchID(), aiPlayer.getName(),
                                humanPlayer.getName(), winnerName, findWhoIsBlackSide());
                        databaseHelper.addMatchResult(matchResult);
                        calculateScore(winnerName); //Calculate the score for human player
                        databaseHelper.editPlayer(humanPlayer);

                    }

                    //If it's tie
                    if (chessView.getGameStatus() == MarkAndFlag.TIE)
                    {
                        Toast.makeText(getApplicationContext(), "Tie", Toast.LENGTH_LONG).show();

                        //Update matchResult table
                        MatchResult matchResult = new MatchResult(databaseHelper.getMatchID(), aiPlayer.getName(), humanPlayer.getName(), "", findWhoIsBlackSide());
                        databaseHelper.addMatchResult(matchResult);
                    }

                }
                break;
            default:
                break;
        }
        return true;
    }


    //Find who win the game
    //@param int blackWonOrWhiteWon the winner's side
    // @return the winner's name
    public String findWhoWon(int blackWonOrWhiteWon)
    {


        if ((blackWonOrWhiteWon == MarkAndFlag.BLACK_WON && aiPlayer.getBlackOrWhiteSide() == MarkAndFlag.BLACK) ||
                (blackWonOrWhiteWon == MarkAndFlag.WHITE_WON && aiPlayer.getBlackOrWhiteSide() == MarkAndFlag.WHITE))
            return aiPlayer.getName();
        else
            return humanPlayer.getName();
    }


    //Return the name of the black side of the game
    public String findWhoIsBlackSide()
    {
        if (aiPlayer.getBlackOrWhiteSide() == MarkAndFlag.BLACK)
            return aiPlayer.getName();
        else
            return humanPlayer.getName();
    }


    //Calculate the score for human player
    public void calculateScore(String winnerName)
    {
        int scoreForLost = 0;
        int scoreForWin = 0;
        if (aiPlayer.getName().equals("Homer"))
        {
            scoreForLost = -10;
            scoreForWin = 2;
        }

        if (aiPlayer.getName().equals("Bart"))
        {
            scoreForLost = -5;
            scoreForWin = 5;
        }

        if (aiPlayer.getName().equals("Lisa"))
        {
            scoreForLost = -2;
            scoreForWin = 10;
        }


        int score;
        if (winnerName.equals(aiPlayer.getName()))
        {
            score = humanPlayer.getScore() + scoreForLost;
            if (score < 0)
                score = 0;
        } else
        {
            score = humanPlayer.score + scoreForWin;
        }

        humanPlayer.setScore(score);

    }


}








