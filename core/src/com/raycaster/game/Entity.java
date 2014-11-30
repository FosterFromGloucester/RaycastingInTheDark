package com.raycaster.game;

import com.badlogic.gdx.graphics.Texture;

public class Entity {

	public Texture entityTexture;
	public int Height;
	public int Width;
	public float speed;
	public float x;
	public float y;
	public float dx;
	public float dy;
	public RaycasterGdxGame game;
	
	public Entity(RaycasterGdxGame game,float x,float y,Texture texture,int Height,int Width,float speed){
		this.entityTexture = texture;
		this.Height = Height;
		this.Width = Width;
		this.speed = speed;
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public void update(float delta){
		
	}
	
	public void move(float NewX,float NewY){
		x = NewX;
		y = NewY;
	}
}
