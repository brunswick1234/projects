package com.example.gomokuV1;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by dell on 6/6/2014.
 * Player class stands for the player in this game, and it has two subclass AiPlayer and HumanPlayer
 *
 */
public class Player implements Parcelable
{
    public static final String TABLE_NAME = "Players";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MOTTO = "motto";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_SCORE = "score";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_NAME + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_MOTTO + " TEXT NOT NULL, " +
                    COLUMN_PHOTO + " INTEGER NOT NULL, " +
                    COLUMN_SCORE + " INTEGER NOT NULL" +
                    ")";



    protected String name;  //Name of the player.
    protected String motto; //Motto of the player. Some instruction about the player.
    protected int photo;  //Photo of the player, using the int of R.drawable.photo to store the photo
    protected int score;  //The score of the player.
    protected int blackOrWhiteSide = 0; //Is black side or white side in a game. MarkAndFlag.BLACK or MarkAndFlag.WHITE


    public static final Creator CREATOR = new Creator()
    {
        public Player createFromParcel(Parcel in)
        {
            return new Player(in);
        }

        public Player[] newArray(int size)
        {
            return new Player[size];
        }
    };


    public Player(String name, String motto, int photo, int score)
    {
        this.name = name;
        this.motto = motto;
        this.photo = photo;
        this.score = score;

    }


    public Player(Parcel in)
    {
        this.name = in.readString();
        this.motto = in.readString();
        this.photo = in.readInt();
        this.score = in.readInt();
    }

    public String getName()
    {
        return name;
    }

    public String getMotto()
    {
        return motto;
    }


    public int getPhoto()
    {
        return photo;
    }

    public int getScore()
    {
        return score;
    }

    @Override
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

    public void setBlackOrWhiteSide(int blackOrWhiteSide)
    {
        this.blackOrWhiteSide = blackOrWhiteSide;
    }

    public int getBlackOrWhiteSide()
    {
        return blackOrWhiteSide;
    }

    public void setMotto(String motto)
    {
        this.motto = motto;
    }

    public void setPhoto(int photo)
    {
        this.photo = photo;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    

}
