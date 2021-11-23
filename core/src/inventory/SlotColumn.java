package inventory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.SnapshotArray;

public class SlotColumn extends Group {

    private float timer = 0;
    private int activeSlot = -1;
    private boolean isVisible = false;
    private int childrenCount = 0;
    private int triggerKey = Input.Keys.NUM_1;

    public SlotColumn()
    {
        init();
    }

    public SlotColumn(Slot[] slots)
    {
        init();
        for (int i = 0; i < slots.length; i++) {

            addActor(slots[i]);
            slots[i].setPosition(getX(), i * slots[i].getHeight());

        }

        childrenCount = slots.length;

        makeVisible(false);

    }

    void init(){

        setTouchable(Touchable.enabled);
        setBounds(0, 0, 32, 96);
    }

    public void activate(boolean value){

        if(!value)
            highlightSlot(-1);

        else{

//            if(isVisible)
                activeSlot += 1;

            makeVisible(true);

            if(activeSlot == childrenCount)
                activeSlot = 0;

            highlightSlot(activeSlot);
        }

    }

    //if -1, remove highlight from all
    void highlightSlot(int index){
        for(int i = 0; i < childrenCount; ++i)
        {
            Slot slot = ((Slot)getChild(i));

            if(i != index)
                slot.setActive(false);

            else
                slot.setActive(true);
        }
    }

    //print function
    public void listChildren()
    {
        SnapshotArray<Actor> children = getChildren();
        for (int i = 0; i < children.size; i++) {
            System.out.println(children.get(i).getName());
        }
    }

    @Override
    public void act(float delta) {

        if(timer > 0)
            timer -= delta;
        else if(timer < 0){
            makeVisible(false);
            timer = 0;
        }

        super.act(delta);
    }

    //extends or closes columns
    void makeVisible(boolean value)
    {
        if(value == true)
        {
            extendColumn();
            isVisible = true;
        }
        else
        {
            collapseColumn();
            isVisible = false;
        }
    }

    void collapseColumn()
    {
        SnapshotArray<Actor> slots = getChildren();

        for (int i = 0; i < childrenCount; i++) {
            Slot currentSlot = (Slot)slots.get(i);
            currentSlot.setPosition(getX(), getY() + currentSlot.getHeight());

            if(i != activeSlot){
                currentSlot.setAlpha(0.1f);
            }
        }

    }

    //timed for 0.6f
    public void extendColumn()
    {
        timer = 0.6f;
        for (int i = 0; i < childrenCount; i++) {
            Slot current = (Slot)getChild(i);

            if(i == 0) {
                current.setPosition( getX(), getY() + current.getHeight() );
            }

            if(i == 1)
                current.setPosition( getX(), getY() + (current.getHeight() * 2) );

            if(i == 2)
                current.setPosition( getX(), getY() );
        }

    }

    //move slots if parent position changed
    @Override
    protected void positionChanged() {
        SnapshotArray<Actor> slots = getChildren();
        for (int i = 0; i < childrenCount; i++) {
            Slot currentSlot = (Slot)slots.get(i);
            currentSlot.setPosition(getX(), getY() + currentSlot.getHeight());
        }
    }

   public void setTriggerKey(int _triggerKey){
        triggerKey = _triggerKey;
    }

    public void addSlot(Slot newSlot){
        if(childrenCount < 3){
            addActor(newSlot);
            newSlot.setPosition(getX(), getY() + newSlot.getHeight());
            ++childrenCount;
        }
    }

    public void removeSlot(Slot slot){
        removeActor(slot);
        --childrenCount;
        activeSlot = -1;
    }

    public int getChildrenCount(){
        return childrenCount;
    }

    public Item getSelectedItem()
    {
        if(activeSlot > -1)
            return ((Slot)getChild(activeSlot)).getItem();
        else
            return null;
    }

}
