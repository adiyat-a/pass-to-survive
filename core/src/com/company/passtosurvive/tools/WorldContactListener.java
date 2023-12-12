package com.company.passtosurvive.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.company.passtosurvive.models.TileObject;
import com.company.passtosurvive.view.Main;
import com.company.passtosurvive.view.PlayGameScreen;

public class WorldContactListener implements ContactListener { // Служит Слушателем столкновений двух объектов в игре
    private PlayGameScreen screen; // я создал Human в PlayGameScreen чтобы для всех левел экранов был один листенер
    public WorldContactListener(PlayGameScreen screen) {
        this.screen = screen;
    }
    @Override
    public void beginContact(Contact contact) { // начало столкновения
        Fixture fixA=contact.getFixtureA(); // первый объект
        Fixture fixB=contact.getFixtureB(); // второй объект
        if(fixA.getUserData()=="Human" || fixB.getUserData()=="Human"){ // проверка столкновения одного объекта с игроком
            Fixture Human=fixA.getUserData()=="Human"? fixA:fixB; // какой из них игрок
            Fixture object= Human==fixA? fixB:fixA; // какой из них не игрок
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) { // не равен ли объект ничему и наследуется ли он от TileObject
                ((TileObject) object.getUserData()).hit(); // задаем Main.hit значение
                if(Main.hit==6){
                    Main.PreviousBouncers=true;
                }
                if(Main.hit==8){
                    Main.nextFloor=true;
                }
                else if (Main.hit != 8){
                    Main.nextFloor=false;
                }
                if(Main.hit==4){
                    Main.HumanXCheckpoint=((TileObject) object.getUserData()).getX();
                    Main.HumanYCheckpoint=((TileObject) object.getUserData()).getY();
                }
                screen.human.isDead(object.getUserData() != null && TileObject.class.isAssignableFrom(object.getUserData().getClass())); // передаем значение то что игрок в контакте
            }
        }
        if(fixA.getUserData()=="HumanHead" || fixB.getUserData()=="HumanHead"){ // проверка столкновения одного объекта с головой игрока
            Fixture Human=fixA.getUserData()=="HumanHead"? fixA:fixB;
            Fixture object= Human==fixA? fixB:fixA;
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                screen.human.isHead(object.getUserData() != null && TileObject.class.isAssignableFrom(object.getUserData().getClass()));
            }
        }
    }
    @Override
    public void endContact(Contact contact) { // конец столкновения
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        if(fixA.getUserData()=="Human" || fixB.getUserData()=="Human"){
            Fixture Human=fixA.getUserData()=="Human"? fixA:fixB;
            Fixture object= Human==fixA? fixB:fixA;
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                if(Main.PreviousBouncers){ // если игрок был в контакте с батутом то сейчас уже нет
                    Main.PreviousBouncers=false;
                }
                screen.human.isDead(object.getUserData() != null && TileObject.class.isAssignableFrom(object.getUserData().getClass()));
            }
        }
        if(fixA.getUserData()=="HumanHead" || fixB.getUserData()=="HumanHead"){
            Fixture Human=fixA.getUserData()=="HumanHead"? fixA:fixB;
            Fixture object= Human==fixA? fixB:fixA;
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                if(screen.human.Head()){ // если голова игрока была в контакте то сейчас нет
                    screen.human.isHead(false);
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
