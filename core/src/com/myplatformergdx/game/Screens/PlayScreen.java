package com.myplatformergdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myplatformergdx.game.MyPlatformerGame;
import com.myplatformergdx.game.Scenes.Hud;

public class PlayScreen implements Screen {
    private MyPlatformerGame game;
    private Hud hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    public PlayScreen(MyPlatformerGame game) {
        this.game = game;


//        Create camera
        gameCam = new OrthographicCamera();
//        Camera type
        gamePort = new FitViewport(MyPlatformerGame.V_WIDTH, MyPlatformerGame.V_HEIGHT, gameCam);
        hud = new Hud(game.batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
