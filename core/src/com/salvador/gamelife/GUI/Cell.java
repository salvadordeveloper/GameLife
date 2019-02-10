package com.salvador.gamelife.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.salvador.gamelife.Main;

import static com.salvador.gamelife.Utils.Constants.CELL_HEIGHT;
import static com.salvador.gamelife.Utils.Constants.CELL_WIDTH;
import static com.salvador.gamelife.Utils.Constants.DEAD;
import static com.salvador.gamelife.Utils.Constants.LIVE;

public class Cell extends Actor {
    private int state;
    private Texture textureLive, textureDead;
    private Texture texture;

    public int x,y;

    public float margin = .15f;

    private CellInterface cellInterface;

    public Cell(Main main, CellInterface cellInterface, int x, int y){
        this.cellInterface = cellInterface;
        this.x = x;
        this.y = y;
        setPosition((x*CELL_WIDTH)+margin,(y*CELL_HEIGHT)+ margin);
        setSize(CELL_WIDTH-margin*2,CELL_HEIGHT-margin*2);
        state = DEAD;

        textureLive = main.assets.getTexture("cell_live.png");
        textureDead = main.assets.getTexture("cell_dead.png");

        texture = textureDead;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }

    public int getState(){
        return state;
    }

    public void changeState(){
        if(state == LIVE){
            state = DEAD;
            cellInterface.cellState(state,x,y);
            texture = textureDead;
        }else{
            state = LIVE;
            cellInterface.cellState(state,x,y);
            texture = textureLive;
        }
    }

}
