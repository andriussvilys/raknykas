package tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Player;
import com.mygdx.game.Raknykas;
import com.mygdx.game.sprites.PlayerSprite;

public class MapManager {

    private TmxMapLoader maploader;
    private TiledMap map;
    private InteractiveLayer interactiveLayer;
    private OrthogonalTiledMapRenderer renderer;
    private World world;

    Box2DDebugRenderer debugRenderer;

    private String[] levels_src = {"./maps/level_2.tmx", "./maps/level_3.tmx", "./maps/level_4.tmx"};
    private int currentLevel = 0;

    private Player player;

    private OrthographicCamera gameCam;

    public MapManager(){
        maploader = new TmxMapLoader();
        LoadLevel();
    }

    public MapManager(OrthographicCamera gameCam){
        debugRenderer = new Box2DDebugRenderer();
        this.gameCam = gameCam;
        maploader = new TmxMapLoader();
        LoadLevel();
    }

    public MapManager(Player player){
        maploader = new TmxMapLoader();
        LoadLevel();
        setPlayer(player);
    }

    private void setDebugger( OrthographicCamera gameCam ){

    }

    private void LoadLevel(){

        world = new World(new Vector2(0,0), false);

        map = maploader.load(levels_src[currentLevel]);

        //generates walls on map (physical bounds/colliders)
        new WorldCreator( this );

        //generates locks + keys on map
        interactiveLayer = new InteractiveLayer(this);

//        world.setContactListener(new WorldContactListener( player.getSprite() ));

        renderer = new OrthogonalTiledMapRenderer(map, 1 / Raknykas.PPM);

    }

    public void setPlayer(Player player){
        this.player = player;
//        world.setContactListener(new WorldContactListener( this.player.getSprite() ) );
    }

    public void update(float delta){
        world.step(1/60f, 6, 2);
        interactiveLayer.update(delta);
    }

    public void updateView(OrthographicCamera camera){
        renderer.setView(camera);
    }

    public void draw(SpriteBatch batch){
        renderer.render();
        interactiveLayer.draw(batch);
        if(debugRenderer != null){
            debugRenderer.render(world, gameCam.combined);
        }
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
    }

    public World getWorld(){
        return this.world;
    }
    public TiledMap getMap(){ return  this.map; }

    public void switchLevel(){

        ++currentLevel;
        if(currentLevel > levels_src.length - 1 )
            currentLevel = 0;

        LoadLevel();
        player.resetSprite(world);
//        world.setContactListener(new WorldContactListener( player.getSprite() ));
    }

}
