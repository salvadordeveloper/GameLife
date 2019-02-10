package com.salvador.gamelife.GUI.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.salvador.gamelife.GUI.UI.Buttons.Button;
import com.salvador.gamelife.GUI.UI.Buttons.MenuButton;
import com.salvador.gamelife.GUI.UI.Buttons.OnClickListenner;
import com.salvador.gamelife.GUI.UI.VelocityBar.VelocityBar;
import com.salvador.gamelife.GUI.UI.VelocityBar.VelocityInteface;
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
    private MenuButton btnSettings;
    private MenuButton btnErase;
    private MenuButton btnRandom;

    private VelocityBar velocityBar;
    private Group menuButtons;

    private Stage menu;
    private OrthographicCamera cameraStage;

    public Menu(Main main){
        this.main = main;
        setupStage();


        filter = new Filter(main,0,0,800,450);
        title = new Title(main,100,280,600, 60);
        menu.addActor(filter);
        menu.addActor(title);

        setupButtons();


    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public void setupButtons(){
        btnPlay = new Button(main.assets.getTexture("btn_start.png"),250,80,325,100,BUTTON_PLAY);

        btnPlay.setTouchTexture(main.assets.getTexture("btn_touch.png"));
        btnSettings = new  MenuButton(main,main.assets.getTexture("play.png"),-1000,-1000,70,70,BUTTON_SETTINGS);
        btnErase = new MenuButton(main,main.assets.getTexture("delete.png"),100,360,70,70,BUTTON_ERASE);
        btnRandom = new  MenuButton(main,main.assets.getTexture("random.png"),200,360,70,70,BUTTON_RANDOM);

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
        filter.setVisible(false);
    }

    public void hideButtons(){
        menuButtons.setVisible(false);
        menuButtons.setPosition(-1000,-1000);
    }

    public void showButtons(){
        menuButtons.setVisible(true);
        menuButtons.setPosition(0,0);
    }

    public int state = 1;


    public void buttonS(){
        if(state == 0){
            state = 1;
            showButtons();
            menuListener.pause();
            btnSettings.setIcon(main.assets.getTexture("play.png"));
        }else{
            state = 0;
            hideButtons();
            menuListener.play();
            filter.setVisible(false);
            btnSettings.setIcon(main.assets.getTexture("pause.png"));
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

}
