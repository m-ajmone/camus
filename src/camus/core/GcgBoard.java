package camus.core;

public class GcgBoard {
    private GcgCell[][] grid;
    private int height=3; //bottom right pos: grid[height-1][width-1]
    private int width=3;
    private int possibleState = 5;
    
    public GcgBoard(GcgCell[][] grid) {
        this.grid = grid;
        height = width = grid.length;
    }

    /**
     * @param height
     * @param width
     * @param p probability that Cell is alive at start
     */
    public GcgBoard(int height, int width, int p) {
        this.height=height;
        this.width = width;
        this.possibleState = p;
        grid = new GcgCell[height][width];
        
        for (int h=0; h<grid.length; h++){
            for (int w=0; w<grid[h].length; w++){
                grid[h][w] = new GcgCell();
                int r = (int)(Math.random()*possibleState);
                grid[h][w].setNewState(r);
                grid[h][w].updateState();
                /*for(int i=1; i <= possibleState; i++){   //inizializzo celle con stato casuale
                	if((i/possibleState) >= r){
                		grid[h][w].setNewState(i - 1);
                        grid[h][w].updateState();
                        break;
                	}
                }*/
            }
        }
    }
    
    public GcgBoard(GcgBoard cgcBoard){
    	this.height = cgcBoard.getSize();
    	this.width = cgcBoard.getSize();
    	int size = cgcBoard.getGrid().length;
    	this.grid = new GcgCell[size][size];
    	for(int i = 0; i < size; i++){
    		for(int j = 0; j < size; j++){
    			this.grid[i][j] = new GcgCell(cgcBoard.getGrid()[i][j].getState());
    		}
    	}
    }
    
    public GcgCell[][] getGrid() {
        return grid;
    }
    
    public int getSize() {
        return width;
    }
    
    public int getPossibleState(){
    	return this.possibleState;
    }

    public void dominateNeighbours(int row, int col) {
    	int myState = getCellState(row, col);
        int stateDominated = myState;
    	if(stateDominated != 0)
    		stateDominated--;
    	else
    		stateDominated = possibleState - 1;
    	
        if (row != 0 && col != 0){    //1
            if(getCellState(row-1,col-1) == stateDominated){
            	grid[row-1][col-1].setNewState(myState);
            }
        }
        
        if (row != 0){
            if(getCellState(row-1,col) == stateDominated){ //2
            	grid[row-1][col].setNewState(myState);
            }
        }
        
        if (row != 0 && col != width-1){//3
            if(getCellState(row-1,col+1) == stateDominated){
            	grid[row-1][col+1].setNewState(myState);
            }
        }
        if (col != 0){
            if(getCellState(row,col-1) == stateDominated){ //4
            	grid[row][col-1].setNewState(myState);
            }
        }
        //self
        if (col != width-1){
            if(getCellState(row,col+1) == stateDominated){ //6
            	grid[row][col+1].setNewState(myState);
            }
        }

        if (row != height-1 && col != 0){
            if(getCellState(row+1,col-1) == stateDominated){ //7
            	grid[row+1][col-1].setNewState(myState);
            }
        }

        if (row != height-1){
            if(getCellState(row+1,col) == stateDominated){ //8
            	grid[row+1][col].setNewState(myState);
            }
        }

        if (row != height-1 && col != width-1){
            if(getCellState(row+1,col+1) == stateDominated){ //9
            	grid[row+1][col].setNewState(myState);
            }
        }
        return;
    }

    public int getCellState(int row, int col) {
        return grid[row][col].getState();
    }

    public void update() {
        prepare();
        commit();
    }

    /**
     * Assigns new state to individual Cells 
     * according to GoF rules
     */
    private void prepare() {
        for (int h=0; h<grid.length; h++){
            for (int w=0; w<grid[h].length; w++){
            	dominateNeighbours(h,w);
            }
        }
    }

    /**
     * Updates Cell state based on newState
     */
    private void commit() {
        for (int h=0; h<grid.length; h++){
            for (int w=0; w<grid[h].length; w++){
                grid[h][w].updateState();
            }
        }
    }
    
    
}
