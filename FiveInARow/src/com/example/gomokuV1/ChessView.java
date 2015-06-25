package com.example.gomokuV1;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dell on 6/8/2014.
 * ChessView will draw the view for the SingPlayerActivity and TwoPlayersActivity, and redraw it when players put stone on the chess.
 */
public class ChessView extends View
{
    private int startX = 0; //The start point in X-axis for drawing the chess
    private int startY = 0; ////The start point in Y-axis for drawing the chess
    private int startXForPhoto = 0;
    private int startYForPhoto = 0;
    private int gridNum = 15; //The grid number of the chess
    private int gridWidth = GlobalData.WIDTH / gridNum; //The width of the grid
    private Paint paint = new Paint();
    private boolean isBlackTurn = true; //If it's black side turn
    private boolean validPlacing = true; //If it's a valid placing
    private boolean wantToQuit = false; //If the player want to quit the game
    private boolean wantToRetry = false;  //If the player want to retry the game
    private boolean hasGotBitmapForPlayers;


    private int[][] chess = new int[gridNum][gridNum]; //The array stands for the chess
    private int gameStatus = MarkAndFlag.RUNNING; //The status of the game
    private boolean isHumanTurn = true; //If it's the human's turn
    private Bitmap bitmapForPlayer1; //bitmap for player1's photo
    private Bitmap bitmapForPlayer2; //bitmap for player2's photo
    private Player[] players; //array store the two players
    private Bitmap bitmapForQuitButton; //bitmap for quit icon
    private Bitmap bitmapForRetryButton; //bitmap for retry icon
    private Region quiteButtonRegion; //region for quit button
    private Region retryButtonRegion; //region for retry button

    public ChessView(Context context)
    {

        super(context);
        paint.setAntiAlias(true);
        int gridMod = GlobalData.WIDTH % gridNum;

        //Decide the starting point for drawing the chess
        if (gridMod == 0)
        {
            startX = gridWidth/2;
            startY = gridWidth/2 + GlobalData.WIDTH/6;
            startXForPhoto = startX;
            gridNum--;
        }
        else
        {
            startX = gridMod/2;
            startY = gridWidth/2 + GlobalData.WIDTH/6;
            startXForPhoto = startX;
        }

        players = new Player[2];
        //Transfer the photo to bitmap
        bitmapForQuitButton = ((BitmapDrawable)getResources().getDrawable(R.drawable.quitbutton)).getBitmap();
        bitmapForQuitButton = Bitmap.createScaledBitmap(bitmapForQuitButton,GlobalData.WIDTH /12, GlobalData.WIDTH /12,true);
        quiteButtonRegion = new Region(11*GlobalData.WIDTH/12, GlobalData.HEIGHT - (2*GlobalData.WIDTH/7), GlobalData.HEIGHT,
                GlobalData.HEIGHT - (2*GlobalData.WIDTH/7) + GlobalData.WIDTH/12);
        bitmapForRetryButton = ((BitmapDrawable)getResources().getDrawable(R.drawable.tryagainbutton)).getBitmap();
        bitmapForRetryButton = Bitmap.createScaledBitmap(bitmapForRetryButton,GlobalData.WIDTH /12, GlobalData.WIDTH /12,true);
        retryButtonRegion = new Region(0, GlobalData.HEIGHT - (2*GlobalData.WIDTH/7), GlobalData.HEIGHT/12,
                GlobalData.HEIGHT - (2*GlobalData.WIDTH/7) + GlobalData.WIDTH/12);
        hasGotBitmapForPlayers = false;


    }

    //Draw the chess
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRGB(255,217,102);
        paint.setColor(Color.BLACK);
        //draw the grids
        for (int i = 0; i<= gridNum; i++)
        {
            canvas.drawLine(startX, startY + i * gridWidth, startX + (gridNum * gridWidth), startY + i * gridWidth, paint);
            canvas.drawLine(startX + i * gridWidth, startY, startX + i * gridWidth, startY + (gridNum * gridWidth), paint);
        }

        //draw the chess
        for (int i = 0; i < gridNum; i++)
        {
            for (int j = 0; j < gridNum; j++)
            {
                switch (chess[i][j])
                {
                    case MarkAndFlag.BLACK: //Draw the black stone
                    {
                        paint.setColor(Color.BLACK);
                        canvas.drawCircle((startX + (i + 1) * gridWidth) - gridWidth / 2,
                                (startY + (j + 1) * gridWidth) - gridWidth / 2, gridWidth / 2, paint);
                        break;
                    }
                    case MarkAndFlag.WHITE: //Draw the white stone
                    {
                        paint.setColor(Color.WHITE);
                        canvas.drawCircle((startX + (i + 1) * gridWidth) - gridWidth / 2,
                                (startY + (j + 1) * gridWidth) - gridWidth / 2, gridWidth / 2, paint);
                        break;
                    }
                    default:
                        break;
                }


            }
        }


        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,GlobalData.WIDTH,startY,paint);
        canvas.drawRect(0,startY+(gridNum) * gridWidth,GlobalData.WIDTH,GlobalData.HEIGHT,paint);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
        //Draw the players' name
        canvas.drawText(players[0].getName(),startXForPhoto +  GlobalData.WIDTH/5, GlobalData.WIDTH/10,paint);
        canvas.drawText(players[1].getName(),4 * GlobalData.WIDTH / 5 -startXForPhoto - paint.measureText(players[1].getName()), GlobalData.WIDTH/10,paint);

        //Draw the players' pictures
        if (!hasGotBitmapForPlayers)
        {
            bitmapForPlayer1 = ((BitmapDrawable) getResources().getDrawable(players[0].getPhoto())).getBitmap();
            bitmapForPlayer1 = Bitmap.createScaledBitmap(bitmapForPlayer1, GlobalData.WIDTH / 5, GlobalData.WIDTH / 5, true);
            bitmapForPlayer2 = ((BitmapDrawable) getResources().getDrawable(players[1].getPhoto())).getBitmap();
            bitmapForPlayer2 = Bitmap.createScaledBitmap(bitmapForPlayer2, GlobalData.WIDTH / 5, GlobalData.WIDTH / 5, true);
            hasGotBitmapForPlayers = true;
        }
        canvas.drawBitmap(bitmapForPlayer1,startXForPhoto,startYForPhoto,paint);
        canvas.drawBitmap(bitmapForPlayer2,4 * GlobalData.WIDTH / 5 -startXForPhoto,startYForPhoto,paint);
        canvas.drawBitmap(bitmapForQuitButton,11 * GlobalData.WIDTH / 12, GlobalData.HEIGHT - (2* GlobalData.WIDTH/7),paint);
        if (gameStatus != MarkAndFlag.RUNNING)
        canvas.drawBitmap(bitmapForRetryButton,0,GlobalData.HEIGHT - (2* GlobalData.WIDTH/7),paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                validPlacing = true;
                float touchX = event.getX();
                float touchY = event.getY();
                if (quiteButtonRegion.contains((int) touchX, (int) touchY))       //Touch quit button to quit
                {
                    wantToQuit = true;
                }
                if (gameStatus != MarkAndFlag.RUNNING && retryButtonRegion.contains((int) touchX,(int) touchY))   //Touch retry button to retry
                {
                    wantToRetry = true;
                }
                else
                {

                    if (gameStatus == MarkAndFlag.RUNNING)
                    {

                        if (isHumanTurn)
                        {

                            int index_x = (int) Math.floor((touchX - startX) / gridWidth);
                            int index_y = (int) Math.floor((touchY - startY) / gridWidth);

                            if (index_x >= gridNum || index_x < 0 || index_y < 0 || index_y >= gridNum)
                            {
                                validPlacing = false;
                            }
                            else
                            {

                                if (chess[index_x][index_y] == MarkAndFlag.BLACK || chess[index_x][index_y] == MarkAndFlag.WHITE)
                                {
                                    Toast.makeText(getContext(), "This place is already occupied", Toast.LENGTH_SHORT).show();
                                    validPlacing = false;
                                } else
                                {
                                    if (isBlackTurn)
                                    {
                                        chess[index_x][index_y] = MarkAndFlag.BLACK;
                                        if (hasWon(MarkAndFlag.BLACK))
                                            gameStatus = MarkAndFlag.BLACK_WON;

                                    } else
                                    {
                                        chess[index_x][index_y] = MarkAndFlag.WHITE;
                                        if (hasWon(MarkAndFlag.WHITE))
                                            gameStatus = MarkAndFlag.WHITE_WON;


                                    }
                                    if (isTie())
                                        gameStatus = MarkAndFlag.TIE;
                                    isHumanTurn = false;
                                    invalidate();
                                }
                            }
                        } else
                            Toast.makeText(getContext(), "The opponent is still thinking", Toast.LENGTH_SHORT).show();
                    }


                }break;


            default:break;
        }
        return false;

    }

    public int[][] getChess()
    {
        return chess;
    }

    public void setChess(int i, int j, int blackOrWhite)
    {
        chess[i][j] = blackOrWhite;
    }


    //Check if someone has won
    public boolean hasWon(int mySide)
    {
        for (int i = 0; i < chess.length; i++)
            for (int j = 0; j < chess[i].length; j++)
                if (chess[i][j] == mySide)
                    if ((hasWonAtNorth(mySide, i, j)) || hasWonAtNorthEast(mySide, i, j) || hasWonAtEast(mySide, i, j) || hasWonAtSouthEast(mySide, i, j))
                        return true;
        return false;
    }

    //check if someone has won at NorthEast-SouthWest direction
    public boolean hasWonAtNorthEast(int mySide, int i, int j)
    {
        if (j <= 3 || i >= gridNum - 4)
            return false;
        else
            if (chess[i + 1][j - 1] == mySide && chess[i + 2][j - 2] == mySide && chess[i + 3][j - 3] == mySide && chess[i + 4][j - 4] == mySide)
                return true;
            else
                return false;

    }


    //check if someone has won at North-South direction
    public boolean hasWonAtNorth(int mySide, int i, int j)
    {
        if (j <= 3)
            return false;
        else
            if (chess[i][j - 1] == mySide && chess[i][j - 2] == mySide && chess[i][j - 3] == mySide && chess[i][j - 4] == mySide)
                return true;
            else
                return false;
    }

    //check if someone has won at East-West direction
    public boolean hasWonAtEast(int mySide, int i, int j)
    {
        if (i >= gridNum - 4)
            return false;
        else
        if (chess[i + 1][j] == mySide && chess[i + 2][j] == mySide && chess[i + 3][j] == mySide && chess[i + 4][j] == mySide)
            return true;
        else
            return false;

    }

    //check if someone has won at NorthWest-SouthEast direction
    public boolean hasWonAtSouthEast(int mySide, int i, int j)
    {
        if (j >= gridNum - 4 || i >= gridNum - 4)
            return false;
        else
        if (chess[i + 1][j + 1] == mySide && chess[i + 2][j + 2] == mySide && chess[i + 3][j + 3] == mySide && chess[i + 4][j + 4] == mySide)
            return true;
        else
            return false;

    }



    public boolean getValidPlacing()
    {
        return validPlacing;
    }

    public boolean getIsBlackTurn()
    {
        return isBlackTurn;
    }


    public void setIsBlackTurn(boolean isBlackTurn)
    {
        this.isBlackTurn = isBlackTurn;
    }

    //Check if it's tie
    public boolean isTie()
    {
        for (int i = 0; i < gridNum; i++)
            for (int j = 0; j < gridNum; j++)
                if (chess[i][j] != MarkAndFlag.BLACK || chess[i][j] != MarkAndFlag.WHITE)
                    return false;
        return true;

    }

    public int getGameStatus()
    {
        return gameStatus;
    }


    public void setIsHumanTurn(boolean isHumanTurn)
    {
        this.isHumanTurn = isHumanTurn;
    }

    public void setWantToQuit(boolean wantToQuit)
    {
        this.wantToQuit = wantToQuit;
    }

    public boolean getWantToQuit()
    {
        return wantToQuit;
    }

    public boolean getWantToRetry()
    {
        return wantToRetry;
    }

    //Set the player of the game
    public void setPlayers(Player[] players)
    {
        this.players[0] = players[0];
        this.players[1] = players[1];
    }

    public void setGameStatus(int gameStatus)
    {
        this.gameStatus = gameStatus;
    }









}

