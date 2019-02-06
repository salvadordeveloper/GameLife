package com.salvador.gamelife.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.salvador.gamelife.Main;

public class Filter extends Actor {

    private Texture texture;

    public Filter(Main main,int x,int y,int w,int h){
        setPosition(x,y);
        setSize(w,h);

        texture = main.assets.getTexture("filter.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }
}
