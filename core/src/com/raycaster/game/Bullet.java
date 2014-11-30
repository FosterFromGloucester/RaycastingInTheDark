package com.raycaster.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class Bullet extends Entity {

	public ArrayList<GridBlock> path;
	public int counter;
	public float gradient;
	public float targetX;
	public float targetY;
	
	public Bullet(RaycasterGdxGame game,float x, float y, Texture texture, int Height, int Width,float speed) {
		super(game,x, y, texture, Height, Width, speed);
		setUpPath();
	}
	
	private void setUpPath() {
		this.targetX = x-game.controller.VectorCenter.x;
		this.targetY =y- game.controller.VectorCenter.y;
		path = game.controller.castRay2((int)x, (int)y,(int)targetX, (int)targetY);
		counter = path.size()-1;
	}

	@Override
	public void update(float delta){
		dx = 0;
		dy =0;
		
		if(counter>0){
			x = path.get(counter).GridPositionX;
			y = path.get(counter).GridPositionY;
			if(game.gridModel.grid[(int)x][(int)y].getObsticalLitStatus() != true){
				game.thePlayer.bullets.remove(this);
			}
			counter = counter - (int)speed;
		}
		else{
			game.thePlayer.bullets.remove(this);
		}
	}

}
