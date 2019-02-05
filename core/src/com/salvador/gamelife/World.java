package com.salvador.gamelife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.salvador.gamelife.GUI.Menu;

import java.util.ArrayList;

import static com.salvador.gamelife.Constants.DEAD;
import static com.salvador.gamelife.Constants.LIVE;

public class World extends GameLife implements CellInterface,GestureDetector.GestureListener {

    private Stage world;
    private OrthographicCamera Camera,camerab;

    private ArrayList<Cell> cells;

    float timeToEat = 0f;

    int WORLD_WIDTH = 200;
    int WORLD_HEIGHT = 100;

    int RENDER_H =WORLD_WIDTH/4;
    int RENDER_V =WORLD_HEIGHT/4;

    int CENTER_H = WORLD_WIDTH/2;
    int CENTER_V = WORLD_HEIGHT/2;

    int[][] map = new int[WORLD_WIDTH][WORLD_HEIGHT];

    private Menu menu;

    public World(Main game){
        super(game);
    }

    @Override
    public void show() {
        world = new Stage();
        Camera = new OrthographicCamera(800,450);
        world.getViewport().setCamera(Camera);
        Camera.setToOrtho(false, 800, 450);


        menu = new Menu(game);

        Camera.position.x = CENTER_H*50;
        Camera.position.y = CENTER_V*50;
        Camera.update();

        GestureDetector gd = new GestureDetector(this);
        InputMultiplexer inputMultiPlex = new InputMultiplexer();
        inputMultiPlex.addProcessor(gd);
        inputMultiPlex.addProcessor(world);

        Gdx.input.setInputProcessor(inputMultiPlex);
        currentZoom = Camera.zoom;

        cells = new ArrayList<Cell>();

        for(int i = CENTER_H-RENDER_H; i < CENTER_H+RENDER_H; i++){
            for(int j = CENTER_V-RENDER_V; j < CENTER_V+RENDER_V;j++){
                map[i][j] = 0;
                Cell cell = new Cell(game,this,i,j);
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

        if(timeToEat > 1f){
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

    public int getWidth(){
        return WORLD_WIDTH;
    }

    public int getHeight(){
        return WORLD_HEIGHT;
    }
    @Override
    public void cellState(int state, int x, int y) {
        map[x][y] = state;
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


       // Camera.translate(-deltaX * currentZoom,deltaY * currentZoom);
       // Camera.update();
        return false;
    }
    @Override
    public boolean zoom(float initialDistance, float distance) {

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


    public void clearSD(){
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

    }
}
