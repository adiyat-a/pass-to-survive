package com.company.passtosurvive.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class JoyStick extends Actor {
    private Texture JoyStick;
    private Texture curJoyStick;
    private boolean isJoyStickDown;
    private float radius;
    private float CUR_RADIUS;
    private float CurJoyStickX;
    private float CurJoyStickY;
    private float inverseRadius;
    private float valueX;
    private float valueY;
    private List<JoyStickChangedListener> listeners=new ArrayList<JoyStickChangedListener>();
    public float getValueX() {
        return valueX;
    } // valueX we need to do using the human control joystick
    public boolean isJoyStickDown(){
        return isJoyStickDown;
    }
    public void handleChangeListener(){ // needed when the player drags the cursor
        for(JoyStickChangedListener listener: listeners){
            listener.changed(valueX, valueY);
        }
    }
    public JoyStick(){
        isJoyStickDown=false;
        CurJoyStickX=0;
        CurJoyStickY=0;
        inverseRadius=0;
        valueX=0;
        valueY=0;
        this.JoyStick=new Texture(Gdx.files.internal("JoyStick.png"));
        this.curJoyStick=new Texture(Gdx.files.internal("curJoyStick.png"));
        addListener(new JoyStickInputListener(this));
    }
    public void setTouched(){
        isJoyStickDown=true;
    }
    public void setUnTouched(){
        isJoyStickDown=false;
    }
    @Override
    public Actor hit(float x, float y, boolean touchable){ // here we check whether our finger is in the joystick field
        Actor actor=super.hit(x, y, touchable);
        if(actor==null) return null;
        else{
            float dx=x-radius;
            float dy=y-radius;
            return (dx*dx+dy*dy<=radius*radius)? this: null; // if yes then return actor if not then nothing
        }
    }
    public void changeCursor(float x, float y){ // x y we get from the listener, these are the coordinates where the joystick was pressed
        float changeX=x-radius; // how much the joystick coordinates have been changed
        float changeY=y-radius;
        float length=(float) Math.sqrt(changeX*changeX+changeY*changeY); // calculate the length from the center of the joystick to the point where the player pressed
        if(length<radius){ // will run if the length from the center to the point where the player pressed is less than the radius
            this.CurJoyStickX=changeX;
            this.CurJoyStickY=changeY;
        }
        else{ // thanks to this, the cursor will not leave the joystick field
            float k=radius/length;
            this.CurJoyStickX=changeX*k;
            this.CurJoyStickY=changeY*k;
        }
        valueX=CurJoyStickX*inverseRadius; // if the player pulled the cursor to the right then valueX is greater than 0, if to the left then vice versa
        valueY=CurJoyStickY*inverseRadius;
    }
    public void setSP(){ // set size
        setX(140);
        setY(40);
        setWidth(400);
        setHeight(400);
        radius=200;
        CUR_RADIUS=90;
    }
    public void setWidth(float width){ // set it so that the joystick is round
        super.setWidth(width);
        super.setHeight(width);
        radius=width/2;
        inverseRadius=1/radius; // we will need the inverse radius to later calculate ValueX and Y
    }
    public void setHeight(float height){ // set it so that the joystick is round
        super.setWidth(height);
        super.setHeight(height);
        radius=height/2;
        inverseRadius=1/radius;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) { // stage will run this method using its draw method
        batch.draw(JoyStick, this.getX(), this.getY(), this.getWidth(), this.getHeight()); // joystick is static
        if(isJoyStickDown){ // needed to draw the cursor in the place where the joystick was pressed
            batch.draw(curJoyStick, this.getX()+radius-CUR_RADIUS+CurJoyStickX, this.getY()+radius-CUR_RADIUS+CurJoyStickY, 2*CUR_RADIUS, 2*CUR_RADIUS);
        }
        else{
            batch.draw(curJoyStick, this.getX()+radius-CUR_RADIUS, this.getY()+radius-CUR_RADIUS, 2*CUR_RADIUS, 2*CUR_RADIUS);
        }
    }
}
