package application;

import java.awt.EventQueue;
import view.MainGUI;

/**
 * Main for Tetris. 
 * 
 * @author Sally Ho
 * @version February 27, 2018
 */
public final class TetrisMain {

    /**
     * So CheckStyle won't yell at me.
     */
    private TetrisMain() {
        //Do nothing. 
    }
    
    /**
     * Main class to run everything. 
     * 
     * @param theArg Command line input.
     */
    public static void main(final String... theArg) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainGUI main = new MainGUI();
                main.createGUI();
            }
        });
    }
}
