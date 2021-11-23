package tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Raknykas;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class InteractiveTile extends Sprite {

    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected Polyline path;

    public InteractiveTile(MapManager mapManager, Rectangle bounds){
        this.world = mapManager.getWorld();
        this.map = mapManager.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        float center_x = bounds.getWidth() / 2;
        float center_y = bounds.getHeight() / 2;
        bdef.position.set(
                (bounds.getX() + center_x) / Raknykas.PPM,
                (bounds.getY() + center_y) / Raknykas.PPM
        );

        body = world.createBody(bdef);

        shape.setAsBox(center_x / Raknykas.PPM, center_y / Raknykas.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);

        fixture = body.getFixtureList().get(0);

    }

    public InteractiveTile(MapManager mapManager, Polyline path){
        this.world = mapManager.getWorld();
        this.map = mapManager.getMap();
        this.bounds = new Rectangle(path.getX(), path.getY(), 32, 32 );
        this.path = path;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        float center_x = bounds.getWidth() / 2;
        float center_y = bounds.getHeight() / 2;
        bdef.position.set(
                (bounds.getX() + center_x) / Raknykas.PPM,
                (bounds.getY() + center_y) / Raknykas.PPM
        );

        body = world.createBody(bdef);

        shape.setAsBox(center_x / Raknykas.PPM, center_y / Raknykas.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);

        fixture = body.getFixtureList().get(0);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(Raknykas.GFX_LAYER);
        return layer.getCell(
                (int)(body.getPosition().x * Raknykas.PPM / 32),
                (int)(body.getPosition().y * Raknykas.PPM / 32)
        );
    }

    public abstract void onInteract();

}
