package com.salvador.gamelife.GUI.UI.VelocityBar;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.salvador.gamelife.GUI.UI.Buttons.Button;
import com.salvador.gamelife.Main;

public class VelocityBar {

    private TouchPoint touchPoint;

    private Group bar;
    private Button line;

    public VelocityBar(Main main, int x, int y){
        bar = new Group();
        line = new Button(main.assets.getTexture("bar.png"),x,y-10,350,20,1);
        touchPoint = new TouchPoint(main.assets.getTexture("btn.png"),x,y-25,400,700);
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
