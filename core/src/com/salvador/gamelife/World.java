package com.salvador.gamelife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import static com.salvador.gamelife.Constants.DEAD;
import static com.salvador.gamelife.Constants.LIVE;

public class World extends GameLife {

    private Stage world;
    private Stage menu;
    private OrthographicCamera Camera,camerab;

    private ArrayList<Cell> cells;

    float timeToEat = 0f;

    int WORLD_WIDTH = 10;
    int WORLD_HEIGHT = 10;

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
        Gdx.input.setInputProcessor(world);

        cells = new ArrayList<Cell>();

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length;j++){
                map[i][j] = 1;
                Cell cell = new Cell(i*50,j*50);
                cells.add(cell);
                world.addActor(cell);
            }
        }
    }

    @Override
    public void render(float delta) {

        timeToEat += delta;

        if(timeToEat > 3f){
            algorithLife();
            timeToEat = 0f;
        }
        world.act();
        world.draw();
        menu.draw();


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

                if(map[i][j] == 1){
                    if(liveNeighbours < 2 || liveNeighbours > 3){
                        temp[i][j] = DEAD;
                    }
                    else if(liveNeighbours == 2|| liveNeighbours == 3){
                        temp[i][j] = LIVE;
                    }
                }
                else{
                    if(liveNeighbours == 3){
                        temp[i][j] = LIVE;
                    }
                }
            }
        }

        for (int x=0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                map[y][x] = temp[y][x];
            }
        }

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
            if (cells.get(i).x / 50 == x ) {
                if (cells.get(i).y / 50 == y ) {
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

        System.out.println(dead);
        return dead;
    }
}
