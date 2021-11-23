package com.mygdx.game.sprites;

import tools.InteractiveTileObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Raknykas;
import inventory.Inventory;
import tools.MapManager;

public class Lock extends InteractiveTileObject {

    private int ID;

    public Lock(MapManager mapManager, Rectangle bounds, int ID) {
        super(mapManager, bounds);
        setRegion(new Texture(Gdx.files.internal("./icons/icons/Icon.5_82.png")));

        setBounds(bounds.x / Raknykas.PPM,
                bounds.y / Raknykas.PPM,
            bounds.getWidth() / Raknykas.PPM,
            bounds.getHeight() / Raknykas.PPM);

        this.ID = ID;
        setCategoryFilter(Raknykas.LOCK_BIT);
    }

    public int getID(){
        return ID;
    }

    @Override
    public void onInteract() {
        Inventory inventoryRef = Inventory.getInstance();
        if(inventoryRef.getSelectedItem() != null){
            int targetID = Inventory.getInstance().getSelectedItem().getTargetID();
            if(targetID == ID){
                inventoryRef.removeItem(inventoryRef.getSelectedItem());
                setCategoryFilter(Raknykas.DESTROYED_BIT);
                setScale(0);
            }
        }
    }



}
