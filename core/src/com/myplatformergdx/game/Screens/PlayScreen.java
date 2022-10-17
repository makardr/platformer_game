package com.myplatformergdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Scenes.Hud;

public class PlayScreen implements Screen {
    private MyPlatformerGame game;
    private Hud hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    //    Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //    Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(MyPlatformerGame game) {
        this.game = game;


//        Create camera
        gameCam = new OrthographicCamera();
//        Camera type
        gamePort = new FitViewport(MyPlatformerGame.V_WIDTH, MyPlatformerGame.V_HEIGHT, gameCam);

//        create hud
        hud = new Hud(game.batch);

//      load map and setup map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
//      initially set gamecam to be centered at the start
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

//        box2d
//        (gravity, put objects to rest)
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

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
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

//            Add body to box2d
            body = world.createBody(bdef);
//            Define polygon shape
//            Divide by 2 because it starts in the center
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //        Create pipe bodies/fixtures, layer 3
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //        Create brick bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //        Create coin bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void show() {

    }


    public void handleInput(float deltatime) {
        if (Gdx.input.isTouched())
            gameCam.position.x += 100 * deltatime;
    }

    //  global world update
    public void update(float deltatime) {
        handleInput(deltatime);
        gameCam.update();
        renderer.setView(gameCam);
    }


    //    delta is called constantly
    @Override
    public void render(float delta) {
        update(delta);

//        clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        render world
        renderer.render();

//        render Box2dDebugLines
        b2dr.render(world, gameCam.combined);

//        Render what is shown in camera, draw hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
