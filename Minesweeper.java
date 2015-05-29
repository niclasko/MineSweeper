
/* Minesweeper implementation
 * by Niclas Kjall-Ohlsson copyright 2005
 */

public class Minesweeper {
	private int nRows = 10;		// Number of cells horizontally
	private int nCols = 10;		// Number of cells vertically

	public int ml_tab[][];		// Mine locations
	public boolean cc_tab[][];	// Cell visibility
	
	private int nUc = 0;		// Number of uncovered cells
	private int nUm = 0;		// Number of uncovered mines
	
	private int nNum_mines = 18;	// Number of mines
	
	public final int EAST = 1;
	public final int NORTHEAST = 2;
	public final int NORTH = 3;
	public final int NORTHWEST = 4;
	public final int WEST = 5;
	public final int SOUTHWEST = 6;
	public final int SOUTH = 7;
	public final int SOUTHEAST = 8;
        
	public int getRows() {
		return nRows;
	}
        
	public int getCols() {
		return nCols;
	}
        
	public int getNumMines() {
		return nNum_mines;
	}
        
	public int getNumUncoveredCells() {
		return nUc;
	}
        
	public int getNumUncoveredMines() {
		return nUm;
	}
        
	public void setNumMines(int n) {
		nNum_mines = n;
	}
	
	public boolean gameOver() {
		return (getNumUncoveredMines() == getNumMines());
	}
        
	private java.util.Vector getAdjacentCoveredCells(int x,int y) {
		java.util.Vector points = new java.util.Vector(8,1);
		if(coveredAdjacentValue(x,y,EAST)) { points.add(new java.awt.Point(x+1,y)); }
		if(coveredAdjacentValue(x,y,NORTHEAST)) { points.add(new java.awt.Point(x+1,y-1)); }
		if(coveredAdjacentValue(x,y,NORTH)) { points.add(new java.awt.Point(x,y-1)); }
		if(coveredAdjacentValue(x,y,NORTHWEST)) { points.add(new java.awt.Point(x-1,y-1)); }
		if(coveredAdjacentValue(x,y,WEST)) { points.add(new java.awt.Point(x-1,y)); }
		if(coveredAdjacentValue(x,y,SOUTHWEST)) { points.add(new java.awt.Point(x-1,y+1)); }
		if(coveredAdjacentValue(x,y,SOUTH)) { points.add(new java.awt.Point(x,y+1)); }
		if(coveredAdjacentValue(x,y,SOUTHEAST)) { points.add(new java.awt.Point(x+1,y+1)); }
		return points;
	}
        
	public void initialize() {
		int nRandX = 0, nRandY = 0;
		boolean bMine = true;
		java.util.Random r = new java.util.Random();
		
		ml_tab = new int[nRows+2][nCols+2];
		cc_tab = new boolean[nRows+2][nCols+2];
                
		nUc = 0;
		nUm = 0;               
                
		// Place mines at nNum_mines random locations
		for(int i=0; i<nNum_mines; i++) {
			do { // until empty cell is found
				nRandX = (int)(r.nextDouble()*(nRows-1)+1);
				nRandY = (int)(r.nextDouble()*(nCols-1)+1);
				if(ml_tab[nRandX][nRandY] == -1) { bMine = true; }
				else { ml_tab[nRandX][nRandY] = -1; bMine = false; }		// Placing mine here
			} while(bMine);
		}
		
		for(int i=0;i<=nRows+1; i++) {
			ml_tab[i][nCols+1] = -2;
			ml_tab[i][0] = -2;
			cc_tab[i][nCols+1] = false;
			cc_tab[i][0] = false;
		}
		
		for(int i=0;i<=nCols+1; i++) {
			ml_tab[0][i] = -2;
			ml_tab[nRows+1][i] = -2;
			cc_tab[0][i] = false;
			cc_tab[nRows+1][i] = false;
		}
		
		for(int i=1; i<nRows+1; i++) {
			for (int j=1; j<nCols+1; j++) {
				cc_tab[i][j] = true; // Set cell covering to true
				if(ml_tab[i][j] != -1) {
					ml_tab[i][j] = 0;
					if(adjacentValue(i,j,EAST) == -1) ml_tab[i][j]++;
					if(adjacentValue(i,j,NORTHEAST) == -1) ml_tab[i][j]++;
					if(adjacentValue(i,j,NORTH) == -1) ml_tab[i][j]++;
					if(adjacentValue(i,j,NORTHWEST) == -1) ml_tab[i][j]++;
					if(adjacentValue(i,j,WEST) == -1) ml_tab[i][j]++;
					if(adjacentValue(i,j,SOUTHWEST) == -1) ml_tab[i][j]++;
					if(adjacentValue(i,j,SOUTH) == -1) ml_tab[i][j]++;
					if(adjacentValue(i,j,SOUTHEAST) == -1) ml_tab[i][j]++;
				}
			}
		}
	}
	
	private int coveredAdjacentCells(int x, int y) {
		int coveredAdjacentCells = 0;
		if(coveredAdjacentValue(x,y,EAST)) { coveredAdjacentCells++; }
		if(coveredAdjacentValue(x,y,NORTHEAST)) { coveredAdjacentCells++; }
		if(coveredAdjacentValue(x,y,NORTH)) { coveredAdjacentCells++; }
		if(coveredAdjacentValue(x,y,NORTHWEST)) { coveredAdjacentCells++; }
		if(coveredAdjacentValue(x,y,WEST)) { coveredAdjacentCells++; }
		if(coveredAdjacentValue(x,y,SOUTHWEST)) { coveredAdjacentCells++; }
		if(coveredAdjacentValue(x,y,SOUTH)) { coveredAdjacentCells++; }
		if(coveredAdjacentValue(x,y,SOUTHEAST)) { coveredAdjacentCells++; }
		return coveredAdjacentCells;
	}
        
	private int uncoveredMines(int x, int y) {
		int uncoveredMines = 0;
		if(adjacentValue(x,y,EAST) == -1 && !coveredAdjacentValue(x,y,EAST)) { uncoveredMines++; }
		if(adjacentValue(x,y,NORTHEAST) == -1 && !coveredAdjacentValue(x,y,NORTHEAST)) { uncoveredMines++; }
		if(adjacentValue(x,y,NORTH) == -1 && !coveredAdjacentValue(x,y,NORTH)) { uncoveredMines++; }
		if(adjacentValue(x,y,NORTHWEST) == -1 && !coveredAdjacentValue(x,y,NORTHWEST)) { uncoveredMines++; }
		if(adjacentValue(x,y,WEST) == -1 && !coveredAdjacentValue(x,y,WEST)) { uncoveredMines++; }
		if(adjacentValue(x,y,SOUTHWEST) == -1 && !coveredAdjacentValue(x,y,SOUTHWEST)) { uncoveredMines++; }
		if(adjacentValue(x,y,SOUTH) == -1 && !coveredAdjacentValue(x,y,SOUTH)) { uncoveredMines++; }
		if(adjacentValue(x,y,SOUTHEAST) == -1 && !coveredAdjacentValue(x,y,SOUTHEAST)) { uncoveredMines++; }
		return uncoveredMines;
	}
                        
	public double probeCell(int x, int y) {
		if(gameOver()) return 0.0;
		if (x>nRows+1 || x < 1) { return 0.0; }
		if (y>nCols+1 || y < 1) { return 0.0; }
		if (!cc_tab[x][y]) { return 0.0; }
		nUc++;
		if(ml_tab[x][y] == -1) { nUm++; cc_tab[x][y] = false; return 1.0; }
		if(ml_tab[x][y] > 0) { cc_tab[x][y] = false; return -1.0; }
		if(ml_tab[x][y] == 0) { // check all adjacent cells recursively
			cc_tab[x][y] = false;   // Uncover cell	
    		if(coveredAdjacentValue(x,y,EAST)) probeCell(x+1,y);      // EAST
			if(coveredAdjacentValue(x,y,NORTHEAST)) probeCell(x+1,y-1);  // NORTHEAST
			if(coveredAdjacentValue(x,y,NORTH)) probeCell(x,y-1);      // NORTH
			if(coveredAdjacentValue(x,y,NORTHWEST)) probeCell(x-1,y-1);  // NORTHWEST
			if(coveredAdjacentValue(x,y,WEST)) probeCell(x-1,y);      // WEST
			if(coveredAdjacentValue(x,y,SOUTHWEST)) probeCell(x-1,y+1);  // SOUTHWEST
			if(coveredAdjacentValue(x,y,SOUTH)) probeCell(x,y+1);      // SOUTH
			if(coveredAdjacentValue(x,y,SOUTHEAST)) probeCell(x+1,y+1);  // SOUTHEAST	
		}
		return 0.0;
	}
	
	private int adjacentValue(int x, int y, int dir) {
		switch(dir) {
			case EAST:
				return ml_tab[x+1][y];
			case NORTHEAST:
				return ml_tab[x+1][y-1];
			case NORTH:
				return ml_tab[x][y-1];
			case NORTHWEST:
				return ml_tab[x-1][y-1];
			case WEST:
				return ml_tab[x-1][y];
			case SOUTHWEST:
				return ml_tab[x-1][y+1];
			case SOUTH:
				return ml_tab[x][y+1];
			case SOUTHEAST:
				return ml_tab[x+1][y+1];
		}
		return -2;
	}
        
	private boolean coveredAdjacentValue(int x, int y, int dir) {
		switch(dir) {
			case EAST:
				return cc_tab[x+1][y];
			case NORTHEAST:
				return cc_tab[x+1][y-1];
			case NORTH:
				return cc_tab[x][y-1];
			case NORTHWEST:
				return cc_tab[x-1][y-1];
			case WEST:
				return cc_tab[x-1][y];
			case SOUTHWEST:
				return cc_tab[x-1][y+1];
			case SOUTH:
				return cc_tab[x][y+1];
			case SOUTHEAST:
				return cc_tab[x+1][y+1];
		}
		return false;
	}
        
}