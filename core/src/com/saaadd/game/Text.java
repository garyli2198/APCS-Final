package com.saaadd.game;

/**
 * Created by Gary on 5/12/2016.
 */
public class Text {
    public String gameIntro = "Hello, and welcome to the\n" +
            "Super Amazing Awesome Adventures of" +
            "\nDylan Dickies!\n" +
            "(Press N to continue or ESC to quit)";
    public String gameIntro2 = "Walk around with W,A,S,D and shoot with Left Click\n" +
            "(Press N to continue or ESC to quit)";

    public String gameIntro3 = "Your name is Dylan Dickies.\n You are the biggest Cuck in the universe.\n" +
            "Try to survive as long as possible. Good Luck!\n" + "(Press N to continue or ESC to quit)";
    public Text()
    {

    }
    public String getGameIntro()
    {
        return gameIntro;
    }

    public String getGameIntro2()
    {
        return gameIntro2;
    }

    public String getGameIntro3()
    {
        return gameIntro3;
    }
}
