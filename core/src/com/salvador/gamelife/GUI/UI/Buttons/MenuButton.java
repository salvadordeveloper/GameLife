package com.salvador.gamelife.GUI.UI.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.salvador.gamelife.Main;

import sun.rmi.runtime.Log;

public class MenuButton extends Button {

    private Texture texture;
    private Texture icon;

    private Texture textureNormal;
    private Texture textureTouch;

    private int iconMargin = 15;

    public MenuButton(Main main, Texture icon){
        super(main);
        this.textureNormal = main.assets.getTexture("btn.png");
        this.textureTouch = main.assets.getTexture("btn_touch.png");

        this.texture = textureNormal;
        this.icon = icon;
    }

    public MenuButton(Main main, Texture icon, int x, int y, int w, int h,int id){
        super(main);
        this.icon = icon;
        this.buttonId = id;
        this.textureNormal = main.assets.getTexture("btn.png");
        this.textureTouch = main.assets.getTexture("btn_touch.png");
        this.texture = textureNormal;

        setPosition(x,y);
        setSize(w,h);
    }


    @Override
    public void touchDown(){
        texture = textureTouch;
    }

    @Override
    public void touchUp(){
        texture = textureNormal;
        if(onClickListenner != null){
            onClickListenner.onClick(buttonId);
        }
    }

    public void setIcon(Texture texture){
        this.icon = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
        batch.draw(icon,getX()+iconMargin,getY()+iconMargin,getWidth()-(iconMargin*2),getHeight()-(iconMargin*2));
    }
}
