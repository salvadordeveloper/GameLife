package com.salvador.gamelife.GUI.VelocityBar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.salvador.gamelife.GUI.Buttons.Button;
import com.salvador.gamelife.Main;

import java.lang.reflect.AccessibleObject;

public class VelocityBar {

    private TouchPoint touchPoint;

    private Group bar;
    private Button line;

    public VelocityBar(Main main, int x, int y){
        bar = new Group();

        line = new Button(main.assets.getTexture("speed_bar.png"),x,y-10,350,20,1);
        touchPoint = new TouchPoint(main.assets.getTexture("bar_point.png"),x,y-25,400,700);
        bar.addActor(line);
        bar.addActor(touchPoint);

    }

    public void setVelocityInteface(VelocityInteface velocityInteface) {
        touchPoint.setVelocityInteface(velocityInteface);

    }

    public Group getBar(){
        return bar;
    }
}
