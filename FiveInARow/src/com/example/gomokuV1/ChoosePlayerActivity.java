package com.example.gomokuV1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;
import java.util.*;
import android.view.ViewGroup;

/**
 * Created by dell on 6/6/2014.
 * ChoosePlayerActivity provides a list of players where you can choose the player you want to act as in the game.
 * In also has buttons which link to AddOrEditPlayerActivity.
 */
public class ChoosePlayerActivity extends Activity
{
    private ArrayList<HumanPlayer> humanPlayers;   //All human players in the game
    private HumanPlayer humanPlayer;  //The current human player the user choose
    private DatabaseHelper databaseHelper;
    private ListView playerList; // The ListView for display all human players
    private int selectedIndex = 0; // The index of the player the user choose in the playerList
    private PlayerAdapter playerAdapter; //The adapter for the playerList


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseplayer);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        humanPlayers = new ArrayList<HumanPlayer>(databaseHelper.getAllHumanPlayer());
        humanPlayer = getIntent().getParcelableExtra("HumanPlayer");
        playerList = (ListView)findViewById(R.id.playerList);
        playerAdapter = new PlayerAdapter();
        playerList.setAdapter(playerAdapter);

        //Set focus to the player the user currently act as
        if (humanPlayer != null)
        {
            selectedIndex = findIndex(humanPlayer, humanPlayers);
            playerList.setSelection(selectedIndex);
        }
        else
        {
            selectedIndex = -1;
        }


        //
        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                humanPlayer = (HumanPlayer)playerList.getAdapter().getItem(i);
                selectedIndex = i;
                playerAdapter.notifyDataSetChanged();

            }
        });


    }

    //Method to start AddOrEditPlayer to add player
    public void addPlayer(View view)
    {
        Intent intent = new Intent(this,AddOrEditPlayerActivity.class);
        startActivityForResult(intent,2);

    }


    //Method to start AddOrEditPlayer to edit player
    public void editPlayer(View view)
    {
        if (humanPlayer == null)
            Toast.makeText(getApplicationContext(), "Please choose the player for editing", Toast.LENGTH_SHORT).show();
        else
        {
            Intent intent = new Intent(this, AddOrEditPlayerActivity.class);
            intent.putExtra("PlayerForEditing", humanPlayer);
            startActivityForResult(intent, 3);
        }
    }


    //Handle the result from AddOeEditPlayer
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 2 || requestCode == 3)
            if (resultCode == RESULT_OK)
            {
                humanPlayers = databaseHelper.getAllHumanPlayer();
                humanPlayer = data.getParcelableExtra("player");
                selectedIndex = findIndex(humanPlayer,humanPlayers);
                playerAdapter = new PlayerAdapter();
                playerList.setAdapter(playerAdapter);
                playerList.setSelection(selectedIndex);

            }

    }


    //Adapter class for playerList
    class PlayerAdapter extends ArrayAdapter<HumanPlayer>
    {
        public PlayerAdapter()
        {
            super(ChoosePlayerActivity.this,R.layout.playerrow,R.id.playerNameChoosePlayerActivity, humanPlayers);
        }

        public View getView(int index, View convertView, ViewGroup parent)
        {
            View row = super.getView(index,convertView,parent);
            ImageView imageView = (ImageView)row.findViewById(R.id.playerPhotoChoosePlayerActivity);
            imageView.setImageResource(humanPlayers.get(index).getPhoto());
            TextView textView = (TextView)row.findViewById(R.id.playerNameChoosePlayerActivity);
            textView.setText("   " + humanPlayers.get(index).getName());
            TextView textView1 = (TextView)row.findViewById(R.id.mottotextfield);
            textView1.setText("    " + humanPlayers.get(index).getMotto());
            if (index == selectedIndex)
            {
                row.setBackgroundResource(R.drawable.outline);
            }
            else
                row.setBackground(null);
            return (row);
        }


    }



    //Press the confirm button to confirm the player your choose and back to MainActivity
    public void choosePlayer(View view)
    {
        if (humanPlayer == null)
        {
            Toast.makeText(getApplicationContext(), "Choose a player first.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent();
            intent.putExtra("player", humanPlayer);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    //Find the index of a player in array of players
    public int findIndex(HumanPlayer player,ArrayList<HumanPlayer> players)
    {
        int i = 0;
        for (HumanPlayer player1 : players)
        {
            if (player1.getName().equals(player.getName()))
                return i;
            i++;
        }
        return -1;

    }








}
