package com.saaadd.character;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

import java.util.ArrayList;
import java.util.List;

import static com.saaadd.game.GameScreen.player;

public class CharacterRenderer {
    private List<Character> characterList;

    /**
     * Constructs a CharacterRenderer
     */
    public CharacterRenderer() {
        characterList = new ArrayList<Character>();
    }

    public void renderCharacters(SpriteBatch batch) {
        for (int i = 0; i < characterList.size(); i++) {
            if (characterList.get(i).shouldRemove()) {
                characterList.remove(i);
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
        characterList.add(new Enemy(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")),
                (float) (player.getX() + Math.random() * 500), (float) (player.getY() + Math.random() * 500), 0, 100, new Weapon(1, "pistol", new Texture(Gdx.files.internal("weapons/1h_pistol.png")), Weapon.oneH, 0.3f, 10)));
    }

    public void add(Character character) {
        characterList.add(character);
    }

    public void remove(Character character) {
        characterList.remove(character);
    }

    public int getCharacterListSize() { return  characterList.size(); }
}
