package com.company.passtosurvive.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class DeadScreen implements Screen { // GameOver screen starts when the player falls into lava or dies from spikes
    final Main game;
    private MusicalAtmosphere music;
    private Texture gameOver, label;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private ImageButton Yes, No;
    private float stateTime, stateTimer; // stateTimer is needed to count time
    private boolean played; // needed to start sound at a certain moment
    public DeadScreen(final Main game) {
        this.game = game;
        Main.HumanX=0;
        Main.HumanY=0;
        music=new MusicalAtmosphere();
        batch=new SpriteBatch();
        Array<TextureRegion> Frames=new Array<TextureRegion>();
        if(Main.deaths==20) {
            label=new Texture("ClickOScr.png");
            atlas = new TextureAtlas("Ghoul.pack");
            for (int i = 1; i <= 13; i++) Frames.add(atlas.findRegion("Ghoul" + i));
            Main.animation = new Animation(0.08f, Frames);
            Frames.clear();
        }
        else if(Main.hit==1 && Main.deaths!=20 || Main.hit==2 && Main.deaths!=20) {
            if(Main.hit==1) {
                atlas = new TextureAtlas("Dead.pack");
                for (int i = 1; i <= 6; i++) Frames.add(atlas.findRegion("fire" + i));
                Main.animation = new Animation(0.15f, Frames);
                Frames.clear();
            }
            else if(Main.hit==2) {
                gameOver=new Texture("gameOver.png");
                atlas = new TextureAtlas("DeadSpikes.pack");
                for (int i = 1; i <= 37; i += 2)
                    Frames.add(atlas.findRegion("deadSpikes" + i));
                for (int i = 38; i <= 48; i++) Frames.add(atlas.findRegion("deadSpikes" + i));
                Main.animation = new Animation(0.05f, Frames);
                Frames.clear();
            }
            stage = new Stage();
            atlas = new TextureAtlas("AllComponents.pack");
            label = new Texture("continue.png");
            skin = new Skin(Gdx.files.internal("Buttons.json"), atlas);
            Yes = new ImageButton(skin, "default8");
            No = new ImageButton(skin, "default7");
            stage.addActor(Yes);
            stage.addActor(No);
            Gdx.input.setInputProcessor(stage);
            No.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
            });
            Yes.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.HumanY = 0;
                    Main.HumanX = 0;
                    dispose();
                    if (Main.screen == 1) {
                        game.setScreen(new Level1ScreenPart1(game));
                    } else if (Main.screen == 2) {
                        game.setScreen(new Level1ScreenPart2(game));
                    } else if (Main.screen == 3) {
                        game.setScreen(new Level2ScreenFloor1(game));
                    } else if (Main.screen == 4) {
                        game.setScreen(new Level2ScreenFloor2(game));
                    }
                }
            });
            music.GameOverSoundPLay();
        }
    }
    public void deadRender(){ // I explained this in slides (.pptx file)
        stateTime+=Gdx.graphics.getDeltaTime();
        batch.draw( (TextureRegion) Main.animation.getKeyFrame(stateTime, true), 0, Main.height/2, Main.width, Main.height/3f);
    }
    public void deadSpikesRender(){ // I explained this in slides (.pptx file)
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) Main.animation.getKeyFrame(stateTime, false), Main.width / 2-(168/(1794/Main.width)),
                Main.height/2-80/(1080/Main.height)+Main.height/5.1f, 336/(1794/Main.width), 336/(1794/Main.width));
    }
    public void ghoulRender(){ // I explained this in slides (.pptx file)
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) Main.animation.getKeyFrame(stateTime, false), -640/(720/Main.height)+(Main.width/2), 0,
                1280/(720/Main.height),
                720/(720/Main.height));
    }
    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // cleanup
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // cleanup
        batch.begin();
        stateTimer+=delta;
        if(Main.hit==1 && Main.deaths!=20 || Main.hit==2 && Main.deaths!=20) {
            if (Main.hit==1 && Main.deaths!=20) {
                deadRender();
                if (stateTimer >= 3) {
                    batch.draw(label, Main.width / 2 - ((1266.7f / (1794 / Main.width)) / 2),
                            Main.height / 2 - 66.6f / (1794 / Main.width) - 50 / (1080 / Main.height),
                            1266.7f / (1794 / Main.width), 66.6f / (1794 / Main.width));
                }
            }
            else if (Main.hit==2 && Main.deaths!=20) {
                batch.draw(gameOver, 0, Main.height / 2 - 80 / (1080 / Main.height), Main.width, Main.height / 3f);
                deadSpikesRender();
                if (stateTimer >= 3) {
                    batch.draw(label, Main.width / 2 - ((1266.7f / (1794 / Main.width)) / 2),
                            Main.height / 2 - 80 / (1080 / Main.height) - 66.6f / (1794 / Main.width) - 50 / (1080 / Main.height),
                            1266.7f / (1794 / Main.width), 66.6f / (1794 / Main.width));
                }
            }
        }
        else if(Main.deaths==20){
            if(stateTimer>=3){
                ghoulRender();
            }
            if(stateTimer>=7){
                batch.draw(label, Main.width/2-(467.5f/(1794/Main.width)), Main.height/2+200/(1080/Main.height),
                        935/(1794/Main.width), 50.5f/(1794/Main.width));
            }
            else if(stateTimer>=4f && !played){
                music.GhoulSoundPlay();
                played=true;
            }
            if(Gdx.input.justTouched() && stateTimer>=7){
                Main.HumanY=0;
                Main.HumanX=0;
                dispose();
                if (Main.screen==1) {
                    game.setScreen(new Level1ScreenPart1(game));
                }
                else if(Main.screen==2){
                    game.setScreen(new Level1ScreenPart2(game));
                }
                else if(Main.screen==3){
                    game.setScreen(new Level2ScreenFloor1(game));
                }
                else if(Main.screen==4){
                    game.setScreen(new Level2ScreenFloor2(game));
                }
            }
        }
        batch.end();
        if(Main.hit==1 && Main.deaths!=20 || Main.hit==2 && Main.deaths!=20) {
            if(Main.hit==2 && !Main.animation.isAnimationFinished(stateTime) || stateTimer<3){
                Yes.setVisible(false);
                No.setVisible(false);
            }
            else if(Main.hit==1 && stateTimer>=3 && Main.deaths!=20 || Main.hit==2 && stateTimer>=3 && Main.deaths!=20){
                Yes.setVisible(true);
                No.setVisible(true);
            }
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) { // I explained this in slides (.pptx file)
        if(Main.hit==1 && Main.deaths!=20 || Main.hit==2 && Main.deaths!=20) {
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
        game.dispose();
        music.dispose();
        label.dispose();
        batch.dispose();
        atlas.dispose();
        if (Main.hit==1 && Main.deaths!=20 || Main.hit==2 && Main.deaths!=20) {
            if (Main.hit==2) gameOver.dispose();
            stage.dispose();
            skin.dispose();
        }
    }
}
