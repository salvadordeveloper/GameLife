package com.salvador.gamelife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.salvador.gamelife.Constants.DEAD;
import static com.salvador.gamelife.Constants.LIVE;

public class Cell extends Actor {
    private int state;
    private Texture textureLive, textureDead;
    private Texture texture;

    public int x,y;

    public Cell(int x,int y){
        this.x = x;
        this.y = y;
        setPosition(x,y);
        setBounds(x,y,50,50);
        state = DEAD;

        textureLive = new Texture(Gdx.files.internal("cell_live.png"));
        textureDead = new Texture(Gdx.files.internal("cell_dead.png"));

        texture = textureDead;
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                changeState();
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,x,y,50,50);
    }


    public void changeState(){
        if(state == LIVE){
            state = DEAD;
            texture = textureDead;
        }else{
            state = LIVE;
            texture = textureLive;
        }
    }

    public int getState(){
        return state;
    }

    public void setState(int state){
        this.state = state;
        if(state == LIVE){
            texture = textureLive;
        }else{
            texture = textureDead;
        }
    }

}
