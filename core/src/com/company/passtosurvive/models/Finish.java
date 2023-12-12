package com.company.passtosurvive.models;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.company.passtosurvive.view.Main;


public class Finish extends TileObject{
    public Finish(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this); // добавляем чтобы WorldContactListener смог распознать
    }

    @Override
    public void hit() {
        Main.hit=3;
    } // каждый наследник ставит разное число
}
