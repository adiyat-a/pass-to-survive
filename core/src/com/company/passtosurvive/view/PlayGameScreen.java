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

public abstract class PlayGameScreen implements Screen { // Наследники этого класса только левел скрины
    public Human human; // создаю Human здесь для WorldContactListener
    public abstract void handle(); // метод для контроля над моделькой игрока
    public abstract void update(float dt); // нужен для обновления текстурки спрайта Human, камеры и т.д.
    public abstract void collision(); // нужен для запуска скрипта в случае столкновения с определенным объект в игре
}
