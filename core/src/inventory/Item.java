package inventory;

import com.badlogic.gdx.graphics.Color;

public class Item {
    private String icon_src;
    private String title;
    private Color color;
    private int targetID;

    public Item(){}

    public Item(String icon_src, String title, Color color){
        this(icon_src, title);
        this.color = color;
    }

    public Item(String icon_src, String title){
        this.icon_src = icon_src;
        this.title = title;
    }

    public String getIconSrc(){
        return icon_src;
    }

    public String getTitle(){
        return title;
    }

    public Color getColor(){
        if(color != null)
            return color;

        else
            return null;
    }

    public void setTargetID(int targetID){
        this.targetID = targetID;
    }

    public int getTargetID(){
        return targetID;
    }

}
