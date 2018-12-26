package com.salvador.gamelife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public  abstract  class GameLife implements Screen {
	SpriteBatch batch;
	Texture img;


	public Main game;

	public GameLife(Main game){
		this.game = game;
	}
}
