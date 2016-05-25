package com.saaadd.game;

import com.badlogic.gdx.Game;
import com.saaadd.character.Character;
import com.saaadd.character.Enemy;

/**
 * wave class that includes mechanics for each round and enemy spawning mechanics
 * @author Stanley Huang
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Gary Li, Wesley Pang
 */
public class Wave {
    private int round;
    private int enemyCount;
    private boolean isDone;
    private boolean hasStarted;

    /**
     * constructs a wave
     * @param round round number
     */
    public Wave(int round){
        this.round = round;
        enemyCount = 0;
        isDone = false;
        hasStarted = false;
    }

    /**
     * starts the round
     */
    public void start(){
        hasStarted = true;
        for(int i = 0; i < round; i++){
            GameScreen.characterRenderer.spawnEnemy();
        }
    }

    /**
     * updates the wave round; checks num of enemies
     */
    public void update(){
        enemyCount = 0;
        for(Character c : GameScreen.characterRenderer.getCharacterList()){
            if(c instanceof Enemy){
                enemyCount++;
            }
        }
        isDone = enemyCount == 0;
    }

    /**
     * @return if round is finished
     */
    public boolean isDone(){
        return isDone;
    }

    /**
     * @return round number
     */
    public int getRound(){
        if(!hasStarted()){
            return round - 1;
        }
        return round;
    }

    /**
     * starts next round
     * @return new wave with round incremented
     */
    public Wave nextWave(){
        return new Wave(round + 1);
    }

    /**
     * @return if new wave has started
     */
    public boolean hasStarted(){
        return hasStarted;
    }


}
