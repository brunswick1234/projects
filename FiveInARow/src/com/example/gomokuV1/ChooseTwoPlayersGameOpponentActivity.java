package com.example.gomokuV1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import java.util.ArrayList;

/**
 * Created by dell on 6/16/2014.
 *  ChooseTwoPlayersGameOpponentActivity allow user to choose the human players to compete with in two players game
 */
public class ChooseTwoPlayersGameOpponentActivity extends Activity implements GestureDetector.OnGestureListener
{
    private DatabaseHelper databaseHelper; //DatabaseHelper for the class
    private ArrayList<HumanPlayer> humanPlayers; //All human players in the game
    private ViewFlipper viewFlipper = null;
    private GestureDetector gestureDetector = null;


    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.filpper);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        ArrayList<HumanPlayer> humanPlayersTemp = databaseHelper.getAllHumanPlayer();
        humanPlayers = new ArrayList<HumanPlayer>();

        //Get the player the user currently act as from the intent
        HumanPlayer humanPlayerGet = getIntent().getParcelableExtra("HumanPlayer");

        //Add all human players to the arrayList except the user currently act as
        for (HumanPlayer humanPlayer : humanPlayersTemp)
        {
            if (!humanPlayerGet.getName().equals(humanPlayer.getName()))
                humanPlayers.add(humanPlayer);
        }

        //Add view of each opponent to the viewFlipper
        viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        int i = 0;
        for (HumanPlayer humanPlayer : humanPlayers)
        {
            RelativeLayout relativeLayout = (RelativeLayout)layoutInflater.inflate(R.layout.choosetwoplayersgameopponent,null);

            ImageButton goPreviousButton = (ImageButton) relativeLayout.findViewById(R.id.goPrevious);
            ImageButton goNextButton = (ImageButton) relativeLayout.findViewById(R.id.goNext);
            goPreviousButton.setEnabled(true);
            goPreviousButton.setVisibility(View.VISIBLE);
            goNextButton.setEnabled(true);
            goNextButton.setVisibility(View.VISIBLE);

            if (i == 0)
            {
                //If it's the first page, the goPrevious button will be disabled
                goPreviousButton.setEnabled(false);
                goPreviousButton.setVisibility(View.INVISIBLE);
            }
            if (i == humanPlayers.size() - 1)
            {
                //If it's the first page, the goNext button will be disabled
                goNextButton.setEnabled(false);
                goNextButton.setVisibility(View.INVISIBLE);
            }

            ImageButton imageButton = (ImageButton)relativeLayout.findViewById(R.id.opponentPhotoInChooseTwoPlayersGameOpponent);
            imageButton.setImageResource(humanPlayer.getPhoto());
            TextView textView = (TextView)relativeLayout.findViewById(R.id.opponentProfileInChooseTwoPlayersGameOpponent);
            textView.setText(humanPlayer.getName() + "\n\n" + humanPlayer.getMotto());
            viewFlipper.addView(relativeLayout);
            i++;
        }
        gestureDetector = new GestureDetector(this,this);
    }

    //Go to next page when press goNext button
    public void goToNextOpponent(View view)
    {
        Animation lOutAnim = AnimationUtils.loadAnimation(this, R.anim.pushrighttoleftleftout);
        Animation rInAnim = AnimationUtils.loadAnimation(this, R.anim.pushrighttoleftrightin);
        viewFlipper.setOutAnimation(lOutAnim);
        viewFlipper.setInAnimation(rInAnim);
        viewFlipper.showNext();
    }


    //Go to previous page when press goPrevious button
    public void goToPreviousOpponent(View view)
    {
        Animation rOutAnim = AnimationUtils.loadAnimation(this,R.anim.pushlefttorightrightout);
        Animation lInAnim = AnimationUtils.loadAnimation(this, R.anim.pushlefttorightleftin);
        viewFlipper.setOutAnimation(rOutAnim);
        viewFlipper.setInAnimation(lInAnim);
        viewFlipper.showPrevious();
    }

    //Stat TwoPlayersGameActivity when press one photo of opponent.
    public void startTwoPlayersGame(View view)
    {
        HumanPlayer humanplayer = getIntent().getParcelableExtra("HumanPlayer");
        Intent intent = new Intent(this,TwoPlayerGameActivity.class);
        intent.putExtra("HumanPlayer", humanplayer);
        intent.putExtra("HumanPlayerOpponent",humanPlayers.get(viewFlipper.getDisplayedChild()));
        startActivity(intent);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        if (e2.getX() - e1.getX() > 120 && viewFlipper.getDisplayedChild() != 0)
        {
            //Animation for push from left to right
            Animation rOutAnim = AnimationUtils.loadAnimation(this,R.anim.pushlefttorightrightout);
            Animation lInAnim = AnimationUtils.loadAnimation(this, R.anim.pushlefttorightleftin);
            viewFlipper.setOutAnimation(rOutAnim);
            viewFlipper.setInAnimation(lInAnim);
            viewFlipper.showPrevious();
            return true;
        }
        else
        {
            if (e2.getX() - e1.getX() < -120 && viewFlipper.getDisplayedChild() != humanPlayers.size() - 1)
            {
                //Animation for push from right to left
                Animation lOutAnim = AnimationUtils.loadAnimation(this,R.anim.pushrighttoleftleftout);
                Animation rInAnim = AnimationUtils.loadAnimation(this, R.anim.pushrighttoleftrightin);
                viewFlipper.setOutAnimation(lOutAnim);
                viewFlipper.setInAnimation(rInAnim);
                viewFlipper.showNext();
                return true;
            }

        }
        return true;
    }


}
