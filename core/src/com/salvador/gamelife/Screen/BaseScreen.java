package com.salvador.gamelife.Screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.salvador.gamelife.Main;

public  abstract  class BaseScreen implements Screen {

	public Main game;

	public BaseScreen(Main game){
		this.game = game;
	}
}
