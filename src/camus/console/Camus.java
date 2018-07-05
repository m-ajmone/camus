package camus.console;
import java.util.Scanner;

import camus.core.GofBoard;
import camus.core.GcgBoard;
import camus.core.DisplayDriver;

public class Camus {

    public static void main(String[] args) throws Exception {
        System.out.print("Please enter number of iterations to run: ");
        Scanner in = new Scanner(System.in);
        int iterations = in.nextInt();
        in.close();
        
        DisplayDriver dd = Display.getDriver();
        GofBoard gofB = new GofBoard(10, 10, 0.3);
        GcgBoard gcgB = new GcgBoard(10, 10, 10);

        for (int i = 0; i <= iterations; i++) {
            dd.displayBoardGof(gofB);
            dd.displayBoardGcg(gcgB);
            gofB.update();
            gcgB.update();
            Thread.sleep(1000);
        }
    }

}
