package com.raycaster.game;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class EnemyBrain {

	public ArrayList<GridBlock> closedList = new ArrayList<GridBlock>();
	public PriorityQueue<GridBlock> openList = new PriorityQueue<GridBlock>();//open list maintains nodes to be checked
	public ArrayList<GridBlock> path = new ArrayList<GridBlock>();//the path that will be followed
	public GridBlock[][] theMap;
	public int mapWidth;
	public int mapHeight;
	public GridBlock[][] theBlockMap;//map of nodes based on the underlying grid
	public String heurstic;//the type of heuristic used
	
	public EnemyBrain(GridBlock[][] theMap,int mapHeight,int mapWidth){
		this.theMap = theMap;
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		theBlockMap  = theMap;
	}
	
	
	public ArrayList<GridBlock> findThePath(int StartX,int StartY,int EndX,int EndY){//this function preforms A star pathfinding to find the shortest path
		
		
		//setUpNodeMap();//make sure the map is set up each time
		path.clear();
		
		GridBlock currentNode;
		//reset lists
		closedList.clear();
		openList.clear();
		theBlockMap[StartX][StartY].blockCost= 0;//first tile
		theBlockMap[StartX][StartY].g = 0;//base level
		
		theBlockMap[StartX][StartY].parent = null;
		openList.add(theBlockMap[StartX][StartY]);//add starting node to the open list
		
		while(openList.size() !=0){//while there are still elements in the open list
			
			currentNode = openList.peek();//get the current node from the queue
			
			if(currentNode == theBlockMap[EndX][EndY]){//if the current node is the target node then end
				break;
			}
			
			openList.remove(currentNode);
			closedList.add(currentNode);//place in the closed list
			
			for(int x = -1;x<2;x++){//check neighbouring nodes to the current node
				for(int y = -1;y<2;y++){
					
					if(x == 0 && y == 0){//don't check the node itself
						continue;
					}
					
					int NeighBourX = currentNode.GridPositionX+x;
					int NeighBourY = currentNode.GridPositionY+y;
					
					if(checkOutOfBounds(NeighBourX,NeighBourY) && currentNode.getObsticalStatus() != true){//check that the neighbour is not a collidable block
						
						float nextNodeCost = currentNode.blockCost+1;//get the new cost
						
						GridBlock theNeighbourNode  = theBlockMap[NeighBourX][NeighBourY];//get the neighbour
					
						if(nextNodeCost< theNeighbourNode.blockCost){
							
							if(openList.contains(NeighBourX)){
								openList.remove();
								
							}
							if(closedList.contains(theNeighbourNode)){
								closedList.remove(theNeighbourNode);
							}
							
						}
					
						if(!openList.contains(theNeighbourNode) && !closedList.contains(theNeighbourNode)){// if the neighbour is not in the open or closed add it to the open list
							theNeighbourNode.blockCost = nextNodeCost;//record its cost
							theNeighbourNode.h = getheuristicCost(theMap,NeighBourX,NeighBourY,EndX,EndY);//check the heuristic costs
							theNeighbourNode.setTileParent(currentNode);//make sure a path can be tracked through parent nodes
							openList.add(theNeighbourNode);//add node to open list
						
						}
					}
					
				}
			}
		}
		
		if(theBlockMap[EndX][EndY].parent == null){//if the targets parent node is null then there is no path
			return null;
		}
		
		GridBlock theTarget = theBlockMap[EndX][EndY];
		GridBlock theStart = theBlockMap[StartX][StartY];
		
		while(theTarget!=theStart){//create the path by going back through the parents
			path.add(theTarget);
			theTarget = theTarget.parent;
		}
		
		return path;
		
	}
	
	public float getheuristicCost(GridBlock[][] theMap,int x,int y, int futureX,int futureY){//calculates the heurstic cost based on the manhatten heuristic
		float dx = Math.abs(futureX-x);
		float dy = Math.abs(futureY-y);
		return  dx+dy;
	}
	
	public boolean checkOutOfBounds(int FutureX,int FutureY){//checks the node doesnt fall outside the map
		if(FutureX>=mapWidth || FutureY>= mapHeight || FutureX<0 || FutureY<0){
			return false;
		}
		else{
			return true;
		}
	}
	

}

