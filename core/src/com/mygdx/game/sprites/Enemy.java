package com.mygdx.game.sprites;

import tools.InteractiveTileObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Raknykas;
import tools.MapManager;

import java.util.ArrayList;

public class Enemy extends InteractiveTileObject {

    private ArrayList<Vector2> vertices = new ArrayList<Vector2>();
    private float timer = 0;
    private int direction = 1;
    private float speedFactor = 1.7f;

    public Enemy(MapManager mapManager, Polyline path){
        super(mapManager, path);

        float[] vertices_temp = path.getTransformedVertices();

        vertices.add(new Vector2(vertices_temp[0], vertices_temp[1]));
        vertices.add(new Vector2(vertices_temp[2], vertices_temp[3]));

        setRegion(new Texture(Gdx.files.internal("./icons/icons/Icon.2_08.png")));

        setBounds(path.getX() / Raknykas.PPM,
                path.getY() / Raknykas.PPM,
                32 / Raknykas.PPM,
                32 / Raknykas.PPM);

        setCategoryFilter(Raknykas.ENEMY_BIT);

    }

    public void update(float delta_time){

        ArrayList<Vector2> temp = new ArrayList<Vector2>(vertices);

        Vector2 srcVector;
        Vector2 destVector;

        if(timer > 1){
            direction = -1;
        }

        if(timer < 0.1){
            direction = 1;
        }

        timer += (delta_time * direction) / speedFactor;

        srcVector = new Vector2(temp.get(0));
        destVector = new Vector2(temp.get(1));
        srcVector.lerp(destVector, timer);

        move(new Vector2(srcVector.x, srcVector.y), body.getAngle());


    }

    public void move(Vector2 vector, float angle){
        float centerOffset_x = bounds.getWidth() / 2;
        float centerOffset_y = bounds.getHeight() / 2;
        setPosition(vector.x / Raknykas.PPM, vector.y / Raknykas.PPM);
        body.setTransform( (vector.x + centerOffset_x) / Raknykas.PPM, (vector.y + centerOffset_y) / Raknykas.PPM, body.getAngle());
    }

}
