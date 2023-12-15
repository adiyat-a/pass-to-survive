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

public class Human extends Sprite { // the player model is in every level screen
    public enum State { JUMPING, RUNNING, STANDING}; // statuses are needed to change the texture of a person
    private BodyDef bDef;
    public State currentState; // two statuses are needed to avoid problems with animation and to determine the status in different situations
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
        runningRight=true; // by default the player model will always look to the right
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
            humanJump = new TextureRegion(atlas.findRegion("humanfall"), 0, 0, 78, 93); // for jumping and falling I have one frame so this is not animation
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
    public void update(float dt){ // needed for the sprite to move behind the body of the person in the world so that it is attracted to him
        setPosition(HumanBody.getPosition().x-getWidth()/2, HumanBody.getPosition().y-getHeight()/2.3f);
        setRegion(getFrame(dt)); // texture update
    }
    public TextureRegion getFrame(float dt){
        currentState=getState(); // get the status so that we can build on it later
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
        // these conditions are needed for the sprite to be mirrored
        if((HumanBody.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight=false;
        }
        else if((HumanBody.getLinearVelocity().x>0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight=true;
        }
        stateTime =currentState==previousState ? stateTime +dt : 0; // we need to avoid problems with animation
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
        bDef.position.set(getX(), getY());
        bDef.type=BodyDef.BodyType.DynamicBody;
        HumanBody=world.createBody(bDef);
        FixtureDef fDef=new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox((23/2)/Main.PPM, (43/2)/Main.PPM);
        fDef.shape=shape;
        HumanBody.createFixture(fDef);
        EdgeShape Human=new EdgeShape(); // we need edgeshape to make it look like walls around the HumanBody
        Human.set(new Vector2(-12f/Main.PPM, -22/Main.PPM), new Vector2(12f/Main.PPM, -22/Main.PPM)); // bottom side
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("Human");
        Human.set(new Vector2(12f/Main.PPM, -22/Main.PPM), new Vector2(12f/Main.PPM, 22/Main.PPM)); // side wall
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("Human");
        Human.set(new Vector2(-12f/Main.PPM, -22/Main.PPM), new Vector2(-12f/Main.PPM, 22/Main.PPM)); // side wall
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("Human");
        Human.set(new Vector2(-10.1f/Main.PPM, 23/Main.PPM), new Vector2(10.1f/Main.PPM, 23/Main.PPM)); // the top wall, it will not serve as a trigger for Game Over
        fDef.shape=Human;
        fDef.isSensor=true;
        HumanBody.createFixture(fDef).setUserData("HumanHead");
    }
}
