package com.saaadd.game;

import java.util.ArrayList;

/**
 * Created by Gary on 5/12/2016.
 */
public class Text {
    public String gameIntro = "Hello, and welcome to the\n" +
            "Super Amazing Awesome Adventures of" +
            "\nDylan Dickless!\n" +
            "(Press N to continue or ESC to quit)";
    public String gameIntro2 = "Walk around with W,A,S,D and shoot with Left Click\n" +
            "(Press N to continue or ESC to quit)";

    public String gameIntro3 = "Your name is Dylan Dickless.\n You are the biggest Cuckold in the universe.\n" +
            "Try to survive as long as possible. Good Luck!\n" + "(Press N to continue or ESC to quit)";
    public Text()
    {

    }
    public ArrayList<String> addText(ArrayList<String> list)
    {
        list.add(gameIntro);
        list.add(gameIntro2);
        list.add(gameIntro3);

        return list;
    }
}
