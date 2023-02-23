package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Scenes.Hud;
import com.myplatformergdx.game.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyPlatformerGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(MyPlatformerGame.DESTROYED_BIT);
//        Remove tile texture
        getCell().setTile(null);
        Hud.addScore(200);
        MyPlatformerGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }

}
