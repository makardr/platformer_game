package com.myplatformergdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.myplatformergdx.game.Sprites.InteractiveTileObject;


//Gets called when two fixtures in Box2d collide
public class WorldContactListener implements ContactListener {

    // Gets called when two fixtures begin to collide
    @Override
    public void beginContact(Contact contact) {
//        Contact has two fixtures, this code must identify which one is which
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

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
