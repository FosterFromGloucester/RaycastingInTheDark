package com.raycaster.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class GridModel implements Grid{

	public int GridHeight = 500;
	public int GridWidth = 500;
	public GridBlock[][] grid;
	
	public Texture blockTexture;
	public Texture obsticalTexture;
	public Texture lightTexture;
	
	public int blockHeight = 1;
	public int blockWidth = 1;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	RaycasterGdxGame view;
	
	
	public GridModel(RaycasterGdxGame view){
		this.view = view;
		initializeBlocks();
		setUpGrid();
	}
	
	@Override
	public void  setUpGrid() {
		grid = new GridBlock[GridWidth][GridHeight];
		for(int i=0;i<GridHeight;i++){
			for(int j =0;j<GridWidth;j++){
				grid[i][j] = new GridBlock(blockTexture,obsticalTexture,lightTexture,blockHeight,blockWidth,i,j);
			}
		}
	}

	@Override
	public void initializeBlocks() {
		blockTexture = new Texture("grid.png");
		obsticalTexture=  new Texture("obstical.png");
		lightTexture = new Texture("light.png");
		blockHeight = blockTexture.getHeight();
		blockWidth = blockTexture.getWidth();
	}

	@Override
	public GridBlock[][] getGrid() {
		return grid;
	}
	
	@Override
	public void addToEntities(Entity entity){
		entities.add(entity);
	}
	
	public Entity getEntity(int position){
		return entities.get(position);
	}
	
	public boolean blockCollision(Entity e, float newX, float newY) {
		  boolean collision = false;

		  // determine affected tiles
		  int x1 = (int) Math.floor((Math.min(e.x, newX))/ blockWidth);
		  int y1 = (int) Math.floor((Math.min(e.y, newY))/ blockHeight);
		  int x2 = (int) Math.floor((Math.max(e.x, newX) + e.Width - 0.1f) / blockWidth);
		  int y2 = (int) Math.floor((Math.max(e.y, newY) + e.Height - 0.1f) / blockHeight);
		  
		  
		  if(y2>=GridHeight || x2>=GridWidth || newX<0 || newY <0){
			  return true;
		  }

		  for(int x = x1; x <= x2; x++) {
			  for(int y = y1; y <= y2; y++) {
				  //System.out.println("x "+x);
				  //System.out.println("y "+y);
				  if(grid[x][y].getObsticalStatus() == true){
					  collision = true;
				  }
			  }
		  }
		  
		  
		  return collision;
	  }
	
	public void resetGrid(){
		for(int i=0;i<GridHeight;i++){
			for(int j =0;j<GridWidth;j++){
				grid[i][j].resetToGrid();
			}
		}
	}
	
	public void printModel(){
		String line = "";
		int counter = 0;
		for(int i=0;i<GridHeight;i++){
			for(int j =0;j<GridWidth;j++){
				if(grid[i][j].getObsticalStatus() == true){
					counter++;
					line = line+"1";
				}
				else{
				}
			}
			System.out.println(counter);
		}
	}
	
}
