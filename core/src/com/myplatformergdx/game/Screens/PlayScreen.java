package com.myplatformergdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myplatformergdx.game.Enemies.Enemy;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Scenes.Hud;
import com.myplatformergdx.game.Sprites.Protagonist;
import com.myplatformergdx.game.Tools.B2WorldCreator;
import com.myplatformergdx.game.Tools.WorldContactListener;

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

    //    Player object
    private Protagonist player;


    //    Textures
//    Alternative AssetManager to load more textures and to optimize load
    private TextureAtlas atlas;

    private Music music;

    private B2WorldCreator creator;

    public PlayScreen(MyPlatformerGame game) {

        this.game = game;

//        Textures
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
//        Create camera
        gameCam = new OrthographicCamera();
//        Camera type
        gamePort = new FitViewport(MyPlatformerGame.V_WIDTH / MyPlatformerGame.PPM, MyPlatformerGame.V_HEIGHT / MyPlatformerGame.PPM, gameCam);

//        create hud
        hud = new Hud(game.batch);

//      load map and setup map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyPlatformerGame.PPM);
//      initially set gamecam to be centered at the start
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);


//        box2d
//        (gravity, put objects to rest)
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

//      Initialize player class object
        player = new Protagonist(this);

//        Custom world creator
        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());

//        Music
//        music = MyPlatformerGame.manager.get("audio/music/mario_music.ogg", Music.class);
//        music.setLooping(true);
//        music.play();

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }


    public void handleInput(float deltatime) {
//        To move a subject physics way can be used either force or impulse
//        Force - gradual increase in speed
//        Impulse - immediate change
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
//            (Vector2(speed in x, speed in y),where in the body apply force, "wake up" body)
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    //  global world update
    public void update(float deltatime) {
//        handle user input
        handleInput(deltatime);
//      takes timestamp of velocity and position iterations - step(60 times second,velocity, position)
//        Calls WorldContactListener, which implements ContactListener
//        Cant delete bodies from world.step because simulation would not be finished
        world.step(1 / 60f, 6, 2);

//        update custom classes
        hud.update(deltatime);
        player.update(deltatime);

        for (Enemy enemy : creator.getGoombas()) {
            enemy.update(deltatime);
//            Wake up body after getting close to it
            if (enemy.getX() < player.getX() + 224 / MyPlatformerGame.PPM) {
                enemy.b2body.setActive(true);
            }
        }
//      track camera with a gamecam
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
//        update gamecam with changed coordinates
        gameCam.update();
//        set renderer to draw only what is currently in the camera
        renderer.setView(gameCam);

    }


    //    delta is called constantly
    @Override
    public void render(float delta) {
//        Update world
        update(delta);

//        clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        render world
        renderer.render();

//        render Box2dDebugLines
        b2dr.render(world, gameCam.combined);


//        Render textures
//      Set only what is visible inside the main cam
        game.batch.setProjectionMatrix(gameCam.combined);

//        Draw entities
//        Open the textures batch
        game.batch.begin();
//      Give game the player sprite
        player.draw(game.batch);
        for (Enemy enemy : creator.getGoombas())
            enemy.draw(game.batch);
//        Close the textures batch
        game.batch.end();

//        Render what is shown in the camera, draw hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }


    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
