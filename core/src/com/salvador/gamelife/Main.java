package com.salvador.gamelife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class Main extends Game {

    public AssetManager manager;

    @Override
    public void create() {
        manager = new AssetManager();
        setScreen(new World(this));
        System.out.println("Hola");
    }
}
