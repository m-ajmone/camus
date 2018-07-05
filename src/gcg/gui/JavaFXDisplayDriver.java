package gcg.gui;

import gcg.core.Board;
import gcg.core.Cell;
import gcg.core.DisplayDriver;
import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JavaFXDisplayDriver implements DisplayDriver {
    private int sz;
    private TilePane tilePane = new TilePane(5,5);
    private Color[] colorsArray;

    public JavaFXDisplayDriver(int boardSize, int cellSizePx, Board board) {
        sz = boardSize;
        tilePane.setPrefRows(boardSize);
        tilePane.setPrefColumns(boardSize);
        
        colorsArray = new Color[board.getPossibleState()];
        for(int i = 0; i<colorsArray.length; i++){
        	colorsArray[i] = new Color(Math.random(), Math.random(), Math.random(), Math.random());
        }
        
        Cell[][] g = board.getGrid();
        Integer x;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Color c = colorsArray[g[i][j].getState()];
                Rectangle r = new Rectangle(cellSizePx, cellSizePx, c);
                tilePane.getChildren().add(r);
                
                attachListeners(r, g[i][j]);
            }
        }
    }

    @Override
    public void displayBoard(Board board) {
        Cell[][] g = board.getGrid();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                Rectangle r = (Rectangle) tilePane.getChildren().get(boardToPaneCoords(i, j));
                r.setFill(colorsArray[g[i][j].getState()]);
            }
        }
    }

    public TilePane getPane() {
        return tilePane;
    }

    private int boardToPaneCoords(int i, int j) {
        return i * sz + j;
    }
    
    private void attachListeners(Rectangle r, Cell c) {
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
}
