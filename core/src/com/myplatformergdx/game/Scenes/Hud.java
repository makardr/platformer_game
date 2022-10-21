package com.myplatformergdx.game.Scenes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myplatformergdx.game.MyPlatformerGame;


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label characterLabel;

    public Hud(SpriteBatch sb) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(MyPlatformerGame.V_WIDTH, MyPlatformerGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countdownLabel = new Label(String.format("%03d", worldTimer), new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));
        characterLabel = new Label("Character", new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));

//        Expand label to the top of the screen, multiple expandX share space
        table.add(characterLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
//        Everything below table.row() is another row, like enter
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

//        add table to the stage
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
