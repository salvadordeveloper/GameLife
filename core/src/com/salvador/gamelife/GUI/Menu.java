package com.salvador.gamelife.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.salvador.gamelife.GUI.Buttons.Button;
import com.salvador.gamelife.GUI.Buttons.OnClickListenner;
import com.salvador.gamelife.GUI.VelocityBar.VelocityBar;
import com.salvador.gamelife.GUI.VelocityBar.VelocityInteface;
import com.salvador.gamelife.Main;

public class Menu implements OnClickListenner {

    private static final int BUTTON_PLAY = 1;
    private static final int BUTTON_SETTINGS = 2;
    private static final int BUTTON_ERASE = 3;
    private static final int BUTTON_RANDOM = 4;

    private Main main;
    private MenuListener menuListener;

    private Filter filter;

    private Title title;

    private Button btnPlay;
    private Button btnSettings;
    private Button btnErase;
    private Button btnRandom;

    private VelocityBar velocityBar;
    private Group menuButtons;

    private Stage menu;
    private OrthographicCamera cameraStage;

    public Menu(Main main){
        this.main = main;
        setupStage();


        filter = new Filter(main,0,0,800,450);
        title = new Title(main,150,250,500, 130);
        menu.addActor(filter);
        menu.addActor(title);

        setupButtons();


    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public void setupButtons(){
        btnPlay = new Button(main.assets.getTexture("button_start.png"),250,80,325,100,BUTTON_PLAY);
        btnSettings = new Button(main.assets.getTexture("play.png"),-1000,-1000,70,70,BUTTON_SETTINGS);
        btnErase = new Button(main.assets.getTexture("delete.png"),100,360,70,70,BUTTON_ERASE);
        btnRandom = new Button(main.assets.getTexture("random.png"),200,360,70,70,BUTTON_RANDOM);

        velocityBar = new VelocityBar(main,400,390);
        velocityBar.setVelocityInteface(new VelocityInteface() {
            @Override
            public void setVelocity(float velocity) {
                menuListener.setSpeed(velocity);
            }
        });

        btnPlay.setOnClickListenner(this);
        btnSettings.setOnClickListenner(this);
        btnErase.setOnClickListenner(this);
        btnRandom.setOnClickListenner(this);

        menuButtons = new Group();
        menuButtons.addActor(btnErase);
        menuButtons.addActor(btnRandom);
        menuButtons.addActor(velocityBar.getBar());

        menu.addActor(btnPlay);
        menu.addActor(btnSettings);
        menu.addActor(menuButtons);

        hideButtons();
    }

    public void setupStage(){
        menu = new Stage();
        cameraStage = new OrthographicCamera(800,450);
        menu.getViewport().setCamera(cameraStage);
        cameraStage.setToOrtho(false,800,450);
    }

    public void draw(){
        menu.draw();
    }

    public Stage getStage(){
        return menu;
    }

    @Override
    public void onClick() {

    }

    public void startGame(){
        menuListener.pause();
        menuListener.erase();
        title.setVisible(false);
        showButtons();
        btnSettings.setPosition(10,360);
        btnPlay.setPosition(-1000,-1000);
        btnPlay.setVisible(false);
    }

    public void hideButtons(){
        menuButtons.setVisible(false);
        menuButtons.setPosition(-1000,-1000);
    }

    public void showButtons(){
        menuButtons.setVisible(true);
        menuButtons.setPosition(0,0);
    }

    public int state = 0;


    public void buttonS(){
        if(state == 0){
            state = 1;
            showButtons();
            menuListener.pause();
            filter.setVisible(true);
        }else{
            state = 0;
            hideButtons();
            menuListener.play();
            filter.setVisible(false);
        }
    }

    @Override
    public void onClick(int button) {
        switch (button){
            case BUTTON_PLAY:
                startGame();
                break;
            case BUTTON_SETTINGS:
                buttonS();
                break;
            case BUTTON_RANDOM:
                menuListener.random();
                break;
            case BUTTON_ERASE:
                menuListener.erase();
                break;
        }
    }

    public Vector3 getRealTouch(int x,int y){
        Vector3 touch = new Vector3(x,y,0);
        cameraStage.unproject(touch);
        return touch;
    }

    public OrthographicCamera getCameraStage() {
        return cameraStage;
    }
}
