package com.raycaster.game;

public interface Grid {

	public void setUpGrid();
	public void initializeBlocks();
	public GridBlock[][] getGrid();
	public void addToEntities(Entity entity);
	public Entity getEntity(int position);
		
}
