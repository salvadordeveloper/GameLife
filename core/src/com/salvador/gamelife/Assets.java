package com.salvador.gamelife;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    private AssetManager manager;

    public Assets(){
        manager = new AssetManager();
    }

    public void loadTexture(String texture){
        manager.load(texture,Texture.class);
    }

    public Texture getTexture(String texture){
        return manager.get(texture);
    }

    public void loadGame(){
        loadTexture("cell_live.png");
        loadTexture("cell_dead.png");

        loadTexture("play.png");
        loadTexture("pause.png");
        loadTexture("delete.png");
        loadTexture("btn_start.png");
        loadTexture("random.png");
        loadTexture("speed_bar.png");
        loadTexture("bar_point.png");
        loadTexture("filter.png");
        loadTexture("title.png");

        loadTexture("btn.png");
        loadTexture("btn_touch.png");
        loadTexture("bar.png");
        manager.finishLoading();
    }

    public AssetManager getManager() {
        return manager;
    }
}
