package com.example.gomokuV1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dell on 6/7/2014.
 */
public class AIPlayer extends Player implements Parcelable
{

    private int opponentSide;  //HumanPlayer's side in the game
    private boolean justStart = true; //If the game just start, and no stone is placed on the chess yet
    public AIPlayer(String name,String motto, int photo)
    {
        super(name,motto,photo, -1);
    }

    public static final Creator CREATOR = new Creator()
    {
        public AIPlayer createFromParcel(Parcel in)
        {
            return new AIPlayer(in);
        }

        public AIPlayer[] newArray(int size)
        {
            return new AIPlayer[size];
        }
    };

    public AIPlayer(Parcel in)
    {
        super(in);
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(name);
        parcel.writeString(motto);
        parcel.writeInt(photo);
        parcel.writeInt(score);
    }


    @Override
    public void setBlackOrWhiteSide(int blackOrWhiteSide)
    {
        this.blackOrWhiteSide = blackOrWhiteSide;
        if (blackOrWhiteSide == MarkAndFlag.BLACK)
            opponentSide = MarkAndFlag.WHITE;
        else
            opponentSide = MarkAndFlag.BLACK;
    }

    /*public int[] placeStone(int[][] chess)
    {
        boolean possibleToWin = true;
        int randomDirection =(int)( Math.random() * (4));
        int direction;
        for (int i = 0; i < chess.length; i++)
            for (int j = 0; j < chess[i].length; j++)
                if (chess[i][j] == blackOrWhiteSide)
                {

                    if (!possibleToWin)
                    {
                        randomDirection = (int) (Math.random() * (4));
                        direction = randomDirection;
                        possibleToWin = true;
                    }
                    switch (direction)
                    {
                        case 0:
                            if(i - 1 < 0)
                                    if (chess[i + 1][j] == opponentSide || chess[i+2][j] == opponentSide || chess[i+3][j] == opponentSide || chess[i+4][j] == opponentSide)


                    }




        int[] stonePosition = new int[3];
        stonePosition[0] = (int)(Math.random()*15);
        stonePosition[1] = (int)(Math.random()*15);
        stonePosition[2] = getBlackOrWhiteSide();
        return stonePosition;
    }*/

    public int[] placeStoneTwoStep(int[][] chess)
    {
        int[][] chess2 = new int[15][15];
        int[] bestPlace = new int[3];
        bestPlace[2] = 0;
        int[] temp;
        for (int i = 0; i < chess2.length; i++)
        {
            for (int j = 0; j < chess2[i].length; j++)
                chess2[i][j] = chess[i][j];
        }

        for (int i = 0; i < chess2.length; i++)
        {
            for (int j = 0; j < chess2[i].length; j++)
            {
                if (chess2[i][j] != blackOrWhiteSide && chess[i][j] != opponentSide)
                {
                    chess2[i][j] = blackOrWhiteSide;  //If place a stone here

                    if (blackOrWhiteSide == MarkAndFlag.BLACK)   //Switch users
                    {
                        setBlackOrWhiteSide(MarkAndFlag.WHITE);
                    } else
                    {
                        setBlackOrWhiteSide(MarkAndFlag.BLACK);
                    }

                    int[] placeByOpponent = placeStone(chess2);

                    if (blackOrWhiteSide == MarkAndFlag.BLACK)  //Switch back
                    {
                        setBlackOrWhiteSide(MarkAndFlag.WHITE);
                    } else
                    {
                        setBlackOrWhiteSide(MarkAndFlag.BLACK);
                    }

                    chess2[placeByOpponent[0]][placeByOpponent[1]] = opponentSide;
                    temp = placeStone(chess2);
                    chess2[placeByOpponent[0]][placeByOpponent[1]] = 0;
                    if (temp[2] >= bestPlace[2])
                    {
                        bestPlace = temp;
                        //chess2[bestPlace[0]][bestPlace[1]] = opponentSide;
                    }

                }


            }
        }
        //chess2[bestPlace[0]][bestPlace[1]] = opponentSide;
        return bestPlace;
    }

    /*
    * @param int[][] chess, two dimensional integer array that the information of the chess
    * @return int[]  the position ai will place its stone
     */
    public int[] placeStone(int[][] chess)
    {

        if(justStart && blackOrWhiteSide == MarkAndFlag.BLACK) //If Ai play as black side(go first in game), and the game just start
        {

            int i;
            int j;
            //Generate a random position that has a least two grids away from the board of the chess
            i = 2 +(int)( Math.random() * (chess.length - 2));
            j = 2 + (int)( Math.random() * (chess[0].length -2));
            justStart = false;
            int[] position  = new int[]{i,j};
            return position;
        }
        else
        {

            int[][] pointsForStone = new int[chess.length][chess[0].length]; //Score for each empty positions
            int[][] pointsForStone2 = new int[chess.length][chess[0].length]; //Score for each empty positions from the opponent's side view.

            //Evaluate each position
            for (int i = 0; i < chess.length; i++)
            {
                for (int j = 0; j < chess[i].length; j++)
                {
                    if (chess[i][j] != blackOrWhiteSide && chess[i][j] != opponentSide) //Only empty position is valid
                    {
                        //Give points to the position by evaluating the surrounding of this position from four directions.
                        //This is from Ai's side view
                        pointsForStone[i][j] = givePoint(evaluateEastAndWest(chess, i, j), evaluateNorthAndSouth(chess, i, j),
                                evaluateNorthWestAndSouthEast(chess, i, j), evaluateNorthEastAndSouthWest(chess, i, j));

                        //Switch to opponent, to give points to each position from the opponent's view.
                        if (blackOrWhiteSide == MarkAndFlag.BLACK)
                        {
                            setBlackOrWhiteSide(MarkAndFlag.WHITE);
                        } else
                        {
                            setBlackOrWhiteSide(MarkAndFlag.BLACK);
                        }
                        //Give points to the position, but only a half of points is given as this is from opponent side view.
                        //One position is valuable to opponent is also valuable to Ai because by occupy this position, Ai can stop opponent to win.
                        //However, this position will only be given a half of points because more weight should be given to those positions that is valuable
                        //from the Ai's side view.
                        pointsForStone2[i][j] = givePoint(evaluateEastAndWest(chess, i, j), evaluateNorthAndSouth(chess, i, j)
                                , evaluateNorthWestAndSouthEast(chess, i, j), evaluateNorthEastAndSouthWest(chess, i, j)) / 2;

                        //Add two points to get the final points for one position, this will combined the attacking and defense in the game.
                        pointsForStone[i][j] = pointsForStone2[i][j] + pointsForStone[i][j];

                        //Switch back the Ai's original side
                        if (blackOrWhiteSide == MarkAndFlag.BLACK)
                        {
                            setBlackOrWhiteSide(MarkAndFlag.WHITE);
                        } else
                        {
                            setBlackOrWhiteSide(MarkAndFlag.BLACK);
                        }

                    }
                }
            }

            int maxPoint = 0;
            int i1 = 0;
            int j1 = 0;
            //Find the position with the highest points
            for (int ii = 0; ii < pointsForStone.length; ii++)
            {
                for (int jj = 0; jj < pointsForStone[ii].length; jj++)
                {
                    if (pointsForStone[ii][jj] >= maxPoint)
                    {
                        maxPoint = pointsForStone[ii][jj];
                        i1 = ii;
                        j1 = jj;
                    }
                }
            }

            //if the highest points is zero, Generate a random position that has a least two grids away from the board of the chess
            //and it's not occupied
            if (maxPoint == 0)
            {
                while (true)
                {
                    i1 = 2 +(int)( Math.random() * (chess.length - 2));
                    j1 = 2 +(int)( Math.random() * (chess[0].length -2));
                    if (chess[i1][j1] == opponentSide || chess[i1][j1] == blackOrWhiteSide)
                        continue;
                    else
                        break;
                }
            }

            //Return the best position
            int[] position = new int[]{i1, j1,maxPoint};
            return position;
        }




    }
    @Override
    public int describeContents()
    {
        return 0;
    }


    /*If a stone was placed on this position, how long of a broken row of my side will be formed in horizontal direction
    *   @para chess, the layout of the chess
    *   @para i and j, the indexes of the position to be evaluated in chess
    *   @return int[], the first int of array is the length of the broken row, the second int of array is the open or closed status of the row.
     */
    public int[] evaluateEastAndWest(int[][] chess, int i, int j)
    {
        int min = 0; //The
        int max = 0; //The south end
        int minEndOpenOrClosed;
        int maxEndOpenOrClosed;


        for (int possibleMin = i - 1; possibleMin >= i - 4; possibleMin --)
        {
            if (possibleMin < 0)
            {
               min = possibleMin + 1;
               break;
            }
            if (chess[possibleMin][j] != blackOrWhiteSide)
            {
                min = possibleMin + 1;
                break;
            }
            min = possibleMin;


        }
        if (min == 0 || chess[min - 1][j] == opponentSide)
            minEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
            minEndOpenOrClosed = MarkAndFlag.OPEN;


        for (int possibleMax = i + 1; possibleMax <= i + 4; possibleMax ++)
        {
            if (possibleMax >= chess.length)
            {
                max = possibleMax - 1;
                break;
            }
            if (chess[possibleMax][j] != blackOrWhiteSide )
            {
                max = possibleMax - 1;
                break;
            }

            max = possibleMax;

        }
        if (max == chess.length - 1 || chess[max + 1][j] == opponentSide)
            maxEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
            maxEndOpenOrClosed = MarkAndFlag.OPEN;




        int[] result = new int[]{(max - min + 1),deadOrLive(minEndOpenOrClosed,maxEndOpenOrClosed)};
        return result;





    }


    public void setJustStart(boolean justStart)
    {
        this.justStart = justStart;
    }


    /*If a stone was placed on this position, how long of a broken row of my side will be formed in vertical direction
    *   @para chess, the layout of the chess
    *   @para i and j, the indexes of the position to be evaluated in chess
    *   @return int[], the first int of array is the length of the broken row, the second int of array is the dead alive status of the row.
     */
    public int[] evaluateNorthAndSouth(int[][] chess, int i, int j)
    {
        int min = 0;
        int max = 0;
        int minEndOpenOrClosed;
        int maxEndOpenOrClosed;

        for (int possibleMin = j - 1; possibleMin >= j - 4; possibleMin --)
        {
            if (possibleMin < 0)
            {
                min = possibleMin + 1;
                break;
            }
            if (chess[i][possibleMin] != blackOrWhiteSide )
            {
                min = possibleMin + 1;
                break;
            }
            min = possibleMin;


        }
        if (min == 0 || chess[i][min - 1] == opponentSide)
            minEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
            minEndOpenOrClosed = MarkAndFlag.OPEN;

        for (int possibleMax = j + 1; possibleMax <= j + 4; possibleMax ++)
        {
            if (possibleMax >= chess[i].length)
            {
                max = possibleMax - 1;
                break;
            }
            if (chess[i][possibleMax] != blackOrWhiteSide )
            {
                max = possibleMax - 1;
                break;
            }

            max = possibleMax;

        }
        if (max == chess[i].length - 1 || chess[i][max + 1] == opponentSide)
            maxEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
            maxEndOpenOrClosed = MarkAndFlag.OPEN;


        int[] result = new int[]{(max - min + 1),deadOrLive(minEndOpenOrClosed,maxEndOpenOrClosed)};
        return result;

    }

    /*If a stone was placed on this position, how long of a broken row of my side will be formed in NorthWest--SouthEst direction
    *   @para chess, the layout of the chess
    *   @para i and j, the indexes of the position to be evaluated in chess
    *   @return int[], the first int of array is the length of the broken row, the second int of array is the dead or alive status of the row.
     */
    public int[] evaluateNorthWestAndSouthEast(int[][] chess, int i, int j)
    {
        int min = 0;
        int max = 0;
        int minEndOpenOrClosed;
        int maxEndOpenOrClosed;

        int possibleMaxj = j + 1;
        for (int possibleMaxi = i + 1; possibleMaxi <= i + 4; possibleMaxi ++)
        {

                if (possibleMaxi >= chess.length  || possibleMaxj >= chess[0].length)
                {
                    max = possibleMaxi - 1;
                    possibleMaxj--;
                    break;
                }
                if (chess[possibleMaxi][possibleMaxj] != blackOrWhiteSide)
                {
                    max = possibleMaxi - 1;
                    possibleMaxj--;
                    break;
                }
                max = possibleMaxi;
                possibleMaxj++;


        }

        if (max == chess.length - 1 || possibleMaxj == chess[0].length - 1 || chess[max + 1][possibleMaxj + 1] == opponentSide)
            minEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
            minEndOpenOrClosed = MarkAndFlag.OPEN;

        int possibleMinj = j - 1;
        for (int possibleMini = i - 1; possibleMini >= i - 4; possibleMini --)
        {
            if (possibleMini < 0 || possibleMinj < 0)
            {
                min = possibleMini + 1;
                possibleMinj++;
                break;
            }
            if (chess[possibleMini][possibleMinj] != blackOrWhiteSide )
            {
                min = possibleMini + 1;
                possibleMinj++;
                break;
            }

            min = possibleMini;
            possibleMinj--;

        }

        if (min <= 0 || possibleMinj <= 0)
            maxEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
        {
            if ((chess[min - 1][possibleMinj - 1] == opponentSide))
                maxEndOpenOrClosed = MarkAndFlag.CLOSED;
            else
            maxEndOpenOrClosed = MarkAndFlag.OPEN;
        }


        int[] result = new int[]{(max - min + 1),deadOrLive(minEndOpenOrClosed,maxEndOpenOrClosed)};
        return result;

    }



    /*If a stone was placed on this position, how long of a broken row of my side will be formed in NorthEast---SouthWest direction
    *   @para chess, the layout of the chess
    *   @para i and j, the indexes of the position to be evaluated in chess
    *   @return int[], the first int of array is the length of the broken row, the second int of array is the dead or live status of the row.
     */
    public int[] evaluateNorthEastAndSouthWest(int[][] chess, int i, int j)
    {
        int min = 0;
        int max = 0;
        int minEndOpenOrClosed;
        int maxEndOpenOrClosed;

        int possibleMaxj = j - 1;
        for (int possibleMaxi = i + 1; possibleMaxi <= i + 4; possibleMaxi ++)
        {

            if (possibleMaxi >= chess.length  || possibleMaxj < 0)
            {
                max = possibleMaxi - 1;
                possibleMaxj++;
                break;
            }
            if (chess[possibleMaxi][possibleMaxj] != blackOrWhiteSide)
            {
                max = possibleMaxi - 1;
                possibleMaxj++;
                break;
            }
            max = possibleMaxi;
            possibleMaxj--;


        }

        if (max == chess.length - 1 || possibleMaxj == 0 || chess[max + 1][possibleMaxj - 1] == opponentSide)
            minEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
            minEndOpenOrClosed = MarkAndFlag.OPEN;

        int possibleMinj = j + 1;
        for (int possibleMini = i - 1; possibleMini >= i - 4; possibleMini --)
        {
            if (possibleMini < 0 || possibleMinj >= chess[0].length)
            {
                min = possibleMini + 1;
                possibleMinj--;
                break;
            }
            if (chess[possibleMini][possibleMinj] != blackOrWhiteSide )
            {
                min = possibleMini + 1;
                possibleMinj--;
                break;
            }

            min = possibleMini;
            possibleMinj++;

        }

        if (min <= 0 || possibleMinj == chess[0].length - 1 || chess[min - 1][possibleMinj + 1] == opponentSide)
            maxEndOpenOrClosed = MarkAndFlag.CLOSED;
        else
            maxEndOpenOrClosed = MarkAndFlag.OPEN;


        int[] result = new int[]{(max - min + 1),deadOrLive(minEndOpenOrClosed,maxEndOpenOrClosed)};
        return result;

    }

    public int deadOrLive(int minEnd, int maxEnd)
    {
        if (minEnd == MarkAndFlag.OPEN && maxEnd == MarkAndFlag.OPEN)
            return MarkAndFlag.ALIVE;
        else
            if (minEnd == MarkAndFlag.OPEN || maxEnd == MarkAndFlag.OPEN)
                return MarkAndFlag.HALF_ALIVE;
            else
                return MarkAndFlag.DEAD;

    }


    /* Give points to position according to the pattern of layout in the four directions.
    * @para int[] Four int array that store the information of how long is the unbroken row and the dead or alive status of the row.
    * @return int A integer which is the points of the position.
    *
     */
    public int givePoint(int[] eastWest, int[] northSouth, int[] northwestSoutheast, int[] southeastNorthwest)
    {
        int[][] results = new int[][]{eastWest,northSouth,northwestSoutheast,southeastNorthwest};
        for (int[] result : results)
        {
            if (result[0] >= 5)
                return MarkAndFlag.FIVE;
            if (result[0] == 4 && result[1] == MarkAndFlag.ALIVE)
                return MarkAndFlag.OPEN_FOUR;

        }

        int halfOpenFourCount = 0;
        for (int[] result :results)
        {
            if (result[0] == 4 && result[1] == MarkAndFlag.HALF_ALIVE)
                halfOpenFourCount++;


        }
        if (halfOpenFourCount >=2)
            return MarkAndFlag.TWO_HALF_OPEN_FOUR;

        int openThreeCount = 0;
        for (int[] result :results)
        {
            if (result[0] == 3 && result[1] == MarkAndFlag.ALIVE)
                openThreeCount++;


        }

        if (halfOpenFourCount >= 1 && openThreeCount >= 1)
            return MarkAndFlag.HALF_OPEN_FOUR_AND_OPEN_THREE;

        int halfOpenThreeCount = 0;
        for (int[] result :results)
        {
            if (result[0] == 3 && result[1] == MarkAndFlag.HALF_ALIVE)
               halfOpenThreeCount++;
        }

        int openTwoCount = 0;
        for (int[] result :results)
        {
            if (result[0] == 2 && result[1] == MarkAndFlag.ALIVE)
                openTwoCount++;


        }
        int halfOpenTwoCount = 0;
        for (int[] result :results)
        {
            if (result[0] == 2 && result[1] == MarkAndFlag.HALF_ALIVE)
                halfOpenTwoCount++;
        }



        int score = halfOpenFourCount * 100 + openThreeCount * 90 + halfOpenThreeCount * 50 + openTwoCount * 10 + halfOpenTwoCount * 5;
        return score;


    }



}
