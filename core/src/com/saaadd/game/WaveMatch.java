package com.saaadd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.saaadd.character.Character;
import com.saaadd.character.CharacterRenderer;

import static com.saaadd.game.GameScreen.player;

/**
 * Created by Gary on 5/13/2016.
 */
public class WaveMatch extends CharacterRenderer {
    private int difficulty;
    private int money, round;

    public WaveMatch() {
        money = 0;
        round = 0;
    }

    public void start() {
        if (player.getHealth() > 0 && getCharacterListSize() == 0 && Gdx.input.isKeyPressed(Input.Keys.N) || round == 0) {
            round++;
            for (int i = 0; i < round * 1.5; i++) {
                spawnEnemy();
            }

        }
    }


    public void money()
    {
        for(Character c : getCharacterList()) {
            if(c.shouldRemove()) {
                money += 10;
            }
        }
    }

    public int getMoney() {
        return money;
    }

    public int getRound() {
        return round;
    }
}
