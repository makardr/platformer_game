package com.myplatformergdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Sprites.Brick;
import com.myplatformergdx.game.Sprites.Coin;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {
        //        Create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

//        world
//              Consists of bodies
//                    Bodies are containers for:
//                          mass
//                          velocity
//                          location
//                          angle
//                          fixtures - physical attributes of a body, have:
//                                  shape
//                                  density
//                                  friction
//                                  restitution - bounciness


//        Create ground bodies/fixtures, layer 2
//       get index in Tiled starts with 0 from the bottom
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

//            Setup body
//            Body types: DynamicBody - like player, can move, StaticBody - does not move, KinematicBody - arent affected by forces, but can be affected by velocity
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyPlatformerGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyPlatformerGame.PPM);

//            Add body to box2d
            body = world.createBody(bdef);
//            Define polygon shape
//            Divide by 2 because it starts in the center
            shape.setAsBox((rect.getWidth() / 2) / MyPlatformerGame.PPM, (rect.getHeight() / 2) / MyPlatformerGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //        Create pipe bodies/fixtures, layer 3
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyPlatformerGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyPlatformerGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MyPlatformerGame.PPM, (rect.getHeight() / 2) / MyPlatformerGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //        Create brick bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(world, map, rect);
        }
        //        Create coin bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(world, map, rect);
        }
    }
}
