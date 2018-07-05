package camus.console;

import camus.core.GofBoard;
import camus.core.GofCell;
import camus.core.GcgBoard;
import camus.core.GcgCell;
import camus.core.DisplayDriver;

public class ConsoleDriver implements DisplayDriver {
    public void displayBoardGof(GofBoard gofBoard) {
        GofCell[][] grid = gofBoard.getGrid();
        
        String border = String.format("+%0" + 2*grid.length + "d+", 0).replace("0","-");
        
        System.out.println(border);
        
        for (GofCell[] row : grid) {
            String r = "|";
            for (GofCell c : row) {
                r += c.getState() ? "* " : "  ";
            }
            r += "|";
            System.out.println(r);
        }
        
        System.out.println(border);
    }
    
    public void displayBoardGcg(GcgBoard gcgBoard) {
        GcgCell[][] grid = gcgBoard.getGrid();
        
        String border = String.format("+%0" + 2*grid.length + "d+", 0).replace("0","-");
        
        System.out.println(border);
        
        for (GcgCell[] row : grid) {
            String r = "|";
            for (GcgCell c : row) {
                r += c.getState() + " ";
            }
            r += "|";
            System.out.println(r);
        }
        
        System.out.println(border);
    }
}
