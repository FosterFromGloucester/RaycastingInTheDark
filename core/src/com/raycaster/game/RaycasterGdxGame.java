package com.raycaster.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RaycasterGdxGame extends ApplicationAdapter {
	
	public SpriteBatch batch;
	public GridModel gridModel;
	public int screenWidth = 500;
	public int screenHeight = 500;
	public GridController controller;
	public Character thePlayer;
	
	@Override
	public void create() {
		
		batch = new SpriteBatch();
		gridModel = new GridModel(this);
		controller = new GridController(gridModel,this);
		controller.addObsticals(5, 10);
		thePlayer = new Character(this,50,50,new Texture("player.png"),10,10,100.0f,new Texture("bullet.png"));
		gridModel.addToEntities(thePlayer);
		AddEnemy();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float delta = Gdx.graphics.getDeltaTime();
		 
		//update the entities position
		for(int i = 0; i < gridModel.entities.size(); i++) {
			  Entity e = gridModel.getEntity(i);
			  e.update(delta);
			  if(controller.checkCollsion(e,e.x + e.dx, e.y + e.dy) != true){
				  e.move(e.x + e.dx, e.y + e.dy); 
		
			  }
		}
		
		gridModel.resetGrid();
		controller.calculateShadowsFlashLight();
		
		batch.begin();
		
		//draw the grid
		for(int i = 0; i<gridModel.GridHeight;i++){
			for(int j = 0; j<gridModel.GridWidth;j++){
				if(gridModel.grid[i][j].isLightOrObstical() == true){
					batch.draw(gridModel.grid[i][j].currentTexture, i*gridModel.blockWidth, j*gridModel.blockHeight);
				}
			}
		}
		
		//draw the entities
		for(int i = 0; i < gridModel.entities.size(); i++) {
			Entity e = gridModel.getEntity(i);
			if(e instanceof Enemy){
				if(controller.checkEnemyLit(((Enemy)e)) == true){
					batch.draw(e.entityTexture, e.x, e.y); 
				}
			}
			else{
				batch.draw(e.entityTexture, e.x, e.y);
			}
		}
		
		//draw the bullets
		for(int i=0;i<thePlayer.bullets.size();i++){
			Bullet b = thePlayer.bullets.get(i);
			b.update(delta);
			b.move(b.x + b.dx, b.y + b.dy);
		}
		
		for(int i=0;i<thePlayer.bullets.size();i++){
			Bullet b = thePlayer.bullets.get(i);
			batch.draw(thePlayer.bulletTexture, b.x, b.y);
		}
		
		
		
		batch.end();
	}
	
	public void AddEnemy(){
		gridModel.addToEntities(new Enemy(this, 400, 50, new Texture("Enemy.png"), 10, 10, 20, gridModel.grid));
		System.out.println(gridModel.grid[400][50]);
	}
}
