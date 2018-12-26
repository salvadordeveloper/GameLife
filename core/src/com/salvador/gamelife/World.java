package com.salvador.gamelife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import static com.salvador.gamelife.Constants.DEAD;
import static com.salvador.gamelife.Constants.LIVE;

public class World extends GameLife implements CellInterface,GestureDetector.GestureListener {

    private Stage world;
    private Stage menu;
    private OrthographicCamera Camera,camerab;

    private ArrayList<Cell> cells;

    float timeToEat = 0f;

    int WORLD_WIDTH = 20;
    int WORLD_HEIGHT = 20;

    int[][] map = new int[WORLD_WIDTH][WORLD_HEIGHT];

    public World(Main game){
        super(game);
    }

    @Override
    public void show() {
        world = new Stage();
        Camera = new OrthographicCamera(800,450);
        world.getViewport().setCamera(Camera);
        Camera.setToOrtho(false, 800, 450);
        world.setDebugAll(true);

        menu = new Stage();
        menu.setDebugAll(false);
        camerab= new OrthographicCamera(800,450);
        menu.getViewport().setCamera(camerab);
        camerab.setToOrtho(false,800,450);

        GestureDetector gd = new GestureDetector(this);
        InputMultiplexer inputMultiPlex = new InputMultiplexer();
        inputMultiPlex.addProcessor(gd);
        inputMultiPlex.addProcessor(world);

        Gdx.input.setInputProcessor(inputMultiPlex);
        currentZoom = Camera.zoom;

        cells = new ArrayList<Cell>();

        for(int i = 0; i < WORLD_WIDTH; i++){
            for(int j = 0; j < WORLD_HEIGHT;j++){
                map[j][i] = 0;
                Cell cell = new Cell(this,i,j);
                cells.add(cell);
                world.addActor(cell);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timeToEat += delta;

        if(timeToEat > 3f){
            algorithLife();
            timeToEat = 0f;
        }
        world.act();
        world.draw();
        menu.draw();
        Camera.update();

    }

    @Override
    public void resize(int width, int height) {
    }

    public void algorithLife(){

        int[][] temp = new int[WORLD_WIDTH][WORLD_HEIGHT];

        //Check if cell is live and count neighbours
        for(int i = 0; i < WORLD_WIDTH; i++){
            for(int j = 0; j < WORLD_HEIGHT;j++){
                int liveNeighbours = countNeighboors(i,j);

                    if(map[j][i] == LIVE){
                        if(liveNeighbours < 2 || liveNeighbours > 3){
                            temp[j][i] = DEAD;
                        }
                        else if(liveNeighbours == 2|| liveNeighbours == 3){
                            temp[j][i] = map[j][i];
                        }
                    }else{
                        if(liveNeighbours == 3){
                            temp[j][i] = LIVE;
                        }
                    }




            }
        }

        map = temp;



        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {

                Cell mCell = findCell(x,y);
                if(map[y][x] == LIVE){
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    public int countNeighboors(int x, int y){
        int alive = 0;
        int dead = 0;
        if (x + 1 < WORLD_WIDTH) { // right
            if (map[y][x + 1] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }

        if (x + 1 < WORLD_WIDTH && y - 1 > 0) { // bottom
            // right
            // corner
            if (map[y - 1][x + 1] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }

        if (x + 1 < WORLD_WIDTH && y + 1 < WORLD_HEIGHT) { // top
            // right
            // corner
            if (map[y + 1][x + 1] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }

        if (y + 1 < WORLD_HEIGHT) {// top middle
            if (map[y+1][x] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }

        if (y +1 < WORLD_HEIGHT && x - 1 > 0) {// top
            // left
            // corner
            if (map[y + 1][x - 1] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }


        if (x -1 > 0) {// left
            if (map[y][x - 1] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }

        if (x -1 > 0 && y -1 > 0) {// bottom
            // left
            // corner
            if (map[y - 1][x - 1] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }

        // x++
        if (y -1 > 0) {// bottom middle
            if (map[y - 1][x] == LIVE) {
                dead++;
            } else {
                alive++;
            }
        }
        if(dead > 0){
            System.out.println(dead+ " : " + x + "," + y);

        }

        return dead;
    }

    @Override
    public void cellState(int state, int x, int y) {
        map[y][x] = state;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {


        Camera.translate(-deltaX * currentZoom,deltaY * currentZoom);
        Camera.update();
        return false;
    }
    @Override
    public boolean zoom(float initialDistance, float distance) {


        Camera.zoom = (initialDistance / distance) * currentZoom;
        Camera.update();

        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("INFO", "panStop");
        currentZoom = Camera.zoom;
        return false;
    }

    public float currentZoom = 0;
}
