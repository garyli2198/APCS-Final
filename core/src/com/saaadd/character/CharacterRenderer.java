package com.saaadd.character;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.saaadd.game.GameScreen.player;
/**
 * A Character Renderer stores all characters in a list, and iterates through the list and renders and updates
 * each character one by one.
 * @author Stanley Huang
 * @version April 24, 2016
 * @author Period - 6
 * @author Assignment - APCS Final
 * @author Sources - Gary Li, Wesley Pang
 */
public class CharacterRenderer {
    private List<Character> characterList;
    private int posneg;
    private Random rand;

    /**
     * Constructs a CharacterRenderer
     */
    public CharacterRenderer() {
        characterList = new ArrayList<Character>();
        rand = new Random();
    }

    /**
     * renders Characters by iterating through the list and calling each characters update and draw methods
     * also removes dead characters and adds money to player for each dead enemy.
     * then checks bullet hits on characters
     * @param batch = spritebatch used for drawing characters
     */
    public void renderCharacters(SpriteBatch batch) {
        for (int i = 0; i < characterList.size(); i++) {
            if (characterList.get(i).shouldRemove()) {
                Character c = characterList.remove(i);
                if(c instanceof  Enemy){
                    GameScreen.player.addMoney(10);
                }
            } else {
                characterList.get(i).update();
                Bullet b = characterList.get(i).hitByBullet();
                if (b != null) {
                    characterList.get(i).onHit(b);
                }
                characterList.get(i).draw(batch);
            }
        }

    }

    /**
     * spawns an enemy in a random position close to the player
     */
    public void spawnEnemy() {
        //never spawns outside border
        characterList.add(new Enemy(Enemy.enemyLegs, Enemy.enemyBody,
                randXpos(), randYpos(), 0, 50, Weapon.copyOf(Weapon.weapons.get("pistol"))));
    }

    private float randXpos()
    {
        if(Math.random()<0.5) {
            posneg = -1;
        }
        else{
            posneg = 1;
        }
        float x = player.getX() + ((rand.nextInt(500) + 500)*posneg);
        while (x < 40) {
            x++;
        }
        while (x > 3150) {
            x--;
        }

        return x;
    }

    private float randYpos()
    {
        if(Math.random()<0.5) {
            posneg = -1;
        }
        else{
            posneg = 1;
        }
        float y = player.getY() + ((rand.nextInt(500) + 500)*posneg) ;
        while (y < 40) {
            y++;
        }
        while (y > 3140) {
            y--;
        }
        return y;
    }

    /**
     * adds character to rendering list
     * @param character = character to be added
     */
    public void add(Character character) {
        characterList.add(character);
    }

    /**
     * returns the list of the renderer's list
     * @return character list
     */
    public List<Character> getCharacterList()
    {
        return characterList;
    }

}
