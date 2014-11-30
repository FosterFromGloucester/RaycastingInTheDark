package com.raycaster.game;

import com.badlogic.gdx.graphics.Texture;

public class GridBlock implements Block, Comparable<Object>  {

	public Texture gridTexture;
	public Texture obsticalTexture;
	public Texture lightTexture;
	public Texture currentTexture;
	
	public int Height;
	public int Width;
	public boolean obstical;
	public boolean obsticalLit;
	
	public int GridPositionX;
	public int GridPositionY;
	
	public float blockCost;
	public float h;
	public int g;
	public GridBlock parent;
	
	public GridBlock(Texture gridTexture,Texture obsticalTexture,Texture light,int Height,int Width,int GridPositionX,int GridPositionY){
		this.Height = Height;
		this.Width  = Width;
		this.gridTexture = gridTexture;
		this.GridPositionX = GridPositionX;
		this.GridPositionY = GridPositionY;
		this.lightTexture = light;
		this.obsticalTexture  = obsticalTexture;
		currentTexture = gridTexture;
	}
	

	@Override
	public int getHeight() {
		return Height;
	}

	@Override
	public int getWidth() {
		return Width;
	}
	
	public void setAsObstical(){
		obstical = true;
	}
	
	public void showObstical(){
		currentTexture = obsticalTexture;
		obsticalLit = true;
	}
	
	public boolean getObsticalStatus(){
		return obstical;
	}
	
	public boolean getObsticalLitStatus(){
		return obsticalLit;
	}
	
	public void setToLight(){
		currentTexture = lightTexture;
		obsticalLit = true;
	}
	
	public void resetToGrid(){
		obsticalLit  = false;
		currentTexture = gridTexture;
	}
	
	public boolean isLightOrObstical(){
		if(currentTexture.equals(lightTexture) || currentTexture.equals(obsticalTexture)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int setTileParent(GridBlock tileParent){
		g = tileParent.g+1;
		this.parent = tileParent;
		return g;
	}
	
	@Override
	public int compareTo(Object theBlock) {
		
		GridBlock toComp = (GridBlock) theBlock;
		float Compf = toComp.h+toComp.blockCost;
		float f = this.h+this.blockCost;
		
		if(f>Compf){
			return 1;
		}
		else if(f<Compf){
			return -1;
		}
		else{
			return 0;
				
		}
	}
	
}
