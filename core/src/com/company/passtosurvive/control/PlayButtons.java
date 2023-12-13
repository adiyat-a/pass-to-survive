package com.company.passtosurvive.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayButtons implements Disposable { // имеет все кнопки и стики для левел экранов, этот класс есть только у них
    public Stage stage;
    private Viewport port;
    public ImageButton pause, jump;
    private Skin skin;
    private TextureAtlas atlas;
    public JoyStick joyStick;
    public PlayButtons(){
        port=new StretchViewport(1794, 1080, new OrthographicCamera()); // StretchViewPort будет растягивать по всему экрану
        stage=new Stage(port);
        atlas=new TextureAtlas("AllComponents.pack");
        skin=new Skin(Gdx.files.internal("Buttons.json"), atlas);
        pause =new ImageButton(skin, "default4");
        jump =new ImageButton(skin, "default2");
        joyStick=new JoyStick();
        // здесь я не использовал свой метод оптимизации т.к. с joystick'ом были проблемы и я не хотел
        // чтобы все кнопки выглядели нормально а один джойстик растянут
        pause.setSize(158, 140);
        pause.setPosition(0, 940);
        joyStick.setSP(); // джойстик сам ставит размеры внутри своего класса
        jump.setSize(235, 320);
        jump.setPosition(1270, 40);
        stage.addActor(pause);
        stage.addActor(jump);
        stage.addActor(joyStick);
        Gdx.input.setInputProcessor(stage); // so that clicks are processed only by stage
    }
    public void update(int width, int height){
        port.update(width, height);
    }
    @Override
    public void dispose(){
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }
}
