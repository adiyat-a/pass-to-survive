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

public class PlayButtons implements Disposable { // has all the buttons and sticks for level screens, only they have this class
    public Stage stage;
    private Viewport port;
    public ImageButton pause, jump;
    private Skin skin;
    private TextureAtlas atlas;
    public JoyStick joyStick;
    public PlayButtons(){
        port=new StretchViewport(1794, 1080, new OrthographicCamera()); // StretchViewPort will stretch across the entire screen
        stage=new Stage(port);
        atlas=new TextureAtlas("AllComponents.pack");
        skin=new Skin(Gdx.files.internal("Buttons.json"), atlas);
        pause =new ImageButton(skin, "default4");
        jump =new ImageButton(skin, "default2");
        joyStick=new JoyStick();
        // here I did not use my optimization method because there were problems with the joystick and I didn’t want to
        // so that all the buttons look normal and one joystick is stretched
        pause.setSize(158, 140);
        pause.setPosition(0, 940);
        joyStick.setSP(); // the joystick itself sets the dimensions inside its class
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
