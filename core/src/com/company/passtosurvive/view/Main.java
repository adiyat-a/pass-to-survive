package com.company.passtosurvive.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class Main extends Game { // самый первый класс при запуске
	public static float width, height; // размеры экрана на котором запускаем
	public static final float PPM=100; // для карт
	public static int hit; // для определенние с чем столкнулся игрок
	public static float HumanX=0; // для сохранения координат игрока после паузы
	public static float HumanY=0;
	public static float HumanXCheckpoint=0; // для сохранения координат чекпоинта
	public static float HumanYCheckpoint=0;
	public static float Human2Y=0; // для сохранения позиции Y при переходе на 2 часть 1 уровня
	public static float worldWidth, worldHeight; // для оптимизации карты под экран
	public static boolean soundIsOn=true; // для проверки включена ли музыка в настройках
	public static boolean infoIsPressed=false; // нажата ли кнопка info
	public static boolean PreviousBouncers=false; // для батутов во 2 уровне
	public static boolean nextFloor=false; // для запуска анимации во 2 уровне 1 части
	public static boolean chooseLevel=false; // для проверки нажата ли кнопка play
	public static boolean level1IsFinished=false; // для проверки пройден ли уровень
	public static boolean level2IsFinished=false;
	public static int deaths=0; // для подсчета кол-ва смертей
	public static int screen; // для определения какой левел экран был
	public static Vector2 v; // чтобы не создавать каждый раз в левле экранах
	public static Animation animation; // чтобы не создавать каждый раз
	@Override
	public void create() {
		v=new Vector2();
		setScreen(new MainMenuScreen(this));
	}
	@Override
	public void render() {
		super.render();
	}
}
