package com.company.passtosurvive.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.passtosurvive.control.PlayButtons;
import com.company.passtosurvive.models.Human;
import com.company.passtosurvive.tools.MusicalAtmosphere;

public abstract class PlayGameScreen implements Screen { // Heirs of this class are only level screens
    public Human human; // create Human here for WorldContactListener
    public abstract void handle(); // method for controlling the player model
    public abstract void update(float dt); // needed to update the textures of the Human sprite, camera, etc.
    public abstract void collision(); // needed to run the script in case of a collision with a certain object in the game
}
