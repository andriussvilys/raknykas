package textInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TextInterface extends Stage {
    TextArea textArea;
    TextButton button;

    public TextInterface(){
        create();
    }

    public TextInterface(ScreenViewport viewport){
        super(viewport);
        create();
    }

    private void create(){
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        button = new TextButton("Run code", skin, "default");
        button.setHeight(50);
        button.setWidth(200);

        textArea = new TextArea("labas", skin);
        textArea.setY(60);
        textArea.setWidth(200);
        textArea.setHeight(200);

        addActor(button);
        addActor(textArea);

        unfocusAll();
    }
}
