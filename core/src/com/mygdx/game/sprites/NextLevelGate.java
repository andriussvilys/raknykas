package com.mygdx.game.sprites;

import tools.InteractiveTileObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Raknykas;
import tools.MapManager;

public class NextLevelGate extends InteractiveTileObject {

    MapManager mapManager;

    public NextLevelGate(MapManager mapManager, Rectangle rect){
        super(mapManager, rect);

        this.mapManager = mapManager;

        setRegion(new Texture(Gdx.files.internal("./icons/icons/Icon.5_81.png")));

        setBounds(bounds.x / Raknykas.PPM,
                bounds.y / Raknykas.PPM,
                bounds.getWidth() / Raknykas.PPM,
                bounds.getHeight() / Raknykas.PPM);

        setCategoryFilter(Raknykas.LOCK_BIT);

    }

    @Override
    public void onInteract() {
        System.out.println("interact with exit");
        mapManager.switchLevel();
    }
}
