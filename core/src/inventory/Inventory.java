package inventory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.SnapshotArray;


public class Inventory extends Group {

    static private Inventory instance = null;

    private int slotColumnsCount = 0;
    private Item selectedItem = new Item();

    private Inventory()
    {
        setTouchable(Touchable.enabled);
        setBounds(0, 0, 32, 96);

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                int columnIndex = keycode - 8;
                if(columnIndex <= (slotColumnsCount - 1) && columnIndex > -1){

                    for (int i = 0; i < slotColumnsCount; i++) {
                        SlotColumn currentColumn = (SlotColumn) getChild(i);

                        if(i == columnIndex){
                            currentColumn.activate(true);
                            selectedItem = currentColumn.getSelectedItem();
                        }

                        else
                            currentColumn.activate(false);
                    }

                }

                return false;
            }
        });

    }

    static public Inventory getInstance(){
        if(instance == null)
            instance = new Inventory();
        return instance;
    }

    public void add(Item item){

        if(slotColumnsCount == 0 || ( (SlotColumn)getChild(slotColumnsCount-1) ).getChildrenCount() >= 3){
            addSlotColumn();
        }

        SlotColumn currentColumn = (SlotColumn)getChild(slotColumnsCount-1);
        currentColumn.addSlot( new Slot(item) );

        //open column to view newly added item
        currentColumn.extendColumn();

    }

    private void addSlotColumn()
    {
        if(slotColumnsCount < 9){
            SlotColumn newColumn = new SlotColumn();
            addActor(newColumn);
            newColumn.setPosition((slotColumnsCount * (newColumn.getWidth()/2)), 0);
            newColumn.setTriggerKey(Input.Keys.NUM_1 + slotColumnsCount);

            ++slotColumnsCount;
        }

    }

    public void removeItem(Item item)
    {
        SnapshotArray<Actor> slotColumns = getChildren();

        for(Actor slotColumn : slotColumns){

            SnapshotArray<Actor> slots = ( (Group)slotColumn ).getChildren();

            for( Actor slot :  slots){
                if( ((Slot) slot).getItem() == item){
                    selectedItem = null;
                    ((SlotColumn)slotColumn).removeSlot((Slot)slot);
                }
            }

        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Item getSelectedItem(){

        if(selectedItem != null)
            return selectedItem;
        else
            return null;

    };

}
