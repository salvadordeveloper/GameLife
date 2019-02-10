package com.salvador.gamelife.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.salvador.gamelife.GUI.Cell;
import com.salvador.gamelife.GUI.CellInterface;
import com.salvador.gamelife.GUI.UI.MenuListener;
import com.salvador.gamelife.Main;

import java.util.ArrayList;
import java.util.Random;

import static com.salvador.gamelife.Utils.Constants.CELL_HEIGHT;
import static com.salvador.gamelife.Utils.Constants.CELL_WIDTH;
import static com.salvador.gamelife.Utils.Constants.DEAD;
import static com.salvador.gamelife.Utils.Constants.LIVE;
import static com.salvador.gamelife.Utils.Constants.SCREEN_HEIGHT;
import static com.salvador.gamelife.Utils.Constants.SCREEN_WIDTH;

public class World implements CellInterface,MenuListener {

    private Main main;

    private int WORLD_WIDTH = 200;
    private int WORLD_HEIGHT = 100;

    private float MAX_ZOOM = 4f;
    private float MIN_ZOOM = 0.25f;

    private int RENDER_H = ((SCREEN_WIDTH * (int)MAX_ZOOM) / CELL_WIDTH)/2;
    private int RENDER_V =  ((SCREEN_HEIGHT * (int)MAX_ZOOM) / CELL_HEIGHT)/2;

    private float val_x = ((SCREEN_WIDTH * MAX_ZOOM) / 2);
    private float val_y = ((SCREEN_HEIGHT * MAX_ZOOM ) / 2);

    private int CENTER_H = WORLD_WIDTH/2;
    private int CENTER_V = WORLD_HEIGHT/2;

    int[][] map = new int[WORLD_WIDTH][WORLD_HEIGHT];

    private Stage world;
    private OrthographicCamera camera;

    private ArrayList<Cell> cells;

    private float timeCounter = 0;
    private float speed = 1;

    private Random randomNum = new Random();
    private float currentZoom;

    private boolean PLAY_STATE = false;

    public World(Main main){
        this.main = main;

        world = new Stage();
        camera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
        world.getViewport().setCamera(camera);
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        camera.position.x = CENTER_H*CELL_WIDTH;
        camera.position.y = CENTER_V*CELL_HEIGHT;

        camera.update();

        currentZoom = camera.zoom;

        cells = new ArrayList<Cell>();


        loadMap();
    }

    public void loadWorld(){

    }

    public void loadMap(){
        for(int i = CENTER_H-RENDER_H; i < CENTER_H+RENDER_H; i++){
            for(int j = CENTER_V-RENDER_V; j < CENTER_V+RENDER_V;j++){
                map[i][j] = DEAD;
                Cell cell = new Cell(main,this,i,j);
                cells.add(cell);
                world.addActor(cell);
            }
        }
    }

    public void draw(float delta){

        if(PLAY_STATE) {
            timeCounter += delta;
            if (timeCounter > speed) {
                algorithmLife();
                timeCounter = 0f;
            }
        }

        world.act();
        world.draw();

    }

    public void algorithmLife(){

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

                }
                if(liveNeighbours == 3){
                    temp[i][j] = LIVE;
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
        if(zoom < MAX_ZOOM && zoom > MIN_ZOOM){

            float cameraW = ((SCREEN_WIDTH*zoom)/2);
            float cameraH = ((SCREEN_HEIGHT*zoom)/2);

            camera.zoom = zoom;

            if(camera.position.x - cameraW < marginL){
                camera.position.x = marginL + cameraW;
            }
            if(camera.position.x + cameraW  > marginR){
                camera.position.x = marginR - cameraW;
            }
            if(camera.position.y - cameraH < marginDown){
                camera.position.y = marginDown + cameraH;
            }
            if(camera.position.y + cameraH > marginUp){
                camera.position.y = marginUp - cameraH;
            }

            camera.update();
        }
    }

    @Override
    public void playGame() {

    }

    @Override
    public void random() {
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

    @Override
    public void erase() {
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

    @Override
    public void pause() {
        PLAY_STATE = false;

    }

    @Override
    public void play() {
        PLAY_STATE = true;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
        System.out.println(speed);
    }

    public void changeCellState(float x,float y){
        Vector3 ss = new Vector3(x,y,0);
        camera.unproject(ss);
        int cx = (int)ss.x / CELL_WIDTH;
        int cy =(int)ss.y / CELL_HEIGHT;
        findCell(cx,cy).changeState();
    }

    public void scrollZoom(float zoom){
        zoom = camera.zoom + (zoom * 0.2f);
        setZoom(zoom);
    }


    float marginL = ((CENTER_H*CELL_WIDTH)-val_x);
    float marginR = ((CENTER_H*CELL_WIDTH)+val_x);

    float marginUp = ((CENTER_V*CELL_WIDTH)+val_y);
    float marginDown = ((CENTER_V*CELL_WIDTH)-val_y);

    public void moveCam(float x, float y){

        float cameraW = ((SCREEN_WIDTH*camera.zoom)/2);
        float cameraH = ((SCREEN_HEIGHT*camera.zoom)/2);

        float dx = x * (getZoom()) * .05f;
        float dy = y * (getZoom()) * .05f;

        if( camera.position.x - cameraW + dx >= marginL && camera.position.x + cameraW + dx <= marginR) {
            camera.position.x += dx;
        }
        if(camera.position.y - cameraH + dy >= marginDown && camera.position.y + cameraH + dy <= marginUp ){
            camera.position.y += dy;
        }

        camera.update();
    }

    public float getZoom(){
        return currentZoom;
    }

    public void setCZoom() {
        currentZoom = camera.zoom;

    }

}
