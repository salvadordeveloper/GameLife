package com.salvador.gamelife.GUI.UI.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.salvador.gamelife.Main;

public class Button extends Actor {

    public int buttonId;
    private Texture texture;

    private Texture textureNormal;
    private Texture textureTouch;

    OnClickListenner onClickListenner;

    public Button(Main main){
        addListener(clickListener);
    }

    public Button(Texture txt, int x, int y, int width, int heigth, int id){
        this.textureNormal = txt;
        this.texture = txt;
        this.buttonId = id;
        setPosition(x,y);
        setSize(width,heigth);
        setBounds(x,y,width,heigth);

        addListener(clickListener);
    }

    public void setTouchTexture(Texture texture){
        textureTouch = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }

    public void touchDown(){
        if(textureTouch != null){
            texture = textureTouch;
        }
    }

    public void touchUp(){
        if(onClickListenner != null){
            onClickListenner.onClick(buttonId);
        }
        texture = textureNormal;
    }

    public void setOnClickListenner(OnClickListenner onClickListenner) {
        this.onClickListenner = onClickListenner;
    }

    public ClickListener clickListener = new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Button.this.touchDown();
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            Button.this.touchUp();
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            super.touchDragged(event, x, y, pointer);
        }
    };
}
