package tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Raknykas;
import com.mygdx.game.screens.PlayScreen;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Key;
import com.mygdx.game.sprites.Lock;
import com.mygdx.game.sprites.NextLevelGate;
import inventory.Item;

import java.util.ArrayList;

public class InteractiveLayer {

    MapManager mapManager;

    //ref to game for drawing sprites

    private ArrayList<Lock> locks = new ArrayList<Lock>();
    private ArrayList<Key> keys = new ArrayList<Key>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<NextLevelGate> exitGates = new ArrayList<NextLevelGate>();

//    public InteractiveLayer(MapManager mapManager, TiledMap map){
    public InteractiveLayer(MapManager mapManager){

        this.mapManager = mapManager;
        TiledMap map = mapManager.getMap();

    //create locks
        for(MapObject object: map.getLayers().get( Raknykas.LOCK_LAYER ).getObjects()){

            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            Lock temp = new Lock(mapManager, rect, (int)object.getProperties().get("id"));
            locks.add(temp);

        }

        if(map.getLayers().size() > Raknykas.KEY_LAYER){

            for(MapObject object: map.getLayers().get( Raknykas.ENEMY_LAYER ).getObjects()){

                Polyline path = ((PolylineMapObject)object).getPolyline() ;
                Enemy temp = new Enemy(mapManager, path);
                enemies.add(temp);

            }
        }

        if(map.getLayers().size() > 6){

            for(MapObject object: map.getLayers().get( Raknykas.EXIT_LAYER ).getObjects()){

                Rectangle rect = ((RectangleMapObject)object).getRectangle();
                NextLevelGate temp = new NextLevelGate(mapManager, rect);
                exitGates.add(temp);

            }
        }

        //create keys
        for(MapObject object: map.getLayers().get( Raknykas.KEY_LAYER ).getObjects()){

            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            Color keyColor = new Color(Color.valueOf(object.getProperties().get("Tint").toString()));
            int targetID = (int)object.getProperties().get("Target_ID");

            Key ref = new Key(mapManager, rect);
            ref.setKeyItem(new Item("./icons/key_default.png", "Key item", keyColor));
            ref.setTargetID(targetID);
            ref.setColor(keyColor);

            keys.add(ref);

            for(Lock lock: locks){
                if(lock.getID() == targetID)
                    lock.setColor(keyColor);
            }

        }
    }

    public void draw(Batch batch){

        for(Lock lock : locks)
            lock.draw(batch);

        for(Key key : keys)
            key.draw(batch);

        for(Enemy enemy: enemies)
            enemy.draw(batch);

        for(NextLevelGate exitGate: exitGates)
            exitGate.draw(batch);
    }

    public void update(float delta_time){
        for(Enemy enemy : enemies)
            enemy.update(delta_time);
    }

}
