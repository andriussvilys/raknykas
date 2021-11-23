package com.mygdx.game.screens;

import com.mygdx.game.Player;
import textInterface.TextInterface;
import tools.MapManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Raknykas;


public class PlayScreen implements Screen {

    int cameraOffsetX = 0;

    MapManager map;

    TextInterface textInterface;

    public Raknykas game;

    private OrthographicCamera gameCam;

    private Viewport gamePort;

    private Player player;

    public PlayScreen(Raknykas game){

        this.game = game;

        gameCam = new OrthographicCamera();
        //camera has viewport
        gamePort = new FitViewport((Raknykas.SCREEN_WIDTH / Raknykas.PPM), (Raknykas.SCREEN_HEIGHT / Raknykas.PPM) - 1, gameCam);


//        map = new MapManager(gameCam);
        map = new MapManager();
        this.player = new Player( map );

//        textInterface = new TextInterface(new ScreenViewport());

    }

    @Override
    public void show() {

    }

    private void handleMouseDrag(float delta_time){

        if(Gdx.input.isTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            float camSpeed = 2;

            if( x < 20)
                gameCam.position.x -= camSpeed * delta_time;

            if( x > gamePort.getWorldWidth() - 20)
                gameCam.position.x += camSpeed * delta_time;

            if(y < 20)
                gameCam.position.y += camSpeed * delta_time;

            if( y > gamePort.getWorldHeight() - 20)
                gameCam.position.y -= camSpeed * delta_time;
        }

    }

    private void handleCam(float delta_time){

//        if(Gdx.input.isTouched())
//            handleMouseDrag(delta_time);

        int playerScreenPosition_X = (int)(player.getSprite().rigidBody.getTransform().getPosition().x * Raknykas.PPM);
        gameCam.position.x = (playerScreenPosition_X - cameraOffsetX) / Raknykas.PPM;

        gameCam.position.y = player.getSprite().rigidBody.getTransform().getPosition().y;

        gameCam.update();

    }

    @Override
    public void render(float delta) {

        map.update(delta);
        map.updateView(gameCam);
        player.update(delta);
        handleCam(delta);

        gamePort.setScreenX((int)(Raknykas.SCREEN_WIDTH / Raknykas.PPM));

        //clear game screen
        ScreenUtils.clear(0, 0, 0, 1);

        //render sprites: player, enemies, keys, exit
        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();

        map.draw(game.batch);
        player.getSprite().draw(Raknykas.batch);

        game.batch.end();


        player.act(delta);
        player.draw();
//        textInterface.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
    }


}
