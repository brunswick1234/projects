package com.example.gomokuV1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dell on 6/6/2014.
 * MainActivity displays the main menu of the application, and user can go to different activities by choose different buttons on this activity.
 */


public class MainActivity extends Activity
{
    private DatabaseHelper databaseHelper; //DatabaseHelper for the activity
    private HumanPlayer currentPlayer = null; //The player which user currently act as in the gaame.

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        //sharedPreferences.edit().clear().commit();
        String currentPlayerName = sharedPreferences.getString("currentPlayerName",null); //Get the name of the player that user act as last time.

        databaseHelper = new DatabaseHelper(getApplicationContext());

        if (currentPlayerName == null || databaseHelper.getHumanPlayer(currentPlayerName) == null)
        {
            //If no current player
            TextView textView = (TextView)findViewById(R.id.nameInMain);
            textView.setText("");
            ImageButton imageButton = (ImageButton)findViewById(R.id.photoInMain);
            imageButton.setImageResource(R.drawable.defaultavatar);
            databaseHelper.onUpgrade(this.databaseHelper.getReadableDatabase(),1,2);
            AIPlayer homer = new AIPlayer("Homer", "HaHa",R.drawable.homer);
            AIPlayer bart = new AIPlayer("Bart","I will destroy you",R.drawable.bart);
            AIPlayer lisa = new AIPlayer("Lisa","You can't win", R.drawable.lisa);
            databaseHelper.addPlayer(homer);
            databaseHelper.addPlayer(bart);
            databaseHelper.addPlayer(lisa);
        }
        else
        {
            //Reload of the information about the player the user last choose
            currentPlayer = databaseHelper.getHumanPlayer(currentPlayerName);
            TextView textViewName = (TextView)findViewById(R.id.nameInMain);
            textViewName.setText(currentPlayer.getName());
            TextView textViewMotto = (TextView)findViewById(R.id.mottoInMain);
            textViewMotto.setText(currentPlayer.getMotto());
            int[] resultCount = databaseHelper.getPlayerMatchResult(currentPlayer.getName());
            TextView textViewScore = (TextView)findViewById(R.id.scoreInMain);
            textViewScore.setText("Win:  " + resultCount[1] + "    Lost:  " + (resultCount[0] - resultCount[1]) +
                                    "\nTotal Score:  " + currentPlayer.getScore());
            ImageButton imageButton = (ImageButton)findViewById(R.id.photoInMain);
            imageButton.setImageResource(currentPlayer.getPhoto());

        }



    }

    //Go to ChoosePlayerActivity when press the photo button
    public void choosePlayer(View view)
    {
        Intent intent = new Intent(this,ChoosePlayerActivity.class);
        intent.putExtra("HumanPlayer",currentPlayer);
        startActivityForResult(intent, 3);

    }

    //Handle the result from ChoosePlayerActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);
        if (requestCode == 3)
       {
            if (resultCode == RESULT_OK)
            {
                //Display the information of the player the user choose in MainActivity
                currentPlayer= intent.getParcelableExtra("player");

                TextView textViewName = (TextView)findViewById(R.id.nameInMain);
                textViewName.setText(currentPlayer.getName());

                TextView textViewMotto = (TextView)findViewById(R.id.mottoInMain);
                textViewMotto.setText(currentPlayer.getMotto());

                int[] resultCount = databaseHelper.getPlayerMatchResult(currentPlayer.getName());
                TextView textViewScore = (TextView)findViewById(R.id.scoreInMain);
                textViewScore.setText("Win:  " + resultCount[1] + "    Lost:  " + (resultCount[0] - resultCount[1]) +
                                        "\nTotal Score:  " + currentPlayer.getScore());

                ImageButton imageButton = (ImageButton)findViewById(R.id.photoInMain);
                imageButton.setImageResource(currentPlayer.getPhoto());

            }
        }
    }


    public void singlePlayer(View view)
    {
        if (currentPlayer == null)
        {
            //Display the message whn user try to play single game without choosing a player.
            Toast.makeText(getApplicationContext(), "Create a player for yourself first", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Start SingleGameActivity
            Intent intent = new Intent(this, ChooseSingleGameOpponentActivity.class);
            intent.putExtra("HumanPlayer", currentPlayer);
            startActivity(intent);
        }
    }

    public void twoPlayer(View view)
    {
        if (currentPlayer == null)
        {
            //Display the message whn user try to play two players game without choosing a player.
            Toast.makeText(getApplicationContext(), "Create a player for yourself first", Toast.LENGTH_SHORT).show();
        }

        if (databaseHelper.getAllHumanPlayer().size() < 2)
        {
            //Display message when user try to play two players game while there is less than two human player created in the application.
            Toast.makeText(getApplicationContext(), "Please create at least two players first", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ////Start TwoPlayersGameActivity
            Intent intent = new Intent(this, ChooseTwoPlayersGameOpponentActivity.class);
            intent.putExtra("HumanPlayer", currentPlayer);
            startActivity(intent);
        }
    }



    //Start StatisticsActivity
    public void statistics(View view)
    {
        Intent intent = new Intent(this, RankTabActivity.class);
        intent.putExtra("HumanPlayer", currentPlayer);
        startActivity(intent);
    }



    //Store the name of the player the user current act as in onStop method
    @Override
    public void onStop()
    {
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String name;
        if (currentPlayer == null)
            name = null;
        else
            name = currentPlayer.getName();
        editor.putString("currentPlayerName",name);
        editor.commit();
    }
}
