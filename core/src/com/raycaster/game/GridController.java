package com.raycaster.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GridController implements Controller{

	public GridModel theModel; 
	public Vector2 VectorCenter;
	public RaycasterGdxGame view;
	
	public GridController(GridModel theModel,RaycasterGdxGame view){
		this.theModel = theModel;
		this.view = view;
	}
	
	@Override
	public void addObsticals(int x, int y) {
		for(int i = 200;i<250;i++){
			for(int j = 200;j<250;j++){
				theModel.grid[i][j].setAsObstical();
			}
		}
	}
	
	public void calculateShadowsFlashLight(){
		
		int PlayerX = (int) (theModel.view.thePlayer.x/theModel.blockWidth)+theModel.view.thePlayer.Width/2;
		int PlayerY = (int) (theModel.view.thePlayer.y/theModel.blockHeight)+theModel.view.thePlayer.Height/2;
		
		int TempMiddleX = PlayerX - Gdx.input.getX();
		int TempMiddleY = PlayerY - (theModel.view.screenHeight-Gdx.input.getY());
		
		Vector2 VectorMiddle = new Vector2(TempMiddleX,TempMiddleY);
		VectorMiddle.clamp(200, 200);
		VectorCenter = new Vector2(VectorMiddle);
		
		VectorMiddle.rotate(30);
		
		int corner1X = PlayerX - (int)VectorMiddle.x;
		int corner1Y = PlayerY - (int)VectorMiddle.y;
		
		VectorMiddle.rotate(-60);
		
		int corner2X =  PlayerX - (int)VectorMiddle.x;
		int corner2Y = PlayerY - (int)VectorMiddle.y;
		
		if(corner1X<=0)
			corner1X =0;
		if(corner2X >= theModel.view.screenWidth)
			corner2X = theModel.view.screenWidth-1;
		if(corner2X<=0)
			corner2X = 0;
		if(corner1X >= theModel.view.screenWidth)
			corner1X = theModel.view.screenWidth-1;
		if(corner1Y<=0)
			corner1Y = 0;
		if(corner1Y>=theModel.view.screenHeight)
			corner1Y = theModel.view.screenHeight -1 ;
		if(corner2Y>=theModel.view.screenHeight)
			corner2Y = theModel.view.screenHeight - 1;
		if(corner2Y<=0)
			corner2Y = 0;

		ArrayList<GridBlock> coneBase = castRay2(corner1X,corner1Y,corner2X,corner2Y);
		for(int i =0;i<coneBase.size();i++){
			castRay2(coneBase.get(i).GridPositionX,coneBase.get(i).GridPositionY,PlayerX,PlayerY);
		}
	}
	
	public void calculateShadowsCircle(){
		int PlayerX = (int) (theModel.view.thePlayer.x/theModel.blockWidth)+theModel.view.thePlayer.Width/2;
		int PlayerY = (int) (theModel.view.thePlayer.y/theModel.blockHeight)+theModel.view.thePlayer.Height/2;
		for(int i = 0; i<theModel.GridHeight;i++){
			for(int j = 0; j<theModel.GridWidth;j++){
				if((Math.pow(i-PlayerX, 2)+Math.pow(j-PlayerY, 2)) == 10000){
					castRay2(i,j,PlayerX,PlayerY);
				}
			}
		}
	}
	
	public void castRay(int x, int y){//x2 y2
		x= 49;
		y = 49;

		ArrayList<GridBlock> ray = new ArrayList<GridBlock>();
		
		int playerPositionX = (int) theModel.view.thePlayer.x/theModel.blockWidth;//x1
		int playerPositionY = (int) theModel.view.thePlayer.y/theModel.blockHeight;//y1
		
		int dx = playerPositionX - x;
		int dy = playerPositionY - y;
		
		int m = 0;
		
		try{
			m = dy/dx;
		}
		catch(ArithmeticException e){
			m = 0;
		}
		float error = 0;
		float Threshold = 0.5f;
		System.out.println("Check");
		int adjust = 1;
		
		if(m<0){
			adjust = -1;
		}
		
		if(m<=1 && m>=-1){
			float slope = Math.abs(m);
			int tempY = playerPositionY;
			if(x<playerPositionX){
				playerPositionX= x;
				tempY = y;
			}
			for(int i = playerPositionX; i<x+1;i++){
				ray.add(theModel.grid[i][tempY]);
				theModel.grid[i][tempY].setToLight();
				error = error + slope;
				if(error >=Threshold){
					tempY = tempY+adjust;
					Threshold = Threshold + 1.0f;
				}
			}
		}
		else{
			float slope = Math.abs(dx/dy);
			int TempX = playerPositionX;
			if(y<playerPositionY){
				int Holder = playerPositionY;
				playerPositionY = y;
				y= Holder;
				TempX = x;
			}
			for(int i = playerPositionY; i<y+1;i++){
				ray.add(theModel.grid[TempX][i]);
				theModel.grid[TempX][i].setToLight();
				error = error + slope;
				if(error >=Threshold){
					TempX = TempX+adjust;
					Threshold = Threshold + 1.0f;
				}
			}
		}
//		rayCollection[RayCounter] = ray;
//		RayCounter++;
	}
	
	public ArrayList<GridBlock> castRay2(int x,int y,int x1,int y1){//x0 y0
		
		ArrayList<GridBlock> ray = new ArrayList<GridBlock>();
		
		int playerPositionX = x1;//x1
		int playerPositionY = y1;//y1
		
		int dx = Math.abs(playerPositionX - x);
		int dy = Math.abs(playerPositionY - y);
		
		int sx;
		int sy;
		
		if (playerPositionX < x){
			sx = 1;
		}
		else{
			sx = -1;
		}
		
		if (playerPositionY < y){
			sy = 1;
		}
		else{
			sy = -1;
		}
		
		int error = dx-dy;
		int error2;
		
		while(true){
			ray.add(theModel.grid[playerPositionX][playerPositionY]);
			theModel.grid[playerPositionX][playerPositionY].setToLight();
			
			if(x == playerPositionX && y == playerPositionY){
				break;
			}
			
			error2 = 2*error;
			
			if(error2>-dy){
				error = error-dy;
				playerPositionX = playerPositionX+sx;
			}
			
			if(error2<dx){
				error = error +dx;
				playerPositionY = playerPositionY+sy;
			}
		}
		setShadow(ray);
		return ray;
	}
	
	
	public void setShadow(ArrayList<GridBlock> ray){
		boolean obstical=  false;
		for(int i = 0;i<ray.size();i++){
			GridBlock tempBlock = ray.get(i);
			if(theModel.grid[tempBlock.GridPositionX][tempBlock.GridPositionY].getObsticalStatus() == true){
				obstical = true;
				theModel.grid[tempBlock.GridPositionX][tempBlock.GridPositionY].showObstical();
			}
			if(obstical == true && theModel.grid[tempBlock.GridPositionX][tempBlock.GridPositionY].getObsticalStatus()==false ){
				theModel.grid[tempBlock.GridPositionX][tempBlock.GridPositionY].resetToGrid();
			}
		}
	}
	
	public boolean checkEnemyLit(Enemy e){
		if(theModel.grid[(int)e.x][(int)e.y].isLightOrObstical()){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean checkCollsion(Entity e,float futureX, float futureY) {
//		if(e instanceof Bullet){
//			if(futureX < view.thePlayer.x + view.thePlayer.Width && view.thePlayer.x < futureX + e.Width && futureY < view.thePlayer.y + view.thePlayer.Height && view.thePlayer.y < futureY + e.Height){	
//				System.out.println("Hit");
//				return true;
//			}
//			else{
//				return false;
//			}
//		}
		if(theModel.blockCollision(e, futureX, futureY)){
			System.out.println("Collisionaaa");
			return true;
		}
		else{
			return false;
		}
	}

}
