package com.salvador.gamelife.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.salvador.gamelife.GUI.UI.Menu;
import com.salvador.gamelife.Main;

public class GameLife extends BaseScreen {

    private World World;
    private Menu menu;
    private InputManager inputManager;
    private GestureDetector gestureDetector;

    public GameLife(Main game){
        super(game);
    }

    @Override
    public void show() {
        World = new World(game);
        menu = new Menu(game);

        menu.setMenuListener(World);

        inputManager = new InputManager(World);
        gestureDetector = new GestureDetector(inputManager);

        InputMultiplexer inputMultiPlex = new InputMultiplexer();
        inputMultiPlex.addProcessor(gestureDetector);
        inputMultiPlex.addProcessor(menu.getStage());
        inputMultiPlex.addProcessor(World.getStage());
        inputMultiPlex.addProcessor(inputManager);

        Gdx.input.setInputProcessor(inputMultiPlex);

        World.random();
        World.playGame();
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.4f, .4f, .4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        World.draw(delta);
        menu.draw();
    }

    @Override
    public void resize(int width, int height) {

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
