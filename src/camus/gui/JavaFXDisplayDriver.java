package camus.gui;

import camus.core.GofBoard;
import camus.core.GofCell;
import camus.core.GcgBoard;
import camus.core.GcgCell;
import camus.core.DisplayDriver;
import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JavaFXDisplayDriver implements DisplayDriver {
    private int sz;
    private TilePane tilePaneGof = new TilePane(5,5);
    private TilePane tilePaneGcg = new TilePane(5,5);
    private Color colorCellLive = Color.RED;
    private Color colorCellDead = Color.WHITE;
    private Color[] colorsArray;

    public JavaFXDisplayDriver(int boardSize, int cellSizePx, GofBoard gofBoard, GcgBoard gcgBoard) {
        sz = boardSize;
        tilePaneGof.setPrefRows(boardSize);
        tilePaneGof.setPrefColumns(boardSize);
        tilePaneGcg.setPrefRows(boardSize);
        tilePaneGcg.setPrefColumns(boardSize);
        
        intiColorsArray(gcgBoard.getPossibleState());
        
        GofCell[][] gofGrid = gofBoard.getGrid();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Color c = gofGrid[i][j].getState() ? colorCellLive : colorCellDead;
                Rectangle r = new Rectangle(cellSizePx, cellSizePx, c);
                tilePaneGof.getChildren().add(r);
                
                attachListenersGof(r, gofGrid[i][j]);
            }
        }
        
        GcgCell[][] gcgGrid = gcgBoard.getGrid();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Color c = colorsArray[gcgGrid[i][j].getState()];
                Rectangle r = new Rectangle(cellSizePx, cellSizePx, c);
                tilePaneGcg.getChildren().add(r);
                
                attachListenersGcg(r, gcgGrid[i][j]);
            }
        }
    }
    
    private void intiColorsArray(int p){
    	colorsArray = new Color[p];
        for(int i = 0; i<colorsArray.length; i++){
        	
        	switch (i) {
    		case 0:
        		colorsArray[i] = Color.BLUE;
        		break;
    		case 1:
        		colorsArray[i] = Color.RED;
        		break;
    		case 2:
        		colorsArray[i] = Color.YELLOW;
        		break;
    		case 3:
    			colorsArray[i] = Color.GREEN;
    			break;
    		case 4:
    			colorsArray[i] = Color.WHITE;
    			break;
    		case 5:
    			colorsArray[i] = Color.BLACK;
    			break;
    		case 6:
    			colorsArray[i] = Color.PINK;
    			break;
    		case 7:
    			colorsArray[i] = Color.SKYBLUE;
    			break;
    		case 8:
    			colorsArray[i] = Color.BROWN;
    			break;
    		default:
    			colorsArray[i] = new Color(Math.random(), Math.random(), Math.random(), Math.random());
        	}
        	
        }
    }
    
    @Override
    public void displayBoardGof(GofBoard gofBoard) {
        GofCell[][] gofGrid = gofBoard.getGrid();
        for (int i = 0; i < gofGrid.length; i++) {
            for (int j = 0; j < gofGrid[0].length; j++) {
                Rectangle r = (Rectangle) tilePaneGof.getChildren().get(boardToPaneCoords(i, j));
                r.setFill(gofGrid[i][j].getState() ? colorCellLive : colorCellDead);
            }
        }
    }
    
    @Override
    public void displayBoardGcg(GcgBoard gcgBoard) {
        GcgCell[][] gcgGrid = gcgBoard.getGrid();
        for (int i = 0; i < gcgGrid.length; i++) {
            for (int j = 0; j < gcgGrid[0].length; j++) {
                Rectangle r = (Rectangle) tilePaneGcg.getChildren().get(boardToPaneCoords(i, j));
                r.setFill(colorsArray[gcgGrid[i][j].getState()]);
            }
        }
    }

    public TilePane getPaneGof() {
        return tilePaneGof;
    }
    
    public TilePane getPaneGcg() {
        return tilePaneGcg;
    }

    private int boardToPaneCoords(int i, int j) {
        return i * sz + j;
    }
    
    private void attachListenersGcg(Rectangle r, GcgCell c) {
        r.setOnMousePressed(e -> { r.setFill(Color.GRAY); });

        r.setOnMouseClicked(e -> {
            int ns = c.getState();
            if(ns != 0)
            	ns--;
            else
            	ns = colorsArray.length - 1;
            r.setFill(colorsArray[ns]);
            c.setNewState(ns);
            c.updateState();
        });
    }
    
    private void attachListenersGof(Rectangle r, GofCell c) {
    	r.setOnMousePressed(e -> { r.setFill(Color.GRAY); });

        r.setOnMouseClicked(e -> {
            r.setFill(c.getState() ? colorCellDead : colorCellLive);
            c.setNewState(!c.getState());
            c.updateState();
        });
    }
}
