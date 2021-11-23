package inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Slot extends Actor {
    private int height = 32;
    private int width = 32;

    private Item item;
    private Sprite sprite;
    private Sprite background = new Sprite(new Texture(Gdx.files.internal("./icons/slot_bg.png")));

    private float alpha = 0;

    public Slot(){
        init();
    }

    public Slot(Item _item){

        init();
        setItem(_item);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch);

        if(sprite != null)
            sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    //update position of sprites after actor position changes
    @Override
    protected void positionChanged() {
        float x = getX();
        float y = getY();

        if(sprite != null)
            sprite.setPosition(x, y);

        background.setPosition(x, y);

        super.positionChanged();
    }

    void setAlpha(float value)
    {
        if(sprite != null)
            sprite.setAlpha(value);

        background.setAlpha(value);
    }

    void setItem(Item _item)
    {
        this.item = _item;
        sprite = new Sprite(new Texture(Gdx.files.internal(_item.getIconSrc())));
        sprite.setSize(width, height);
        if(item.getColor() != null){
            sprite.setColor(item.getColor());
        }
    }

    void init()
    {
        background.setSize(width, height);
        background.setColor(Color.BLACK);
        setBounds(0, 0, width, height);
        setTouchable(Touchable.enabled);
        setAlpha(0.1f);

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.D){
                    MoveByAction mba = new MoveByAction();
                    mba.setAmount(100f, 100f);
                    mba.setDuration(2f);

                    Slot.this.addAction(mba);
                }

                if(keycode == Input.Keys.W)
                    setActive(true);
                if(keycode == Input.Keys.S)
                    setActive(false);

                return true;
            }
        });


    }

    void setActive(boolean value)
    {
        if(value)
            setAlpha(0.8f);
        else
            setAlpha(0.1f);
    }

    public Item getItem(){
        return item;
    }
}
