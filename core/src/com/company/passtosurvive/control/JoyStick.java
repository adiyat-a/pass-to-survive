package com.company.passtosurvive.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class JoyStick extends Actor {
    private Texture JoyStick; // джойстик
    private Texture curJoyStick; // курсор джойстика
    private boolean isJoyStickDown; // проверяет нажат ли джойстик
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
    } // valueX нам понадобится чтобы сделать с помощью джойстика управление человека
    public boolean isJoyStickDown(){
        return isJoyStickDown;
    }
    public void handleChangeListener(){ // нужен во время того как игрок перетягивает курсор
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
    public Actor hit(float x, float y, boolean touchable){ // здесь мы проверяем попадает ли наш палец в поле джойстика
        Actor actor=super.hit(x, y, touchable);
        if(actor==null) return null;
        else{
            float dx=x-radius;
            float dy=y-radius;
            return (dx*dx+dy*dy<=radius*radius)? this: null; // если да то возвращаем actor если нет то ничего
        }
    }
    public void changeCursor(float x, float y){ // x y мы получаем от листенера, это координаты где был нажат джойстик
        float changeX=x-radius; // насколько координаты джойстика были изменены
        float changeY=y-radius;
        float length=(float) Math.sqrt(changeX*changeX+changeY*changeY); // вычисляем длину от центра джойстика до точки куда нажал игрок
        if(length<radius){ // запустится если длина от центра до точки куда нажал игрок меньше радиуса
            this.CurJoyStickX=changeX;
            this.CurJoyStickY=changeY;
        }
        else{ // благодаря этому крусор не выйдет из поля джойстика
            float k=radius/length;
            this.CurJoyStickX=changeX*k;
            this.CurJoyStickY=changeY*k;
        }
        valueX=CurJoyStickX*inverseRadius; // если игрок потянул курсор в право то valueX больше 0 если в лево то наоборот
        valueY=CurJoyStickY*inverseRadius;
    }
    public void setSP(){ // ставим размеры
        setX(140);
        setY(40);
        setWidth(400);
        setHeight(400);
        radius=200;
        CUR_RADIUS=90;
    }
    public void setWidth(float width){ // ставим так чтобы джойстик был круглым
        super.setWidth(width);
        super.setHeight(width);
        radius=width/2;
        inverseRadius=1/radius; // инверс радиус нам понадобится чтобы потом вычислить ValueX и Y
    }
    public void setHeight(float height){ // ставим так чтобы джойстик был круглым
        super.setWidth(height);
        super.setHeight(height);
        radius=height/2;
        inverseRadius=1/radius;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) { // stage будет запускать этот метод с помощью своего метода draw
        batch.draw(JoyStick, this.getX(), this.getY(), this.getWidth(), this.getHeight()); // джойстик статичен
        if(isJoyStickDown){ // нужен для отрисовки курсора в том месте где нажали на джойстик
            batch.draw(curJoyStick, this.getX()+radius-CUR_RADIUS+CurJoyStickX, this.getY()+radius-CUR_RADIUS+CurJoyStickY, 2*CUR_RADIUS, 2*CUR_RADIUS);
        }
        else{
            batch.draw(curJoyStick, this.getX()+radius-CUR_RADIUS, this.getY()+radius-CUR_RADIUS, 2*CUR_RADIUS, 2*CUR_RADIUS);
        }
    }
}
