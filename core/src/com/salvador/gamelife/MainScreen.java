package com.salvador.gamelife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.salvador.gamelife.GUI.Menu;
import com.salvador.gamelife.GUI.MenuListener;

public class MainScreen extends GameLife implements GestureDetector.GestureListener, MenuListener {

    private mWorld mWorld;
    private Menu menu;

    public MainScreen(Main game){
        super(game);
    }

    @Override
    public void show() {
        mWorld = new mWorld(game);
        menu = new Menu(game);

        menu.setMenuListener(this);

        GestureDetector gd = new GestureDetector(this);
        InputMultiplexer inputMultiPlex = new InputMultiplexer();
        inputMultiPlex.addProcessor(gd);
        inputMultiPlex.addProcessor(menu.getStage());
        inputMultiPlex.addProcessor(mWorld.getStage());

        Gdx.input.setInputProcessor(inputMultiPlex);

        mWorld.randomMap();
        mWorld.Play();

    }


    @Override
    public void render(float delta) {
        mWorld.draw(delta);
        menu.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void playGame() {

    }

    @Override
    public void random() {
        mWorld.randomMap();
    }

    @Override
    public void erase() {
        mWorld.clearMap();
    }

    @Override
    public void pause() {
        mWorld.Pause();
    }

    @Override
    public void play() {
        mWorld.Play();
    }

    @Override
    public void setSpeed(float speed) {
        mWorld.setSpeed(speed);
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
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        mWorld.setCZoom();
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float zoom = (initialDistance / distance) * mWorld.getZoom();
        mWorld.setZoom(zoom);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public Vector3 getRealTouch(float x, float y){
        Vector3 touch = new Vector3(x,y,0);
        menu.getCameraStage().unproject(touch);
        return touch;
    }
}
