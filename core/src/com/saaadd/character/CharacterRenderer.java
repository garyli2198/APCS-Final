package com.saaadd.character;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saaadd.item.Bullet;

import java.util.ArrayList;
import java.util.List;

public class CharacterRenderer {
    private List<Character> characterList;

    /**
     * Constructs a CharacterRenderer
     */
    public CharacterRenderer(){
        characterList = new ArrayList<Character>();
    }
    public void renderCharacters(SpriteBatch batch){
        for(int i = 0; i < characterList.size(); i++){
            if(characterList.get(i).shouldRemove()){
                characterList.remove(i);
            }
            else{
                characterList.get(i).update();
                Bullet b = characterList.get(i).hitByBullet();
                if(b != null){
                    characterList.get(i).onHit(b);
                }
                characterList.get(i).draw(batch);
            }
        }
    }
    public void add(Character character){
        characterList.add(character);
    }
    public void remove(Character character){
        characterList.remove(character);
    }
}
