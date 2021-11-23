package tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Raknykas;

public class InteractiveTileObject extends InteractiveTile{

    public InteractiveTileObject(MapManager mapManager, Rectangle rect){
        super(mapManager, rect);
        fixture.setUserData(this);
        setCategoryFilter(Raknykas.DEFAULT_BIT);
    }

    public InteractiveTileObject(MapManager mapManager, Polyline path){
        super(mapManager, path);
        fixture.setUserData(this);
        setCategoryFilter(Raknykas.DEFAULT_BIT);
    }

    @Override
    public void onInteract() {
    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
}
