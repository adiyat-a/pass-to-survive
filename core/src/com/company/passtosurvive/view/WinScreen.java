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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.company.passtosurvive.tools.MusicalAtmosphere;

public class WinScreen implements Screen { // the victory screen is launched after completing the level completely
    final Main game;
    private MusicalAtmosphere music;
    private Texture label, Thanks;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private ImageButton Yes, No;
    private float stateTime, stateTimer; // stateTimer is needed to count time
    private boolean played; // needed to start sound at a certain moment
    public WinScreen(final Main game) {
        this.game = game;
        music=new MusicalAtmosphere();
        batch=new SpriteBatch();
        if(Main.level1IsFinished && Main.level2IsFinished) { // when player have completely completed the game
            label = new Texture("GameOverThanks.png");
            Thanks = new Texture("thforpl.png");
        }
        else if(Main.level1IsFinished && !Main.level2IsFinished || Main.level2IsFinished && !Main.level1IsFinished) { // when one of the levels has passed
            stage = new Stage();
            label = new Texture("continueWhite.png");
            atlas = new TextureAtlas("AllComponents.pack");
            skin = new Skin(Gdx.files.internal("Buttons.json"), atlas);
            atlas = new TextureAtlas("YOUWIN.pack");
            Array<TextureRegion> Frames = new Array<TextureRegion>();
            for (int i = 1; i <= 15; i++) Frames.add(atlas.findRegion("YOUWIN" + i));
            Main.animation = new Animation(0.2f, Frames);
            Frames.clear();
            Yes = new ImageButton(skin, "default14");
            Yes.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.HumanY = 0;
                    Main.HumanX = 0;
                    Main.HumanXCheckpoint = 0;
                    Main.HumanYCheckpoint = 0;
                    dispose();
                    if (Main.level1IsFinished) {
                        game.setScreen(new Level2ScreenFloor1(game));
                    } else if (Main.level2IsFinished) {
                        game.setScreen(new Level1ScreenPart1(game));
                    }
                }
            });
            No = new ImageButton(skin, "default15");
            No.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
            });
            stage.addActor(Yes);
            stage.addActor(No);
            Gdx.input.setInputProcessor(stage); // so that clicks are processed only by stage
        }
        if(Main.level1IsFinished && !Main.level2IsFinished || Main.level2IsFinished && !Main.level1IsFinished) {
            music.WinSoundPlay();
        }
    }
    public void winRender(){
        stateTime+=Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion)  Main.animation.getKeyFrame(stateTime, true), 0, Main.height/2, Main.width, Main.height/3f);
    }
    @Override
    public void show() { }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // cleanup
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // cleanup
        batch.begin();
        stateTimer+=delta;
        if(Main.level1IsFinished && !Main.level2IsFinished || Main.level2IsFinished && !Main.level1IsFinished){
            winRender();
            if(stateTimer>=3) {
                batch.draw(label, Main.width / 2 - ((1266.7f / (1794 / Main.width)) / 2),
                        Main.height / 2 - 80 / (1080 / Main.height) - 66.6f / (1794 / Main.width) - 50 / (1080 / Main.height),
                        1266.7f / (1794 / Main.width), 66.6f / (1794 / Main.width));
            }
        }
        else if(Main.level1IsFinished && Main.level2IsFinished){
            batch.draw(label, 0, Main.height/2, Main.width, 203/(1794/Main.width));
            if(stateTimer>=7) {
                if(!played){
                    music.EndSoundPlay();
                    played=true;
                }
                batch.draw(Thanks, Main.width / 2 - ((1266.7f / (1794 / Main.width)) / 2),
                        Main.height / 2 - 80 / (1080 / Main.height) - 66.6f / (1794 / Main.width) - 50 / (1080 / Main.height),
                        1266.7f / (1794 / Main.width), 66.6f / (1794 / Main.width));
            }
        }
        batch.end();
        if(Main.level1IsFinished && !Main.level2IsFinished || Main.level2IsFinished && !Main.level1IsFinished) {
            if (Main.level1IsFinished && !Main.level2IsFinished && stateTimer >= 3 || Main.level2IsFinished && !Main.level1IsFinished && stateTimer >= 3) {
                Yes.setVisible(true);
                No.setVisible(true);
            }
            else if (Main.level1IsFinished && Main.level2IsFinished || stateTimer < 3) {
                Yes.setVisible(false);
                No.setVisible(false);
            }
            stage.act(delta);
            stage.draw();
        }
    }
    @Override
    public void resize(int width, int height) { // I explained this in slides (.pptx file)
        if (Main.level1IsFinished && !Main.level2IsFinished || Main.level2IsFinished && !Main.level1IsFinished) {
            Yes.setSize(141.5f / (1794 / Main.width), 50 / (1794 / Main.width));
            No.setSize(141.5f / (1794 / Main.width), 50 / (1794 / Main.width));
            Yes.setPosition((Main.width / 2) - (341.5f / (1794 / Main.width)),
                    (Main.height / 2) - (66.6f / (1794 / Main.width)) - (180 / (1080 / Main.height)) - (100 / (1080 / Main.height)));
            No.setPosition((Main.width / 2) + (200f / (1794 / Main.width)),
                    (Main.height / 2) - (66.6f / (1794 / Main.width)) - (180 / (1080 / Main.height)) - (100 / (1080 / Main.height)));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        music.dispose();
        game.dispose();
        label.dispose();
        if(Main.level1IsFinished && Main.level2IsFinished){
            Thanks.dispose();
        }
        else if(Main.level1IsFinished && !Main.level2IsFinished || Main.level2IsFinished && !Main.level1IsFinished){
            atlas.dispose();
            stage.dispose();
            skin.dispose();
        }
    }
}
