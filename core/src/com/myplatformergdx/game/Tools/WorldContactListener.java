package com.myplatformergdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Sprites.Enemy;
import com.myplatformergdx.game.Sprites.InteractiveTileObject;

//Gets called when two fixtures in Box2d collide
public class WorldContactListener implements ContactListener {

    // Gets called when two fixtures begin to collide
    @Override
    public void beginContact(Contact contact) {
//        Contact has two fixtures, this code must identify which one is which
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

//      Collision definition
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

//        Test if object has the user data of head
        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
//            if fixA is head assign fixA to head, otherwise assign fixB to head
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
//            if fixA = head then fixB is the object, otherwise (: is otherwise) fixA is the object
            Fixture object = head == fixA ? fixB : fixA;
//             test if object is an extension of InteractiveTileObject
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
//        Collision entities
        switch (cDef) {
//            Collision for Mario and enemy head
            case MyPlatformerGame.ENEMY_HEAD_BIT | MyPlatformerGame.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MyPlatformerGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
//                Collision for enemy with object
            case MyPlatformerGame.ENEMY_BIT | MyPlatformerGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MyPlatformerGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MyPlatformerGame.MARIO_BIT | MyPlatformerGame.ENEMY_BIT:
                Gdx.app.log("MARIO", "DIED");
                break;
            case MyPlatformerGame.ENEMY_BIT | MyPlatformerGame.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
        }
    }

    // Gets called when two fixtures disconnect from each other
    @Override
    public void endContact(Contact contact) {

    }

    //Can change characteristics of collision when collision happen
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    //Gives results of the collision
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
