package com.example.gomokuV1;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;


/**
 * Created by dell on 6/7/2014.
 * ChooseSingleGameOpponentActivity allow user to choose the ai to compete with in single game
 */
public class ChooseSingleGameOpponentActivity extends Activity implements GestureDetector.OnGestureListener
{

    private DatabaseHelper databaseHelper; //database helper for the class
    private AIPlayer[] ais; // array that stores all Ais
    private ViewFlipper viewFlipper = null;
    private GestureDetector gestureDetector = null;


    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.filpper);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        //Create the Ai
        AIPlayer homer = databaseHelper.getAIPlayer("Homer");
        AIPlayer bart = databaseHelper.getAIPlayer("Bart");
        AIPlayer lisa = databaseHelper.getAIPlayer("Lisa");
        ais = new AIPlayer[3];
        ais[0] = homer;
        ais[1] = bart;
        ais[2] = lisa;


        viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);

        //Add view for each Ai to the viewFlipper
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        int i = 0; //Index of page in viewFlipper
        for (AIPlayer ai : ais)
        {
            RelativeLayout relativeLayout = (RelativeLayout)layoutInflater.inflate(R.layout.choosesinglegameopponent,null);
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
            if (i == ais.length - 1)
            {
                //If it's the first page, the goNext button will be disabled
                goNextButton.setEnabled(false);
                goNextButton.setVisibility(View.INVISIBLE);
            }
            ImageButton imageButton = (ImageButton)relativeLayout.findViewById(R.id.opponentPhotoInChooseSingleGameOpponent);
            imageButton.setImageResource(ai.getPhoto());
            TextView textView = (TextView)relativeLayout.findViewById(R.id.opponentProfileInChooseSingleGameOpponent);
            textView.setText(ai.getName() + "\n\n" + ai.getMotto());
            viewFlipper.addView(relativeLayout);
            i++;
        }
        gestureDetector = new GestureDetector(this,this);
    }

    //Go to next page when press goNext button
    public void goToNextOpponent(View view)
    {
        Animation lOutAnim = AnimationUtils.loadAnimation(this,R.anim.pushrighttoleftleftout);
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


    //Stat SingleGameActivity when press one photo of ai.
    public void startSingleGame(View view)
    {
        HumanPlayer humanplayer = getIntent().getParcelableExtra("HumanPlayer");
        Intent intent = new Intent(this,SingleGameActivity.class);
        intent.putExtra("HumanPlayer", humanplayer);
        intent.putExtra("AIPlayer",ais[viewFlipper.getDisplayedChild()]);

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
            if (e2.getX() - e1.getX() < -120 && viewFlipper.getDisplayedChild() != ais.length - 1)
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
