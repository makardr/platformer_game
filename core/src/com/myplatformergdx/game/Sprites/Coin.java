package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.myplatformergdx.game.Items.ItemDef;
import com.myplatformergdx.game.Items.Mushroom;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Scenes.Hud;
import com.myplatformergdx.game.Screens.PlayScreen;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    //    Tiled starts counting from zero and libgdx from zero, so to get tile with Id 27 you need to add one and get 28 in the end
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MyPlatformerGame.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        if (getCell().getTile().getId() == BLANK_COIN) {
            MyPlatformerGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        } else {
            MyPlatformerGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
//        Spawn item above the coin that mario hits
            try {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / MyPlatformerGame.PPM), Mushroom.class));
            } catch (Exception e){
                Gdx.app.log("COIN", e.getMessage());
                e.printStackTrace();
            }

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
