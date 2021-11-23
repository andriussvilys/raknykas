package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import tools.InteractiveTile;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Raknykas;
import com.mygdx.game.screens.PlayScreen;
import tools.WorldContactListener;

public class PlayerSprite extends Sprite{

    private float stepSize = 0.05f;

    private TextureAtlas atlas;

    public World world;
    public Body rigidBody;

    private TextureRegion[] north = new TextureRegion[3];
    private TextureRegion[] east = new TextureRegion[3];
    private TextureRegion[] south = new TextureRegion[3];
    private TextureRegion[] west = new TextureRegion[3];

    private float timer = 0;
    private int spriteCounter = 0;

    public boolean isIntersecting = false;
    public InteractiveTile intersectionObject;

    public PlayerSprite(World world){

        super();

        atlas = new TextureAtlas("./player/zombie.pack");

        setRegion(atlas.findRegion("bloody_zombie-NESW"));

        this.world = world;
        definePlayer();

        makeSpriteSequence(0, 0, north);
        makeSpriteSequence(0, 32, east);
        makeSpriteSequence(0, 64, south);
        makeSpriteSequence(0, 96, west);

        setBounds(0,0, 24 / Raknykas.PPM, 32 / Raknykas.PPM);
        update(0,0);

    }

    public void setWorld(World world){
        this. world = world;
        definePlayer();
    }

    private void makeSpriteSequence(int x, int y, TextureRegion[] dest){
        for (int i = 0; i < 3; ++i) {
            dest[i] = (new TextureRegion(getTexture(), x + (i * 24), y, 24, 32));
        }
    }

    public void update(float delta_time, int direction){

        timer += delta_time;

        if(timer > 0.1)
        {
            timer = 0;
            ++spriteCounter;
            if(spriteCounter > 2)
                spriteCounter = 0;
        }

        moveRigidBody(direction);

        setPosition(rigidBody.getPosition().x - getWidth() / 2, rigidBody.getPosition().y - getHeight() / 2);

        switch (direction){

            case Input.Keys.W: {
                setRegion(north[spriteCounter]);
                break;
            }

            case Input.Keys.S: {
                setRegion(south[spriteCounter]);
                break;
            }

            case Input.Keys.D: {
                setRegion(east[spriteCounter]);
                break;
            }

            case Input.Keys.A: {
                setRegion(west[spriteCounter]);
                break;
            }
            default: {
                setRegion(north[0]);
            }

        }
    }

    private void moveRigidBody(int direction){
        switch (direction){

            case Input.Keys.W: {

                Vector2 position = rigidBody.getTransform().getPosition();
                position.y += stepSize;
                rigidBody.setTransform(position, (float)(90f * DEGREES_TO_RADIANS));

                break;
            }

            case Input.Keys.S: {
                Vector2 position = rigidBody.getTransform().getPosition();
                position.y += -stepSize;
                rigidBody.setTransform(position, (float)(270 * DEGREES_TO_RADIANS));
                break;
            }

            case Input.Keys.D: {
                Vector2 position = rigidBody.getTransform().getPosition();
                position.x += stepSize;
                rigidBody.setTransform(position, (float)(0f * DEGREES_TO_RADIANS));
                break;
            }

            case Input.Keys.A: {
                Vector2 position = rigidBody.getTransform().getPosition();
                position.x += -stepSize;
                rigidBody.setTransform(position, (float)(180f * DEGREES_TO_RADIANS));
                break;
            }
            default: {
                break;
            }

        }
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(0 / Raknykas.PPM, 64 / Raknykas.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        rigidBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape  shape = new CircleShape();
        shape.setRadius(6 / Raknykas.PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = Raknykas.PLAYER_BIT;
        //this sets what player can collide with
        fdef.filter.maskBits = Raknykas.DEFAULT_BIT | Raknykas.KEY_BIT | Raknykas.LOCK_BIT | Raknykas.ENEMY_BIT;
        rigidBody.createFixture(fdef);

        EdgeShape head = new EdgeShape();
//        head.set(new Vector2(6 / Raknykas.PPM, -7 / Raknykas.PPM), new Vector2(6 / Raknykas.PPM, 7 / Raknykas.PPM));
        head.set(new Vector2(5 / Raknykas.PPM, 0 / Raknykas.PPM), new Vector2(20 / Raknykas.PPM, 0 / Raknykas.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        rigidBody.createFixture(fdef).setUserData("Player");

        world.setContactListener(new WorldContactListener( this ) );

    }

    private static final double DEGREES_TO_RADIANS = (Math.PI/180);


}
