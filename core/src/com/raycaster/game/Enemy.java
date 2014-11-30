package com.raycaster.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Entity {

	public EnemyBrain theBrain;
	public Texture enemyTexture;
	public  ArrayList<GridBlock> thePath = new ArrayList<GridBlock>();
	public int counter = 0;
	public GridBlock[][] theMap;
	
	public Enemy(RaycasterGdxGame game, float x, float y, Texture texture,int Height, int Width, float speed,GridBlock[][] theMap) {
		super(game, x, y, texture, Height, Width, speed);
		this.theMap = theMap;
		theBrain = new EnemyBrain(theMap, game.screenWidth, game.screenHeight);
	}
	
	public void update(float delta){
		thePath = theBrain.findThePath((int)x, (int)y, (int)game.thePlayer.x, (int)game.thePlayer.y);
		chase();
	}
	
	public void chase(){
		if(thePath.size()!=0){
			GridBlock temp = thePath.get(thePath.size()-1);
			x = temp.GridPositionX;
			y = temp.GridPositionY;
			thePath.remove(temp);	
		}
	}
	
	

}
