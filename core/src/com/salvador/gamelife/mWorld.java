package com.salvador.gamelife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

import static com.salvador.gamelife.Constants.DEAD;
import static com.salvador.gamelife.Constants.LIVE;
import static com.salvador.gamelife.Constants.START_X;

public class mWorld implements CellInterface{

    private Main main;

    private int WORLD_WIDTH = 200;
    private int WORLD_HEIGHT = 100;

    private int RENDER_H = ((850 * 5) /50)/2;
    private int RENDER_V =  ((400 * 5) /50)/2;

    private int CENTER_H = WORLD_WIDTH/2;
    private int CENTER_V = WORLD_HEIGHT/2;

    int[][] map = new int[WORLD_WIDTH][WORLD_HEIGHT];

    private Stage world;
    private OrthographicCamera camera;

    private ArrayList<Cell> cells;

    private float timeCounter = 0;
    private float speed = 1;

    Random randomNum = new Random();
    private float currentZoom;

    private boolean PLAY_STATE = false;

    public mWorld(Main main){
        this.main = main;
        world = new Stage();
        camera = new OrthographicCamera(800,450);
        world.getViewport().setCamera(camera);
        camera.setToOrtho(false, 800, 450);

        camera.position.x = CENTER_H*50;
        camera.position.y = CENTER_V*50;

        camera.update();

        currentZoom = camera.zoom;
        cells = new ArrayList<Cell>();

        for(int i = CENTER_H-RENDER_H; i < CENTER_H+RENDER_H; i++){
            for(int j = CENTER_V-RENDER_V; j < CENTER_V+RENDER_V;j++){
                map[i][j] = 0;
                Cell cell = new Cell(main,this,i,j);
                cells.add(cell);
                world.addActor(cell);

            }
        }
        Cell cell = new Cell(main,this,0,0);
        cells.add(cell);
        world.addActor(cell);
    }

    public void draw(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(PLAY_STATE) {
            timeCounter += delta;
            if (timeCounter > speed) {
                algorithLife();
                timeCounter = 0f;
            }
        }

        world.act();
        world.draw();

    }

    public void algorithLife(){

        int[][] temp = new int[WORLD_WIDTH][WORLD_HEIGHT];

        for(int i = CENTER_H-RENDER_H; i < CENTER_H+RENDER_H; i++){
            for(int j = CENTER_V-RENDER_V; j < CENTER_V+RENDER_V;j++){
                int liveNeighbours = countNeighbors(i,j);

                if(map[i][j] == LIVE){
                    if(liveNeighbours < 2 || liveNeighbours > 3){
                        temp[i][j] = DEAD;
                    }
                    else if(liveNeighbours == 2|| liveNeighbours == 3){
                        temp[i][j] = map[i][j];
                    }
                }else{
                    if(liveNeighbours == 3){
                        temp[i][j] = LIVE;
                    }
                }
            }
        }

        map = temp;


        for(int i = CENTER_H-RENDER_H; i < CENTER_H+RENDER_H; i++){
            for(int j = CENTER_V-RENDER_V; j < CENTER_V+RENDER_V;j++){

                Cell mCell = findCell(i,j);
                if(map[i][j] == LIVE){
                    if(mCell.getState() == DEAD){
                        mCell.changeState();
                    }
                }

                else{
                    if(mCell.getState() == LIVE){
                        mCell.changeState();
                    }
                }
            }
        }
    }

    public Cell findCell(int x, int y){
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).x == x ) {
                if (cells.get(i).y == y ) {
                    return cells.get(i);

                }
            }
        }
        return null;
    }

    public int countNeighbors(int x,int y){
        int live = 0;
        live += neightbor(x-1,y+1); // UP LEFT
        live += neightbor(x,y+1);      // UP
        live += neightbor(x+1,y+1); // UP RIGHT
        live += neightbor(x-1,y);      // LEFT
        live += neightbor(x+1,y);      // RIGHT
        live += neightbor(x-1,y-1); // DOWN LEFT
        live += neightbor(x,y-1);      // DOWN
        live += neightbor(x+1,y-1); // DOWN RIGHT
        return live;
    }

    public int neightbor(int x,int y){
        if(x >= 0 && y >= 0 && x < WORLD_WIDTH && y < WORLD_HEIGHT){
            return map[x][y];
        }
        return 0;
    }


    public Stage getStage(){
        return world;
    }

    @Override
    public void cellState(int state, int x, int y) {
        map[x][y] = state;
    }


    public void setZoom(float zoom){
        if(zoom < 5 && zoom > 0.25){
            camera.zoom = zoom;
            camera.update();
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        System.out.println(speed);
    }

    public float getSpeed() {
        return speed;
    }

    public float getZoom(){
        return currentZoom;
    }

    public void setCZoom(){
        currentZoom = camera.zoom;
    }

    public void clearMap(){
        for(int i = CENTER_H-RENDER_H; i < CENTER_H+RENDER_H; i++){
            for(int j = CENTER_V-RENDER_V; j < CENTER_V+RENDER_V;j++){
                map[i][j] = DEAD;
                Cell mCell = findCell(i,j);
                if(mCell.getState() == LIVE) {
                    mCell.changeState();
                }
            }
        }
    }


    public void randomMap(){
        for(int i = CENTER_H-RENDER_H; i < CENTER_H+RENDER_H; i++){
            for(int j = CENTER_V-RENDER_V; j < CENTER_V+RENDER_V;j++){

                int state = randomNum.nextInt(2);
                map[i][j] = state;
                Cell mCell = findCell(i,j);
                if(mCell.getState() != state) {
                    mCell.changeState();
                }
            }
        }
    }

    public void Play(){
        PLAY_STATE = true;
    }

    public void Pause(){
        PLAY_STATE = false;
    }
}