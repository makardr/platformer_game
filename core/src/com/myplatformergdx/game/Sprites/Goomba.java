package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Screens.PlayScreen;

public class Goomba extends Enemy {
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
//        create frames
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
//            Texture region, x, y
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / MyPlatformerGame.PPM, 16 / MyPlatformerGame.PPM);
    }

    public void update(float dt) {
        stateTime += dt;
//        -getWidth()/2 to offset sprite by half the width of the sprite, same with height
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyPlatformerGame.PPM, 32 / MyPlatformerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

//        Define fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyPlatformerGame.PPM);
//        Set category
        fdef.filter.categoryBits = MyPlatformerGame.ENEMY_BIT;
//        What protagonist can collide with
        fdef.filter.maskBits = MyPlatformerGame.GROUND_BIT | MyPlatformerGame.COIN_BIT | MyPlatformerGame.BRICK_BIT | MyPlatformerGame.ENEMY_BIT | MyPlatformerGame.OBJECT_BIT| MyPlatformerGame.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
