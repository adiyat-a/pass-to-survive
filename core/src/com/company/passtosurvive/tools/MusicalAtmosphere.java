package com.company.passtosurvive.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class MusicalAtmosphere extends AssetManager { // used for music and sounds

    public MusicalAtmosphere() { // loading everything
        load("Level1Sound.ogg", Music.class);
        load("Level2Sound.ogg", Music.class);
        load("MainMenuSound.ogg", Music.class);
        load("GameOverSound.wav", Sound.class);
        load("HumanJump.wav", Sound.class);
        load("WinSound.wav", Sound.class);
        load("ghoulSound.wav", Sound.class);
        load("End.wav", Sound.class);
        finishLoading();
    }
    public void MainMenuSoundPLay(){
        get("MainMenuSound.ogg", Music.class).setVolume(0.4f);
        get("MainMenuSound.ogg", Music.class).setLooping(true);
        get("MainMenuSound.ogg", Music.class).play();
    }
    public boolean MainMenuSoundIsPlaying(){
        return get("MainMenuSound.ogg", Music.class).isPlaying();
    }
    public void Level1SoundPLay(){
        get("Level1Sound.ogg", Music.class).setVolume(0.4f);
        get("Level1Sound.ogg", Music.class).setLooping(true);
        get("Level1Sound.ogg", Music.class).play();
    }
    public void Level2SoundPlay(){
        get("Level2Sound.ogg", Music.class).setVolume(0.4f);
        get("Level2Sound.ogg", Music.class).setLooping(true);
        get("Level2Sound.ogg", Music.class).play();
    }
    public void JumpSoundPLay(){
        get("HumanJump.wav", Sound.class).play();
    }
    public void GameOverSoundPLay(){
        get("GameOverSound.wav", Sound.class).play();
    }
    public void WinSoundPlay(){
        get("WinSound.wav", Sound.class).play();
    }
    public void GhoulSoundPlay(){get("ghoulSound.wav", Sound.class).play();}
    public void MainMenuSoundStop(){
        get("MainMenuSound.ogg", Music.class).stop();
    }
    public void Level1SoundStop(){
        get("Level1Sound.ogg", Music.class).stop();
    }
    public void Level2SoundStop(){
        get("Level2Sound.ogg", Music.class).stop();
    }
    public void EndSoundPlay(){get("End.wav", Sound.class).play();}
}
