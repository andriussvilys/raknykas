package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.*;
import com.mygdx.game.sprites.PlayerSprite;
import inventory.Inventory;
import tools.MapManager;

public class Player extends Stage {

    private PlayerSprite sprite;
    private boolean isMoving;
    private int moveDirection;
    private Inventory inventory;
    MapManager mapManager;

    public Player ( MapManager mapManager ){

        this.mapManager = mapManager;
        sprite = new PlayerSprite(mapManager.getWorld());

        Gdx.input.setInputProcessor(this);

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.ENTER)
                {
                    System.out.println("pressed ENTER -- player");
                    switchLevel();
                }

                if(keycode == Input.Keys.SPACE || checkIfNumKeyPressed(keycode)){
                    handleInventoryInput(keycode);
                }

                else{
                    isMoving = true;
                    moveDirection = keycode;
                }

                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                isMoving = false;
                return super.keyUp(event, keycode);
            }
        });

        inventory = Inventory.getInstance();
        int playerScreenPosition_X = (int)(sprite.rigidBody.getTransform().getPosition().x);
        inventory.setPosition( playerScreenPosition_X, 32);
        addActor(inventory);
    }


    private void handleInventoryInput(int keycode){
//        NUM_KEYS toggle inventory
        if (checkIfNumKeyPressed(keycode)) {
            setKeyboardFocus((Group) inventory);
        }

//else add new item to inventory
//or interact with exitGate
        else if (keycode == Input.Keys.SPACE){
            if(sprite.isIntersecting)
            {
                System.out.println(sprite.intersectionObject.toString());
                sprite.intersectionObject.onInteract();
            }
        }

    }

    @Override
    public void draw() {
        super.draw();;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void update(float delta){
        if(isMoving)
            sprite.update(delta, moveDirection);

        int position_x = (int)(Raknykas.SCREEN_WIDTH / 2 ) + 300 - 96;
        int position_y = (int)(Raknykas.SCREEN_HEIGHT / 2 ) + 20;

        inventory.setPosition( position_x, position_y);
    }

    public PlayerSprite getSprite(){return this.sprite; }

    private boolean checkIfNumKeyPressed(int keycode)
    {
        int[] numKeys = {
                Input.Keys.NUM_1,
                Input.Keys.NUM_2,
                Input.Keys.NUM_3,
                Input.Keys.NUM_4,
                Input.Keys.NUM_5,
                Input.Keys.NUM_6,
                Input.Keys.NUM_7,
                Input.Keys.NUM_8,
                Input.Keys.NUM_9
        };

        for (int i = 0; i < numKeys.length; i++) {
            if(numKeys[i] == keycode)
                return true;
        }

        return false;
    };

    private void switchLevel(){
        mapManager.switchLevel();
//        sprite = new PlayerSprite(mapManager.getWorld());
    }

    public void resetSprite(World world){
        sprite = new PlayerSprite(world);
    }

    public void move(int direction){
        this.moveDirection = direction;
        update(1);
    }

    public void interact(int keycode){
        handleInventoryInput(keycode);
    }



}
