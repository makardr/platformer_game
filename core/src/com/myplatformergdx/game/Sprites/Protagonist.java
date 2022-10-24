package com.myplatformergdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
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
    public enum State {FALLING, JUMPING, STANDING, RUNNING}

    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Protagonist(World world, PlayScreen screen) {
        //        find texture region for getTexture in later use
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        com.badlogic.gdx.utils.Array<TextureRegion> frames = new com.badlogic.gdx.utils.Array<TextureRegion>();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));

        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        marioJump = new Animation<TextureRegion>(0.1f, frames);


        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);

        defineProtagonist();

//        Texture render size
        setBounds(0, 0, 16 / MyPlatformerGame.PPM, 16 / MyPlatformerGame.PPM);
//        Texture region associated with the sprite
        setRegion(marioStand);


    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
//                stateTimer decides which frame is pulled from animation
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
//        is flipX returns true if the texture is flipped
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
//        Does current state equals previous state, if it does state timer + deltatime, that resets the timer on animation
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
//        based on b2body
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
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
