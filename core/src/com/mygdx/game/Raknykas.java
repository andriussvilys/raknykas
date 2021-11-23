package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.PlayScreen;

public class Raknykas extends Game {
    public static SpriteBatch batch;
    //pixels per meter
    public static final int SCREEN_WIDTH = 320;
    public static final int SCREEN_HEIGHT = 320;
    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short KEY_BIT = 4;
    public static final short LOCK_BIT = 8;
    public static final short ENEMY_BIT = 32;
    public static final short DESTROYED_BIT = 16;

    public static final short BG_LAYER = 0;
    public static final short GFX_LAYER = 1;
    public static final short FIRE_LAYER = 2;
    public static final short WALL_LAYER = 3;
    public static final short LOCK_LAYER = 4;
    public static final short KEY_LAYER = 5;
    public static final short ENEMY_LAYER = 6;
    public static final short EXIT_LAYER = 7;

    @Override
    public void create(){
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
