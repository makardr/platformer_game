package com.myplatformergdx.game.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Screens.PlayScreen;
import com.myplatformergdx.game.Sprites.Protagonist;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 16 / MyPlatformerGame.PPM, 16 / MyPlatformerGame.PPM);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineItem();

    public abstract void use(Protagonist mario);

    public void update(float dt) {
        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
    }
//    Every item has an ability to reverse velocity
    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }


    //    Overriding
    public void draw(Batch batch) {
//        Draw if only not destroyed
        if (!destroyed) {
            super.draw(batch);
        }
    }

    //    Box2d object cant be destroyed inside world.step, so it is destroyed in update method instead
    public void destroy() {
        toDestroy = true;
    }
}
