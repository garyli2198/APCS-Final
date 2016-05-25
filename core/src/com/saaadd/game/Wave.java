package com.saaadd.game;

import com.badlogic.gdx.Game;
import com.saaadd.character.Character;
import com.saaadd.character.Enemy;

/**
 * Created by stanl on 5/15/2016.
 */
public class Wave {
    private int round;
    private int enemyCount;
    private boolean isDone;
    private boolean hasStarted;
    public Wave(int round){
        this.round = round;
        enemyCount = 0;
        isDone = false;
        hasStarted = false;
    }

    public void start(){
        hasStarted = true;
        for(int i = 0; i < round; i++){
            GameScreen.characterRenderer.spawnEnemy();
        }
    }

    public void update(){
        enemyCount = 0;
        for(Character c : GameScreen.characterRenderer.getCharacterList()){
            if(c instanceof Enemy){
                enemyCount++;
            }
        }
        isDone = enemyCount == 0;
    }

    public boolean isDone(){
        return isDone;
    }
    public int getRound(){
        if(!hasStarted()){
            return round - 1;
        }
        return round;
    }
    public Wave nextWave(){
        return new Wave(round + 1);
    }
    public boolean hasStarted(){
        return hasStarted;
    }


}
