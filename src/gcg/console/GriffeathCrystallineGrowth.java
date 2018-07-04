package gcg.console;
import java.util.Scanner;

import gcg.core.Board;
import gcg.core.DisplayDriver;

public class GriffeathCrystallineGrowth {

    public static void main(String[] args) throws Exception {
        System.out.print("Please enter number of iterations to run: ");
        Scanner in = new Scanner(System.in);
        int iterations = in.nextInt();
        
        
        DisplayDriver dd = Display.getDriver();
        Board b = new Board(15, 15, 10);

        for (int i = 0; i <= iterations; i++) {
            dd.displayBoard(b);
            b.update();
            Thread.sleep(300);
        }
        in.close();
    }

}
