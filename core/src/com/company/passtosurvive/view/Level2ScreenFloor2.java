package com.company.passtosurvive.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.passtosurvive.control.PlayButtons;
import com.company.passtosurvive.models.Human;
import com.company.passtosurvive.tools.MusicalAtmosphere;
import com.company.passtosurvive.tools.WorldContactListener;
import com.company.passtosurvive.tools.b2WorldCreator;
public class Level2ScreenFloor2 extends PlayGameScreen { // уровень 2 часть 2 запускается когда игрок сталкивается с определенным объектом в 1 части
    final Main game;
    private MusicalAtmosphere music;
    private PlayButtons buttons;
    private SpriteBatch batch;
    private Texture background; // нужен чтобы человека не было видно когда тот идет к финишу
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private TmxMapLoader mapLoader;
    private OrthographicCamera cam;
    private Viewport mapPort;
    public Level2ScreenFloor2(final Main game) {
        this.game = game;
        batch=new SpriteBatch();
        Main.screen=4;
        music=new MusicalAtmosphere();
        background=new Texture("mapBackground.png");
        buttons = new PlayButtons();
        cam = new OrthographicCamera();
        if(Main.width==1794 && Main.height==1080){ // объяснил в презентации, почему я так сделал
            Main.worldHeight=672f;
            Main.worldWidth=1056;
        }
        else {
            Main.worldHeight=672f;
            Main.worldWidth=1056/((16/9f)/(Main.width/Main.height)/1.1f);
        }
        mapPort = new FitViewport(Main.worldWidth / Main.PPM, Main.worldHeight / Main.PPM, cam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map4.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Main.PPM); // почти все величины связаные с картой я делю на PPM чтобы не было проблем с физикой
        cam.position.set(mapPort.getWorldWidth() / 2, mapPort.getWorldHeight() / 2, 0);
        Main.v.set(0, -21); // я не создал new vector2 т.к. из-за этого будет лишнее выделение в памяти
        world = new World(Main.v, true);
        if(Main.HumanX!=0 && Main.HumanY!=0){
            human = new Human(world,Main.HumanX + 0.225f, Main.HumanY + 0.3f); // увеличиваю из-за того что игрок спавнится не ровно по центру
        }
        else if (Main.HumanX == 0 && Main.HumanY == 0 && Main.HumanYCheckpoint == 0 && Main.HumanXCheckpoint == 0) {
            human = new Human(world, 14272/ Main.PPM, 704 / Main.PPM);
        }
        else if(Main.HumanX==0 && Main.HumanY==0){
            human = new Human(world, Main.HumanXCheckpoint, Main.HumanYCheckpoint+0.3f); // увеличиваю Y на 0.3f чтобы игрок спавнился чуть выше чем сам чекпоинт
        }
        b2dr = new Box2DDebugRenderer();
        new b2WorldCreator(world, map, this);
        world.setContactListener(new WorldContactListener(this));
        buttons.pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.HumanX=human.getX();
                Main.HumanY=human.getY();
                music.Level2SoundStop();
                dispose();
                game.setScreen(new PauseScreen(game));
            }
        });
        if (Main.soundIsOn) {
            music.Level2SoundPlay();
        }
        else{
            music.Level2SoundStop();
        }
    }
    @Override
    public void show() { }

    @Override
    public void handle() {
        if (buttons.jump.isPressed() && Main.hit!=6 && !Main.PreviousBouncers && human.HumanBody.getLinearVelocity().y == 0 && !human.Head()) { // 6 означает батут
            Main.v.set(0, 7f);
            human.HumanBody.applyLinearImpulse(Main.v, human.HumanBody.getWorldCenter(), true);
            music.JumpSoundPLay();
        }
        if (buttons.joyStick.isJoyStickDown()) {
            if (buttons.joyStick.getValueX() > 0 && human.HumanBody.getLinearVelocity().x <= 3f) { // 3f это макс. скорость 0.3f как разгон (ускорение)
                Main.v.set(0.3f, 0);
                human.HumanBody.applyLinearImpulse(Main.v, human.HumanBody.getWorldCenter(), true);
            } else if (buttons.joyStick.getValueX() < 0 && human.HumanBody.getLinearVelocity().x >= -3f) {
                Main.v.set(-0.3f, 0);
                human.HumanBody.applyLinearImpulse(Main.v, human.HumanBody.getWorldCenter(), true);
            }
        }
        if(!buttons.joyStick.isJoyStickDown() && human.HumanBody.getLinearVelocity().x!=0){ // нужно чтобы игрок сразу остановился после отпускания джойстика
            Main.v.set(-human.HumanBody.getLinearVelocity().x, 0);
            human.HumanBody.applyLinearImpulse(Main.v, human.HumanBody.getWorldCenter(), true);
        }
    }

    @Override
    public void update(float dt) {
        handle();
        world.step(1 / 60f, 6, 2);
        cam.position.x = human.HumanBody.getPosition().x; // камера движется за игроком
        human.update(dt); // обновляем спрайт
        cam.update();
        renderer.setView(cam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1); // отчистка
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // отчистка
        renderer.render();
        b2dr.render(world, cam.combined); // вот это можете отключить если не хотите видеть зеленую обводку вокруг объектов world
        batch.setProjectionMatrix(cam.combined);
        collision();
        batch.begin();
        human.draw(batch);
        batch.draw(background, -127, -375); // это текстура находится около финиша
        batch.end();
        buttons.stage.act(delta);
        buttons.stage.draw();
    }

    @Override
    public void collision() {
        if (human.Dead() && Main.hit == 1) { // 1 означает лаву
            Main.deaths++;
            music.Level2SoundStop();
            dispose();
            game.setScreen(new DeadScreen(game));
        }
        if (human.Dead() && Main.hit == 3) { // 3 означает финиш
            Main.level2IsFinished=true;
            music.Level2SoundStop();
            dispose();
            game.setScreen(new WinScreen(game));
        }
        if (Main.hit == 6 && Main.PreviousBouncers && human.HumanBody.getLinearVelocity().y == 0 && !human.Head()) { // 6 означает батуты, previousBouncers нам нужен чтобы не было проблем с батутами
            Main.v.set(0, 8f);
            human.HumanBody.applyLinearImpulse(Main.v, human.HumanBody.getWorldCenter(), true);
            music.JumpSoundPLay();
        }
    }

    @Override
    public void resize(int width, int height) {
        mapPort.update(width, height);
        buttons.update(width, height);
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
        map.dispose();
        batch.dispose();
        game.dispose();
        music.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        background.dispose();
        buttons.dispose();
    }
}
