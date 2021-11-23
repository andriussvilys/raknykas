package com.mygdx.game.sprites;

import tools.InteractiveTileObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Raknykas;
import inventory.Inventory;
import inventory.Item;
import tools.MapManager;

public class Key extends InteractiveTileObject {

    private Item item;

    //creates rigid body in world according to map data
    public Key(MapManager mapManager, Rectangle bounds){
        super(mapManager, bounds);
        setCategoryFilter(Raknykas.KEY_BIT);
        setRegion(new Texture(Gdx.files.internal("./icons/key_default.png")));
        setBounds(bounds.x / Raknykas.PPM, bounds.y / Raknykas.PPM, bounds.getWidth() / Raknykas.PPM, bounds.getHeight() / Raknykas.PPM);
    }

    public Key(MapManager mapManager, Rectangle bounds, Item item){
        this(mapManager, bounds);
        this.item = item;
    }

    @Override
    public void onInteract() {
        setCategoryFilter(Raknykas.DESTROYED_BIT);
//        getCell().setTile(null);
        if(item != null){
            setScale(0);
            Inventory.getInstance().add(item);
        }
    }

    public void setKeyItem(Item item){
        this.item = item;
    }

    public void setTargetID(int targetID){
        item.setTargetID(targetID);
    }

}
