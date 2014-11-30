package com.raycaster.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Character extends Entity {
	
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Texture bulletTexture;
	public RaycasterGdxGame game;
	
	public Character(RaycasterGdxGame game,float x,float y,Texture characterTexture, int Height, int Width,float Speed,Texture bulletTexture){
		super(game,x,y,characterTexture,Height,Width,Speed);
		this.bulletTexture = bulletTexture;
		this.game = game;
	}
	
	@Override
	public void update(float delta){
		
		dx = 0;
		dy =0;
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			dx = -speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			dx = speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			dy = speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			dy = -speed * delta;
		}
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
			Bullet newBullet = new Bullet(game,x+game.thePlayer.Width/2, y+game.thePlayer.Height/2,bulletTexture, 5, 5, 10);//speed is the number of nodes to skip. Greater = faster
			bullets.add(newBullet);
				
		}
	
	}
}
