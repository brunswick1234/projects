package com.example.gomokuV1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by dell on 6/17/2014.
 * Display the ranking of the players
 */
public class RankTabActivity extends Activity
{
    private ArrayList<HumanPlayer> humanPlayers;
    private DatabaseHelper databaseHelper;
    private ListView rankList;
    private RankAdapter rankAdapter;
    private ArrayList<HumanPlayer> humanPlayersSorted;




    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranktab);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        humanPlayers = databaseHelper.getAllHumanPlayer();
        humanPlayersSorted = new ArrayList<HumanPlayer>();
        HumanPlayer humanPlayerHighest;
        while (humanPlayers.size() != 0)
        {
            humanPlayerHighest = humanPlayers.get(0);
            for (HumanPlayer hp : humanPlayers)
            {
                if (hp.getScore() > humanPlayerHighest.getScore())
                {
                    humanPlayerHighest = hp;

                }
            }
            humanPlayersSorted.add(humanPlayerHighest);
            humanPlayers.remove(humanPlayerHighest);
        }
        rankList = (ListView)findViewById(R.id.rankList);
        rankList.setAdapter(new RankAdapter());







    }





    class RankAdapter extends ArrayAdapter<HumanPlayer>
    {
        public RankAdapter()
        {
            super(RankTabActivity.this,R.layout.rankrow,R.id.nameInRankTab, humanPlayersSorted);
        }

        public View getView(int index, View convertView, ViewGroup parent)
        {
            View row = super.getView(index,convertView,parent);
            ImageView imageView = (ImageView)row.findViewById(R.id.photoInRankTab);
            imageView.setImageResource(humanPlayersSorted.get(index).getPhoto());
            TextView textView = (TextView)row.findViewById(R.id.nameInRankTab);
            textView.setText("   " + humanPlayersSorted.get(index).getName());
            TextView textView1 = (TextView)row.findViewById(R.id.scoreInRankTab);
            textView1.setText("    Total Score:" + humanPlayersSorted.get(index).getScore());
            TextView textView2 = (TextView)row.findViewById(R.id.RankingInRankTab);
            textView2.setText(" " + (index + 1) + ".");
            return (row);
        }


    }

}
