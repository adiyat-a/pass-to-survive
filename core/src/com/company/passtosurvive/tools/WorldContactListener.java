package com.company.passtosurvive.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.company.passtosurvive.models.TileObject;
import com.company.passtosurvive.view.Main;
import com.company.passtosurvive.view.PlayGameScreen;

public class WorldContactListener implements ContactListener { // Serves as a Collision Listener for two objects in the game
    private PlayGameScreen screen; // I created Human in PlayGameScreen so that there would be one listener for all level screens
    public WorldContactListener(PlayGameScreen screen) {
        this.screen = screen;
    }
    @Override
    public void beginContact(Contact contact) { // the start of collision
        Fixture fixA=contact.getFixtureA(); // first object
        Fixture fixB=contact.getFixtureB(); // second object
        if(fixA.getUserData()=="Human" || fixB.getUserData()=="Human"){ // check if one of the objects is player
            Fixture Human=fixA.getUserData()=="Human"? fixA:fixB; // which one is player
            Fixture object= Human==fixA? fixB:fixA; // which one is not player
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) { // is the object equal to nothing and does it inherit from TileObject
                ((TileObject) object.getUserData()).hit(); // set Main.hit value
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
                screen.human.isDead(object.getUserData() != null && TileObject.class.isAssignableFrom(object.getUserData().getClass())); // pass the value that the player is in contact
            }
        }
        if(fixA.getUserData()=="HumanHead" || fixB.getUserData()=="HumanHead"){ // check for collision of one object with the player's head
            Fixture Human=fixA.getUserData()=="HumanHead"? fixA:fixB;
            Fixture object= Human==fixA? fixB:fixA;
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                screen.human.isHead(object.getUserData() != null && TileObject.class.isAssignableFrom(object.getUserData().getClass()));
            }
        }
    }
    @Override
    public void endContact(Contact contact) { // end of collision
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        if(fixA.getUserData()=="Human" || fixB.getUserData()=="Human"){
            Fixture Human=fixA.getUserData()=="Human"? fixA:fixB;
            Fixture object= Human==fixA? fixB:fixA;
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                if(Main.PreviousBouncers){ // if the player was in contact with the trampoline, now he is no longer
                    Main.PreviousBouncers=false;
                }
                screen.human.isDead(object.getUserData() != null && TileObject.class.isAssignableFrom(object.getUserData().getClass()));
            }
        }
        if(fixA.getUserData()=="HumanHead" || fixB.getUserData()=="HumanHead"){
            Fixture Human=fixA.getUserData()=="HumanHead"? fixA:fixB;
            Fixture object= Human==fixA? fixB:fixA;
            if(object.getUserData()!=null && TileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                if(screen.human.Head()){ // if the player's head was in contact then now it's not
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
