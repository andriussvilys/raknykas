package tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.sprites.PlayerSprite;

public class WorldContactListener implements ContactListener {

    private PlayerSprite playerSprite;

    public WorldContactListener(PlayerSprite playerSprite){
        this.playerSprite = playerSprite;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        Fixture player_fixture;
        Fixture collisionObject;

        if(fixA.getUserData() == "Player" || fixB.getUserData() == "Player"){
            player_fixture = fixA.getUserData() == "Player" ? fixA : fixB;
            collisionObject = fixA == player_fixture ? fixB : fixA;

            playerSprite.isIntersecting = true;
            playerSprite.intersectionObject = (InteractiveTile) collisionObject.getUserData();

        }
    }

    @Override
    public void endContact(Contact contact) {
        playerSprite.isIntersecting = false;
        playerSprite.intersectionObject = null;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
