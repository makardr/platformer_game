package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Scenes.Hud;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    //    Tiled starts counting from zero and libgdx from zero, so to get tile with Id 27 you need to add one and get 28 in the end
    private final int BLANK_COIN = 28;

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MyPlatformerGame.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        if (getCell().getTile().getId() == BLANK_COIN)
            MyPlatformerGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else
            MyPlatformerGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
