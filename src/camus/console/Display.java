package camus.console;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import camus.core.GofBoard;
import camus.core.GofCell;
import camus.core.GcgBoard;
import camus.core.GcgCell;
import camus.core.DisplayDriver;

public class Display {
    public static DisplayDriver getDriver() {
        DisplayDriver driver;
        
        try {
            driver = new EclipseDriver();
        } catch (AWTException e) {
            // TODO: implement a better fall-back
            driver = new ConsoleDriver();
        }
        
        return driver;
    }
    
    private Display() {}

    private static class EclipseDriver extends ConsoleDriver {
        private Robot eclipse;
        
        public EclipseDriver() throws AWTException {
            eclipse = new Robot();
        }
        
        public void displayBoards(GofBoard gofBoard, GcgBoard gcgBoard) {
            //cleanConsole();
            super.displayBoardGof(gofBoard);
            super.displayBoardGcg(gcgBoard);
        }
        
        private void cleanConsole() {
            eclipse.keyPress(KeyEvent.VK_SHIFT);
            eclipse.keyPress(KeyEvent.VK_F10);
            eclipse.keyRelease(KeyEvent.VK_SHIFT);
            eclipse.keyRelease(KeyEvent.VK_F10);
            eclipse.keyPress(KeyEvent.VK_R);
            eclipse.keyRelease(KeyEvent.VK_R);
        }
    }
}