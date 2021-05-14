
import java.lang.Math;

public class MazeGenerator {

    public void run(int n) {

	// creates all cells
	Cell[][] mazeMap = new Cell[n][n];
	initializeCells(mazeMap);

	// create a list of all internal walls, and links the cells and walls
	Wall[] walls = getWalls(mazeMap);

	createMaze(walls, mazeMap);

	printMaze(mazeMap);

    }

    public void createMaze(Wall[] walls, Cell[][] mazeMap) {

		//open the entrance and exit to the maze
		mazeMap[0][0].left.visible = false;
		mazeMap[mazeMap.length-1][mazeMap[0].length-1].right.visible = false;
		
		//initialize a boolean array that stores true if a wall has been checked to be removed and is null if that wall hasn't been checked
		boolean[] checked = new boolean[walls.length];
		//initialize an int that stores how many different walls have been checked
		int check = 0;

		//while not all walls have been checked, keep trying to remove walls
		while(check != walls.length){

			//get a random number within the range of walls indexes and access that wall
			int wallNum = (int)(Math.random()*walls.length);
			Wall wall = walls[wallNum];

			//if the wall has not already been checked, check it
			if(checked[wallNum] != true){

				//mark that wall as check and increment the check counter
				checked[wallNum] = true;
				check++;

				//if the wall is not an exterior wall (has a cell on either side), check if it can be removed
				if(wall.first != null && wall.second != null){

					//if the cells on either side of the wall are not already part of the same group, make wall not visible and union cells
					if(!(UnionFind.find(wall.first).equals(UnionFind.find(wall.second)))){

						wall.visible = false;
						UnionFind.union(wall.first, wall.second);

					} // if there isn't a path between walls already

				} //if wall isn't exterior

			} //if checked

		} //while

    } //createMaze()


    // print out the maze in a specific format
    public void printMaze(Cell[][] maze) {
	for(int i = 0; i < maze.length; i++) {
	    // print the up walls for row i
	    for(int j = 0; j < maze.length; j++) {
		Wall up = maze[i][j].up;
		if(up != null && up.visible) System.out.print("+--");
		else System.out.print("+  ");
	    }
	    System.out.println("+");

	    // print the left walls and the cells in row i
	    for(int j = 0; j < maze.length; j++) {
		Wall left = maze[i][j].left;
		if(left != null && left.visible) System.out.print("|  ");
		else System.out.print("   ");
	    }

	    //print the last wall on the far right of row i
	    Wall lastRight = maze[i][maze.length-1].right;
	    if(lastRight != null && lastRight.visible) System.out.println("|");
	    else System.out.println(" ");
	}

	// print the last row's down walls
	for(int i = 0; i < maze.length; i++) {
	    Wall down = maze[maze.length-1][i].down;
	    if(down != null && down.visible) System.out.print("+--");
	    else System.out.print("+  ");
	}
	System.out.println("+");


    }

    // create a new Cell for each position of the maze
    public void initializeCells(Cell[][] maze) {
	for(int i = 0; i < maze.length; i++) {
	    for(int j = 0; j < maze[0].length; j++) {
		maze[i][j] = new Cell();
	    }
	}
    }

    // create all walls and link walls and cells
    public Wall[] getWalls(Cell[][] mazeMap) {

	int n = mazeMap.length;

	Wall[] walls = new Wall[2*n*(n+1)];
	int wallCtr = 0;

	// each "inner" cell adds its right and down walls
	for(int i = 0; i < n; i++) {
	    for(int j = 0; j < n; j++) {
		// add down wall
		if(i < n-1) {
		    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i+1][j]);
		    mazeMap[i][j].down = walls[wallCtr];
		    mazeMap[i+1][j].up = walls[wallCtr];
		    wallCtr++;
		}
		
		// add right wall
		if(j < n-1) {
		    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i][j+1]);
		    mazeMap[i][j].right = walls[wallCtr];
		    mazeMap[i][j+1].left = walls[wallCtr];
		    wallCtr++;
		}
	    }
	}

	// "outer" cells add their outer walls
	for(int i = 0; i < n; i++) {
	    // add left walls for the first column
	    walls[wallCtr] = new Wall(null, mazeMap[i][0]);
	    mazeMap[i][0].left = walls[wallCtr];
	    wallCtr++;

	    // add up walls for the top row
	    walls[wallCtr] = new Wall(null, mazeMap[0][i]);
	    mazeMap[0][i].up = walls[wallCtr];
	    wallCtr++;

	    // add down walls for the bottom row
	    walls[wallCtr] = new Wall(null, mazeMap[n-1][i]);
	    mazeMap[n-1][i].down = walls[wallCtr];
	    wallCtr++;

	    // add right walls for the last column
	    walls[wallCtr] = new Wall(null, mazeMap[i][n-1]);
	    mazeMap[i][n-1].right = walls[wallCtr];
	    wallCtr++;
	}

	
	return walls;
	}

    public static void main(String [] args) {
	
	if(args.length > 0) {
		int n = Integer.parseInt(args[0]);
		//print n  and then nxn maze
		System.out.println(n);
	    new MazeGenerator().run(n);
	} else {
		//print n and then nxn maze
		System.out.println(5);
		new MazeGenerator().run(5);
	}
    }

}
