package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Screens.PlayScreen;

public class Protagonist extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion marioStand;

    public Protagonist(World world, PlayScreen screen) {
        //        find texture region for getTexture in later use
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineProtagonist();
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
//        Texture render size
        setBounds(0, 0, 16 / MyPlatformerGame.PPM, 16 / MyPlatformerGame.PPM);
//        Texture region associated with the sprite
        setRegion(marioStand);


    }

    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
    }
    public void defineProtagonist() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyPlatformerGame.PPM, 32 / MyPlatformerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyPlatformerGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
