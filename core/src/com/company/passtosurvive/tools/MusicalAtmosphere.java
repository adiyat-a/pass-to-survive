package com.company.passtosurvive.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class MusicalAtmosphere extends AssetManager { // отвечает за музыку и звуки

    public MusicalAtmosphere() { // загрузка всего
        load("18 - Dr. Wily's Castle.ogg", Music.class);
        load("18 - Dr. Wily's Castle (1).ogg", Music.class);
        load("10 - Magnet Man.ogg", Music.class);
        load("30 - Game Over.wav", Sound.class);
        load("HumanJump.wav", Sound.class);
        load("WinSound.wav", Sound.class);
        load("ghoulSound.wav", Sound.class);
        load("End.wav", Sound.class);
        finishLoading();
    }
    public void MainMenuSoundPLay(){
        get("10 - Magnet Man.ogg", Music.class).setVolume(0.4f);
        get("10 - Magnet Man.ogg", Music.class).setLooping(true);
        get("10 - Magnet Man.ogg", Music.class).play();
    }
    public boolean MainMenuSoundIsPlaying(){
        return get("10 - Magnet Man.ogg", Music.class).isPlaying();
    }
    public void Level1SoundPLay(){
        get("18 - Dr. Wily's Castle.ogg", Music.class).setVolume(0.4f);
        get("18 - Dr. Wily's Castle.ogg", Music.class).setLooping(true);
        get("18 - Dr. Wily's Castle.ogg", Music.class).play();
    }
    public void Level2SoundPlay(){
        get("18 - Dr. Wily's Castle (1).ogg", Music.class).setVolume(0.4f);
        get("18 - Dr. Wily's Castle (1).ogg", Music.class).setLooping(true);
        get("18 - Dr. Wily's Castle (1).ogg", Music.class).play();
    }
    public void JumpSoundPLay(){
        get("HumanJump.wav", Sound.class).play();
    }
    public void GameOverSoundPLay(){
        get("30 - Game Over.wav", Sound.class).play();
    }
    public void WinSoundPlay(){
        get("WinSound.wav", Sound.class).play();
    }
    public void GhoulSoundPlay(){get("ghoulSound.wav", Sound.class).play();}
    public void MainMenuSoundStop(){
        get("10 - Magnet Man.ogg", Music.class).stop();
    }
    public void Level1SoundStop(){
        get("18 - Dr. Wily's Castle.ogg", Music.class).stop();
    }
    public void Level2SoundStop(){
        get("18 - Dr. Wily's Castle (1).ogg", Music.class).stop();
    }
    public void EndSoundPlay(){get("End.wav", Sound.class).play();}
}
