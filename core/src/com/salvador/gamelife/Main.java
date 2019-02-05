package com.salvador.gamelife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class Main extends Game {

    public Assets assets;
    public MainScreen screen;

    @Override
    public void create() {
        assets = new Assets();
        assets.loadGame();
        screen = new MainScreen(this);
        setScreen(screen);
    }

}
