package com.myplatformergdx.game.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.myplatformergdx.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
//        Speed and direction where enemy is moving
        velocity = new Vector2(1, 0);
//        Put body default to sleep
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();

    //    Public to contact from WorldContactListener
    public abstract void hitOnHead();

    //If x or y is true it will reverse velocity
    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    public abstract void update(float dt);
}
