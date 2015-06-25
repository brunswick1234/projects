package com.example.gomokuV1;

/**
 * Created by dell on 6/6/2014.
 * AddOrEditPlayerActivity allow you to add a player to the game, or edit your player's photo and motto.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;



public class AddOrEditPlayerActivity extends Activity
{
    private int pictureIndex;
    private DatabaseHelper databaseHelper;
    private HumanPlayer humanPlayer;
    private boolean addOrEdit = false; //true for adding player, false for editing player


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplayer);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        //If no player is passed from the intent, then the user try to add player,
        //otherwise the user try to edit the player passed by intent.
        if(getIntent().getParcelableExtra("PlayerForEditing") == null)
        {
            //Set the view for adding player
            addOrEdit = true;
            pictureIndex = R.drawable.defaultavatar;
            EditText nameText = (EditText)findViewById(R.id.nameOfNewPlayer);
            nameText.requestFocus();
        }
        else
        {
            //Set the view for editing player
            addOrEdit = false;
            humanPlayer = getIntent().getParcelableExtra("PlayerForEditing");
            pictureIndex = humanPlayer.getPhoto();
            ImageButton imageButton = (ImageButton)findViewById(R.id.addPhotoButton);
            imageButton.setImageResource(humanPlayer.getPhoto());
            EditText nameText = (EditText)findViewById(R.id.nameOfNewPlayer);
            nameText.setText(humanPlayer.getName());
            nameText.setEnabled(false); //Name cannot be edited
            EditText mottoText = (EditText)findViewById(R.id.mottoOfNewPlayer);
            mottoText.setText(humanPlayer.getMotto());


        }

    }


    //Press the ImageButton to start ChoosePhotoActivity
    public void choosePhoto(View view)
    {
        Intent intent = new Intent(this,ChoosePhotoActivity.class);
        startActivityForResult(intent, 1);
    }


    //Confirm the change and go back to ChoosePlayerActivity
    public void confirmAddPlayerOrEditPlayer(View view)
    {


        EditText mottoText = (EditText)findViewById(R.id.mottoOfNewPlayer);
        String motto = mottoText.getText().toString();

        if (addOrEdit)
        {
            //If the user added a player
            EditText nameText = (EditText)findViewById(R.id.nameOfNewPlayer);
            String name = nameText.getText().toString();


            if (pictureIndex == R.drawable.defaultavatar)
                Toast.makeText(getApplicationContext(), "Please choose a picture", Toast.LENGTH_SHORT).show(); //If user didn't choose the photo
            else
            {
                if (name.equals(""))
                    Toast.makeText(getApplicationContext(), "The name cannot be blank", Toast.LENGTH_SHORT).show(); //If the user didn't type name
                else
                {
                    if (databaseHelper.getPlayer(name) != null)
                    {
                        //If the name is already be chosen
                        Toast.makeText(getApplicationContext(), "This name has already been choose, please pick another one", Toast.LENGTH_SHORT).show();
                        nameText.setText("");
                    } else
                    {

                        humanPlayer = new HumanPlayer(name, motto, pictureIndex, 0);
                        databaseHelper.addPlayer(humanPlayer);
                        Intent intent = new Intent();
                        intent.putExtra("player", humanPlayer);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        }
        else
        {
            //If the user edit a player.
            humanPlayer.setMotto(motto);
            humanPlayer.setPhoto(pictureIndex);
            databaseHelper.editPlayer(humanPlayer);
            Intent intent = new Intent();
            intent.putExtra("player",humanPlayer);
            setResult(RESULT_OK, intent);
            finish();
        }

    }


    //Press the red cross button to clear the fields
    public void clearAddPlayer(View view)
    {
        if (addOrEdit)
        {
            EditText nameText = (EditText) findViewById(R.id.nameOfNewPlayer);
            nameText.setText("");
            EditText mottoText = (EditText) findViewById(R.id.mottoOfNewPlayer);
            mottoText.setText("");
        }
        else
        {
            ImageButton imageButton = (ImageButton)findViewById(R.id.addPhotoButton);
            imageButton.setImageResource(humanPlayer.getPhoto());
            EditText mottoText = (EditText) findViewById(R.id.mottoOfNewPlayer);
            mottoText.setText(humanPlayer.getMotto());
        }

    }

    //Hand the result from choosePhoto Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                pictureIndex = data.getIntExtra("photoIndex", R.drawable.defaultavatar);
                ImageView imageView = (ImageView)findViewById(R.id.addPhotoButton);
                imageView.setImageResource(pictureIndex);

            }
        }

    }




}
