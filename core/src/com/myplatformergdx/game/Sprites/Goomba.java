package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Screens.PlayScreen;

public class Goomba extends Enemy {
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

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
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;

        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
//        -getWidth()/2 to offset sprite by half the width of the sprite, same with height
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

//        Define fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyPlatformerGame.PPM);
//        Set category
        fdef.filter.categoryBits = MyPlatformerGame.ENEMY_BIT;
//        What protagonist can collide with
        fdef.filter.maskBits = MyPlatformerGame.GROUND_BIT | MyPlatformerGame.COIN_BIT | MyPlatformerGame.BRICK_BIT | MyPlatformerGame.ENEMY_BIT | MyPlatformerGame.OBJECT_BIT | MyPlatformerGame.MARIO_BIT;

        fdef.shape = shape;
//        Set user data so WorldContactListener could determine what is interacting with what
        b2body.createFixture(fdef).setUserData(this);

        //Create head
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / MyPlatformerGame.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / MyPlatformerGame.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / MyPlatformerGame.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / MyPlatformerGame.PPM);
        head.set(vertice);

        fdef.shape = head;
//        Bounciness, 1 is jump up the same height as falling on texture
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = MyPlatformerGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
//      texture only drawn if not destroyed || = or stateTime < 1
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead() {
//        Instead of deleting the object
        setToDestroy = true;
    }
}
