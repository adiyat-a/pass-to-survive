package com.company.passtosurvive.models;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.company.passtosurvive.view.Main;

public class Human extends Sprite { // моедлька игрока есть в каждом левел скрине
    public enum State { JUMPING, RUNNING, STANDING}; // статусы нужны для смены текстуры человека
    private BodyDef bDef;
    public State currentState; // нужны два статуса чтобы не было проблем с анимацией и чтобы определять статус в разных ситуациях
    public State previousState;
    public World world;
    public Body HumanBody;
    private TextureAtlas atlas;
    private TextureRegion humanJump;
    private Animation humanRun, humanStand;
    private float stateTime;
    private boolean runningRight, Dead, Head;
    public Human(World world, float x, float y){
        atlas=new TextureAtlas("Human.pack");
        this.world=world;
        currentState=State.STANDING;
        previousState=State.STANDING;
        stateTime=0;
        Dead=false;
        runningRight=true; // всегда по умолчанию моделька игрока будет в правую сторону смотреть
        Array<TextureRegion> frames=new Array<TextureRegion>();
        if(Main.deaths<20) {
            frames.add(atlas.findRegion("humanrun1"));
            frames.add(atlas.findRegion("humanrun2"));
            humanRun = new Animation(0.2f, frames);
            frames.clear();
            frames.add(atlas.findRegion("humanstay"));
            frames.add(atlas.findRegion("humanstay1"));
            frames.add(atlas.findRegion("humanstay2"));
            humanStand = new Animation(0.25f, frames);
            frames.clear();
            humanJump = new TextureRegion(atlas.findRegion("humanfall"), 0, 0, 78, 93); // для прыжка и падения у меня один кадр поэтому это не анимация
        }
        else{
            frames.add(atlas.findRegion("deadrun1"));
            frames.add(atlas.findRegion("deadrun2"));
            humanRun = new Animation(0.2f, frames);
            frames.clear();
            frames.add(atlas.findRegion("deadstay"));
            frames.add(atlas.findRegion("deadstay1"));
            frames.add(atlas.findRegion("deadstay2"));
            humanStand = new Animation(0.25f, frames);
            frames.clear();
            humanJump = new TextureRegion(atlas.findRegion("deadfall"), 0, 0, 78, 93);
        }
        setBounds(0 ,0, 63/(1.4f*Main.PPM), 72/(1.4f*Main.PPM)); // форма спрайта
        setPosition(x, y);
        defineHuman();
    }
    public void isHead(boolean Head){ this.Head=Head; }
    public boolean Head(){ return this.Head;}
    public void isDead(boolean Dead){
        this.Dead=Dead;
    }
    public boolean Dead(){
        return Dead;
    }
    public void update(float dt){ // нужен для того чтобы спрайт двигался за телом самого человека в world чтобы был притянут к нему
        setPosition(HumanBody.getPosition().x-getWidth()/2, HumanBody.getPosition().y-getHeight()/2.3f);
        setRegion(getFrame(dt)); // обновление текстуры
    }
    public TextureRegion getFrame(float dt){
        currentState=getState(); // получаем статус чтобы потом от него отталкиватся
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region=humanJump;
                break;
            case RUNNING:
                region=(TextureRegion) humanRun.getKeyFrame(stateTime, true);
                break;
            case STANDING:
            default:
                region=(TextureRegion) humanStand.getKeyFrame(stateTime, true);
                break;
        }
        // эти условия нужны для того чтобы спрайт отзеркаливался
        if((HumanBody.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight=false;
        }
        else if((HumanBody.getLinearVelocity().x>0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight=true;
        }
        stateTime =currentState==previousState ? stateTime +dt : 0; // нужно чтобы не было проблем с анимацией
        previousState=currentState;
        return region;
    }
    public State getState() {
        if (HumanBody.getLinearVelocity().y > 0 || HumanBody.getLinearVelocity().y < 0) return State.JUMPING;
        else if (HumanBody.getLinearVelocity().x != 0) return State.RUNNING;
        else if (HumanBody.getLinearVelocity().x == 0 && HumanBody.getLinearVelocity().y == 0) return State.STANDING;
        else return State.STANDING;
    }
    public void defineHuman(){
        bDef=new BodyDef();
        bDef.position.set(getX(), getY()); // ставим позицию
        bDef.type=BodyDef.BodyType.DynamicBody; // ставим тип
        HumanBody=world.createBody(bDef); // добавляем
        FixtureDef fDef=new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox((23/2)/Main.PPM, (43/2)/Main.PPM); // ставим размеры
        fDef.shape=shape;
        HumanBody.createFixture(fDef); // добавляем
        EdgeShape Human=new EdgeShape(); // edgeshape нам нужен чтобы сделать типо как стенки вокруг тела HumanBody
        Human.set(new Vector2(-12f/Main.PPM, -22/Main.PPM), new Vector2(12f/Main.PPM, -22/Main.PPM)); // нижняя стенка
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("Human");
        Human.set(new Vector2(12f/Main.PPM, -22/Main.PPM), new Vector2(12f/Main.PPM, 22/Main.PPM)); // боковая стенка
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("Human");
        Human.set(new Vector2(-12f/Main.PPM, -22/Main.PPM), new Vector2(-12f/Main.PPM, 22/Main.PPM)); // боковая стенка
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("Human");
        Human.set(new Vector2(-10.1f/Main.PPM, 23/Main.PPM), new Vector2(10.1f/Main.PPM, 23/Main.PPM)); // вверхняя стенка, она не будет служить триггером для GameOver
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("HumanHead");
    }
}
