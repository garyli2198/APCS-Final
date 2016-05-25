package com.saaadd.character;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.saaadd.game.GameScreen.player;

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

    public void renderCharacters(SpriteBatch batch) {
        for (int i = 0; i < characterList.size(); i++) {
            if (characterList.get(i).shouldRemove()) {
                Character c = characterList.remove(i);
                if(c instanceof  Enemy){
                    GameScreen.player.addMoney(1000);
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

    public void spawnEnemy() {
        //never spawns outside border
        characterList.add(new Enemy(Enemy.enemyLegs, Enemy.enemyBody,
                randXpos(), randYpos(), 0, 50, Weapon.copyOf(Weapon.weapons.get("pistol"))));
    }

    public float randXpos()
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

    public float randYpos()
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

    public void add(Character character) {
        characterList.add(character);
    }

    public List<Character> getCharacterList()
    {
        return characterList;
    }

}
