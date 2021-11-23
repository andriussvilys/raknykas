package tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Raknykas;

public class WorldCreator {

    public WorldCreator(MapManager mapManager){

        //map 2dBox colliders to tiled map

        TiledMap map = mapManager.getMap();

        for (int i = 2; i < 6; i++) {

            if(i != Raknykas.LOCK_LAYER && i != Raknykas.KEY_LAYER){

                for(MapObject object: map.getLayers().get(i).getObjects()){
                    Rectangle rect = ((RectangleMapObject)object).getRectangle();
                    new InteractiveTileObject(mapManager, rect);
                }

            }

        }

    }

}
