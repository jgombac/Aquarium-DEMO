package com.gombi.aqua;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import states.GameStateManager;
import states.TankState;

public class AquaGame extends ApplicationAdapter {

	public static int WIDTH = 480;
	public static int HEIGHT = 800;
	private SpriteBatch sb;
	private GameStateManager gsm;

	
	@Override
	public void create () {
		sb = new SpriteBatch();
		gsm = new GameStateManager();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new TankState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}

	@Override
	public void dispose(){
		gsm.dispose();
	}
}
