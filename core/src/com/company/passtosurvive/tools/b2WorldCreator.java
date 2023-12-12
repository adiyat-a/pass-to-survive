package com.company.passtosurvive.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.company.passtosurvive.models.Bouncers;
import com.company.passtosurvive.models.CheckPoint;
import com.company.passtosurvive.models.Finish;
import com.company.passtosurvive.models.NextPart;
import com.company.passtosurvive.models.Ground;
import com.company.passtosurvive.models.Lava;
import com.company.passtosurvive.models.NextFloor;
import com.company.passtosurvive.models.Spikes;
import com.company.passtosurvive.view.Level1ScreenPart1;
import com.company.passtosurvive.view.Level1ScreenPart2;
import com.company.passtosurvive.view.Level2ScreenFloor1;
import com.company.passtosurvive.view.Level2ScreenFloor2;

public class b2WorldCreator { // создает объекты из карт для world и его листенера, в левел экранах есть Box2DDebugRenderer он отрисовывает эти объекты
    public b2WorldCreator(World world, TiledMap map, Level1ScreenPart1 screen){
        Body body;
        // создаем землю
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Ground(world, map, rect);
        }
        // создаем лаву
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Lava(world, map, rect);
        }
        // создаем шипы
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Spikes(world, map, rect);
        }
        // создаем шипы
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Spikes(world, map, rect);
        }
        // создаем объект служащий триггером для перехода на след. часть уровня
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new NextPart(world, map, rect);
        }
        // создаем чекпоинты
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new CheckPoint(world, map, rect);
        }
    }
    public b2WorldCreator(World world, TiledMap map, Level1ScreenPart2 screen){
        Body body;
        // создаем землю
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Ground(world, map, rect);
        }
        // создаем шипы
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Spikes(world, map, rect);
        }
        // создаем сундук
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Finish(world, map, rect);
        }
        // создаем чекпоинты
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new CheckPoint(world, map, rect);
        }
        // создаем лаву
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Lava(world, map, rect);
        }
    }
    public b2WorldCreator(World world, TiledMap map, Level2ScreenFloor1 screen){
        BodyDef bDef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fDef=new FixtureDef();
        Body body;
        // создаем батуты
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Bouncers(world, map, rect);
        }
        // создаем землю
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Ground(world, map, rect);
        }
        // создаем лаву
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Lava(world, map, rect);
        }
        // создаем объект служащий для запуска скрипта (в Level2ScreenFloor1)
        for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new NextFloor(world, map, rect);
        }
        // создаем чекпоинты
        for(MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new CheckPoint(world, map, rect);
        }
        // создаем объект служащий триггером для перехода на след. часть уровня
        for(MapObject object: map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new NextPart(world, map, rect);
        }
    }
    public b2WorldCreator(World world, TiledMap map, Level2ScreenFloor2 screen){
        BodyDef bDef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fDef=new FixtureDef();
        Body body;
        // создаем батуты
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Bouncers(world, map, rect);
        }
        // создаем землю
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Ground(world, map, rect);
        }
        // создаем лаву
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Lava(world, map, rect);
        }
        // создаем финиш
        for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new Finish(world, map, rect);
        }
        // создаем чекпоинты
        for(MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            new CheckPoint(world, map, rect);
        }
    }
}
