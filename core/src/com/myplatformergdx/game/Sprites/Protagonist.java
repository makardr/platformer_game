package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.myplatformergdx.game.MyPlatformerGame;

public class Protagonist extends Sprite {
    public World world;
    public Body b2body;

    public Protagonist(World world) {
        this.world = world;
        defineProtagonist();
    }

    public void defineProtagonist() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyPlatformerGame.PPM, 32 / MyPlatformerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MyPlatformerGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
