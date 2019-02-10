package com.salvador.gamelife.GUI.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.salvador.gamelife.Main;

public class Title extends Actor {

    private Texture texture;

    public Title(Main main, int x, int y,int w,int h){
        setPosition(x,y);
        setSize(w,h);
        texture = main.assets.getTexture("title.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }
}
