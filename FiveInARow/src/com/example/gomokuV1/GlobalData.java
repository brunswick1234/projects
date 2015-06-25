package com.example.gomokuV1;

/**
 * Created by dell on 6/8/2014.
 * GlobalData class store the width and height of the screen, which will help other class to decide the size of some view in layout.
 */
public class GlobalData
{
    public static int WIDTH;   //Width of the screen
    public static int HEIGHT;  //Height of the screen


    public static void setWIDTH(int width)
    {
        WIDTH = width;
    }

    public static void setHEIGHT(int height)
    {
        HEIGHT = height;
    }
}
