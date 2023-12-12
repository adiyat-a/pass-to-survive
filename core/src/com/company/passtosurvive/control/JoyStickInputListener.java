package com.company.passtosurvive.control;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.company.passtosurvive.control.JoyStick;

public class JoyStickInputListener extends InputListener {
    private JoyStick joyStick;

    public JoyStickInputListener(JoyStick joyStick) {
        this.joyStick = joyStick;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { // запускается когда на джойстик нажали
        joyStick.setTouched();
        joyStick.changeCursor(x, y);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) { // запускается когда джойстик отпустили
        joyStick.setUnTouched();
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) { // запускается когда джойстик зажали и перетягивают его курсор
        joyStick.changeCursor(x, y);
        if(joyStick.isJoyStickDown()){ // проверка нажатия
            joyStick.handleChangeListener();
        }
    }
}
