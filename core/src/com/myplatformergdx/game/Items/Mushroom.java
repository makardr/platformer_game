package com.myplatformergdx.game.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Screens.PlayScreen;
import com.myplatformergdx.game.Sprites.Protagonist;

public class Mushroom extends Item {
    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
//        Bind texture to the item
        setRegion(screen.getAtlas().findRegion("mushroom"), 0, 0, 16, 16);
        velocity = new Vector2(0.7f, 0);
    }

    @Override
    public void defineItem() {
//        Create body at x and y
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
//        Dynamic can move and affected by gravity
        bdef.type = BodyDef.BodyType.DynamicBody;
//        Add to the world
        body = world.createBody(bdef);

//        Define fixture in the shape of the circle
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
//        Set radius to six
        shape.setRadius(6 / MyPlatformerGame.PPM);

//        Set fixture
        fdef.filter.categoryBits = MyPlatformerGame.ITEM_BIT;
//        What can this fixture collide with
        fdef.filter.maskBits = MyPlatformerGame.MARIO_BIT |
                MyPlatformerGame.OBJECT_BIT |
                MyPlatformerGame.GROUND_BIT |
                MyPlatformerGame.COIN_BIT |
                MyPlatformerGame.BRICK_BIT;

//        Attached fixture to the body
        fdef.shape = shape;
//        Set user data so WorldContactListener could determine what is interacting with what
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void use(Protagonist mario) {
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
//        Set position of the texture region (sprite) to the center of box2d body
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setLinearVelocity(velocity);

        //        Push item in x
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }
}
