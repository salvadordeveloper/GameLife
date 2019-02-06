package com.salvador.gamelife.GUI.VelocityBar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.salvador.gamelife.Main;

public class TouchPoint extends Actor {

    private VelocityInteface velocityInteface;
    private Texture texture;
    private int x;
    private int y;

    private int minX;
    private int maxX;

    public TouchPoint(Texture texture, int x, int y, final int minX, final int maxX){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.minX = minX;
        this.maxX = maxX;
        setPosition(x,y);
        setSize(50,50);

        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return super.touchDown(event, x, y, pointer, button);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                setPosition(TouchPoint.this.x,getY());

            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 position = localToStageCoordinates(new Vector2(x,y));
                movePoint( position.x-25);
                super.touchDragged(event, x, y, pointer);
            }
        });
    }

    public void movePoint(float x){
        if(x >= minX && x <= maxX){
            this.x = (int)x;
            velocityInteface.setVelocity(1 - (x - minX) / (maxX - minX));

        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,x,y,getWidth(),getHeight());
    }

    public void setVelocityInteface(VelocityInteface velocityInteface) {
        this.velocityInteface = velocityInteface;

    }
}
