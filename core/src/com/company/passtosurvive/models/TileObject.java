package com.company.passtosurvive.models;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.company.passtosurvive.view.Main;

public abstract class TileObject { // от этого класса наследуются все объекты для world и его листенера кроме Human
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    public TileObject(World world, TiledMap map, Rectangle bounds) { // всё берется из b2WorldCreator
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        BodyDef bDef=new BodyDef();
        FixtureDef fDef=new FixtureDef();
        PolygonShape shape=new PolygonShape();
        bDef.type=BodyDef.BodyType.StaticBody; // ставим тип тела
        bDef.position.set((bounds.getX()+bounds.getWidth()/2)/ Main.PPM, (bounds.getY()+bounds.getHeight()/2)/Main.PPM); // ставим позицию для него
        body=world.createBody(bDef); // добавление в world
        shape.setAsBox((bounds.getWidth()/2)/Main.PPM, (bounds.getHeight()/2)/Main.PPM); // ставим размеры
        fDef.shape=shape;
        fixture = body.createFixture(fDef); // добавляем объекту форму
    }
    public abstract void hit(); // для определения какой объект в WorldContactListener
    public float getX(){
        return body.getPosition().x;
    } // для чекпоинта
    public float getY(){
        return body.getPosition().y;
    } // для чекпоинта
}
