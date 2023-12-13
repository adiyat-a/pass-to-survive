package com.company.passtosurvive.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.company.passtosurvive.tools.MusicalAtmosphere;

public class MainMenuScreen implements Screen { // the main menu starts at the beginning of the game through the pause menu you can return here
    final Main game;
    private MusicalAtmosphere music;
    private SpriteBatch batch;
    private Texture background, information;
    private float stateTime;
    private Stage stage;
    private ImageButton exit, sound, info, play, level1, level2, soundIsOff;
    private Skin skin;
    private TextureAtlas atlas;
    public MainMenuScreen(final Main game){
        this.game=game;
        Main.chooseLevel=false;
        Main.HumanX=0;
        Main.HumanY=0;
        Main.HumanXCheckpoint=0;
        Main.HumanYCheckpoint=0;
        music=new MusicalAtmosphere();
        batch=new SpriteBatch();
        Array<TextureRegion> logoFrames=new Array<TextureRegion>();
        atlas=new TextureAtlas("Logo.pack");
        information=new Texture("DevInfo.png");
        for(int i=1; i<=175; i+=3){
            logoFrames.add(atlas.findRegion("Logo"+i)); // I increase i here by 3 because there are too many textures in the atlas (177)
        }
        logoFrames.add(atlas.findRegion("Logo177"));
        Main.animation=new Animation(0.05f, logoFrames);
        logoFrames.clear();
        background=new Texture(Gdx.files.internal("backgroundMainMenu.png"));
        stage=new Stage();
        atlas=new TextureAtlas("AllComponents.pack");
        skin=new Skin(Gdx.files.internal("Buttons.json"), atlas);
        play=new ImageButton(skin, "default");
        play.addListener(new ClickListener() { // create a listener for it that will read keystrokes
            @Override
            public void clicked(InputEvent event, float x, float y) {
                level1.setVisible(true);
                level2.setVisible(true);
                Main.chooseLevel=true;
            }
        });
        exit =new ImageButton(skin, "default1");
        exit.addListener(new ClickListener(){ // create a listener for it that will read keystrokes
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
            }
        });
        sound=new ImageButton(skin, "default9");
        soundIsOff=new ImageButton(skin, "default13");
        soundIsOff.addListener(new ClickListener(){ // create a listener for it that will read keystrokes
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(sound.isVisible() || Main.infoIsPressed) {
                    music.MainMenuSoundPLay();
                    Main.soundIsOn = true;
                }
            }
        });
        sound.addListener(new ClickListener(){ // create a listener for it that will read keystrokes
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(music.MainMenuSoundIsPlaying()){
                    music.MainMenuSoundStop();
                    Main.soundIsOn=false;
                }
                else{
                    music.MainMenuSoundPLay();
                    Main.soundIsOn=true;
                }
            }
        });
        info=new ImageButton(skin, "default10");
        info.addListener(new ClickListener(){ // create a listener for it that will read keystrokes
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Main.infoIsPressed){
                    Main.infoIsPressed=false;
                }
                else{
                    Main.infoIsPressed=true;
                }
            }
        });
        level1=new ImageButton(skin, "default11");
        level1.addListener(new ClickListener(){ //create a listener for it that will read keystrokes
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.MainMenuSoundStop();
                dispose();
                game.setScreen(new Level1ScreenPart1(game));
            }
        });
        level2=new ImageButton(skin, "default12");
        level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.MainMenuSoundStop();
                dispose();
                game.setScreen(new Level2ScreenFloor1(game));
            }
        });
        level1.setVisible(false);
        level2.setVisible(false);
        stage.addActor(exit);
        stage.addActor(play);
        stage.addActor(info);
        stage.addActor(sound);
        stage.addActor(soundIsOff);
        stage.addActor(level1);
        stage.addActor(level2);
        Gdx.input.setInputProcessor(stage); // so that clicks are processed only by stage
        if(Main.soundIsOn) {
            music.MainMenuSoundPLay();
        }
        else{
            music.MainMenuSoundStop();
        }
    }
    public void animeRender(){ // in the presentation I explained why I divide 1920 / by the screen size of the device on which I run
        stateTime+=Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) Main.animation.getKeyFrame(stateTime, true),
                Main.width/2-(936/(1920/Main.width))/2,
                Main.height/2+250/(1080/Main.height),
                936/(1920/Main.width),
                144/(1920/Main.width));
    }
    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // cleanup
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // cleanup
        batch.begin();
        batch.draw(background, 0, 0, Main.width, Main.height);
        if(Main.infoIsPressed==false) {
            animeRender();
        }
        else{ batch.draw(information, Main.width/2-((1500/(1794/Main.width))/2),
                    Main.height/2-((225/(1794/Main.width))/2),
                    1500/(1794/Main.width), 225/(1794/Main.width));
        }
        batch.end();
            stage.act(delta);
            stage.draw();
            if(Main.soundIsOn){
                sound.setVisible(true);
                soundIsOff.setVisible(false);
            }
            else{
                sound.setVisible(false);
                soundIsOff.setVisible(true);
            }
            if(Main.infoIsPressed){
                exit.setVisible(false);
                play.setVisible(false);
            }
            else{
                exit.setVisible(true);
                play.setVisible(true);
            }
            if(Main.chooseLevel){
                exit.setVisible(false);
                play.setVisible(false);
                info.setVisible(false);
                sound.setVisible(false);
                soundIsOff.setVisible(false);
            }
            else if(!Main.chooseLevel && !Main.infoIsPressed){
                exit.setVisible(true);
                play.setVisible(true);
                info.setVisible(true);
                sound.setVisible(true);
            }
    }
    @Override
    public void resize(int width, int height) { // in the presentation I explained why I optimize the sizes of buttons for the screen in my own way
        Main.width=width;
        Main.height=height;
        sound.setSize(150/(1794/Main.width),
                117/(1794/Main.width));
        sound.setPosition(0, Main.height/2+100/(1080/Main.height));
        soundIsOff.setSize(150/(1794/Main.width),
                117/(1794/Main.width));
        soundIsOff.setPosition(0, Main.height/2+100/(1080/Main.height));
        info.setSize(150/(1794/Main.width),
                117/(1794/Main.width));
        info.setPosition(0, Main.height/2-50/(1080/Main.height));
        play.setSize((875/(2880/Main.width)),
                (350/(2880/Main.width)));
        play.setPosition((Main.width/2)-((play.getWidth())/2),
                ((Main.height/2)-200/(2880/Main.width)));
        exit.setSize((875/(2880/Main.width)),
                (350/(2880/Main.width)));
        exit.setPosition((Main.width/2)-((exit.getWidth())/2),
                ((Main.height/2)-600/(2880/Main.width)));
        level1.setSize(525/(1794/Main.width),
                525/(1794/Main.width));
        level2.setSize(525/(1794/Main.width),
                525/(1794/Main.width));
        level1.setPosition(Main.width/2-(525/(1794/Main.width))/2-350/(1794/Main.width),
                Main.height/2-(525/(1794/Main.width))/2-100/(1080/Main.height));
        level2.setPosition(Main.width/2-(525/(1794/Main.width))/2+350/(1794/Main.width),
                Main.height/2-(525/(1794/Main.width))/2-100/(1080/Main.height));
    }
    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();
        skin.dispose();
        atlas.dispose();
        game.dispose();
        batch.dispose();
        background.dispose();
        information.dispose();
    }
}