package com.example.gomokuV1;

/**
 * Created by dell on 6/7/2014.
 * HumanPlayer class stands for the human player which human choose to player as in the game.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class HumanPlayer extends Player implements Parcelable
{

    public HumanPlayer(String name,String motto,int photo, int score)
    {
        super(name,motto,photo,score);
    }

    public static final Creator CREATOR = new Creator()
    {
        public HumanPlayer createFromParcel(Parcel in)
        {
            return new HumanPlayer(in);
        }

        public HumanPlayer[] newArray(int size)
        {
            return new HumanPlayer[size];
        }
    };

    public HumanPlayer(Parcel in)
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
    public int describeContents()
    {
        return 0;
    }
}
