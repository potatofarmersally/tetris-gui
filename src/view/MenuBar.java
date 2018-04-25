/*
 * TCSS 305 - Winter 2018
 * Assignment 5 - Tetris
 */
package view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Board;

/**
 * This is the Class that will hold the MenuBar to 
 * play a new game/ end a game, or exit. 
 * 
 * @author Sally Ho
 * @version February 27, 2018
 */
public class MenuBar extends JMenuBar {

    /** Serial UID for Java. */
    private static final long serialVersionUID = -111659266189879540L;

    /** Menu tab of the game. */
    private final JMenu myGame; 
    
    /** passing in the board class.*/
    private final Board myBoard; 
    
    /** new game item.*/
    private final JMenuItem myNewGame; 
    
    /** end game item.*/
    private final JMenuItem myEndGame; 
    
    /** exit game item.*/
    private final JMenuItem myExit; 
    
    /** passing in the timer.*/
    private final Timer myTimer; 
    
    /** initial clip when it starts. */
    private Clip myClip; 
    
    /** clip of when it ends.*/
    private Clip myEndClip;

    
    /**
     * Constructor for the menu bar. 
     * 
     * @param theBoard is the board object being passed in. 
     * @param theTimer is the timer being passed in. 
     */
    public MenuBar(final Board theBoard, final Timer theTimer) {
        super();
        setVisible(true);
        myBoard = theBoard; 
        myGame = new JMenu("Game");
        add(myGame);
        myNewGame = new JMenuItem("New Game");
        myEndGame = new JMenuItem("End Game");
        myExit = new JMenuItem("Exit");
        myTimer = theTimer;
        constructGame();
        
    }

    /**
     * Helper to help construct the menu bar. 
     */
    private void constructGame() {
        myNewGame.addActionListener(theEvent -> newGame());
        myEndGame.addActionListener(theEvent -> endGame());
        myEndGame.setEnabled(false);
        myExit.addActionListener(theEvent -> exitGame());

        myGame.add(myNewGame);
        myGame.add(myEndGame);
        myGame.addSeparator();
        myGame.add(myExit);
        setClip();
    }
    
    /**
     * What happens when a new game is clicked. 
     */
    private void newGame() {
        myNewGame.setEnabled(false);
        myBoard.newGame();
        myTimer.start();
        myEndGame.setEnabled(true);
        myClip.setMicrosecondPosition(0);
        myClip.start();
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
        myEndClip.stop();
    }

    
    /**
     * What happens when the end game is clicked. 
     */
    protected void endGame() {
        myEndGame.setEnabled(false);
        myTimer.stop();
        myNewGame.setEnabled(true);
        myClip.stop();
        myEndClip.setMicrosecondPosition(0);
        myEndClip.start();
        myEndClip.loop(Clip.LOOP_CONTINUOUSLY);

        JOptionPane.showMessageDialog(this, "You shouldn't have done that. :)",
                                      "WARNING!!!",
                                      0, new ImageIcon("./images/chucky.PNG"));
        
        final Object[] msg = {"Game Over"};
        JOptionPane.showOptionDialog(this, "", "",
                                     JOptionPane.YES_OPTION,
                                     JOptionPane.QUESTION_MESSAGE,
                                     new ImageIcon("./images/ring.gif"), msg, msg[0]);
    }

    /** 
     * sets the initial clip. 
     */
    private void setClip() {      
        AudioInputStream aIS = null;
        //try to open the file and catch if it doesn't. 
        try {
            aIS = AudioSystem.getAudioInputStream
                            (new File("./dingdong/lavvytown.wav").getAbsoluteFile());
        } catch (final UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        
        //try to get the clip and catch if it doesn't. 
        try {
            myClip = AudioSystem.getClip();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        }
        
        // try to open the clip and catch if it doesn't. 
        try {
            myClip.open(aIS);
        } catch (final LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        
        AudioInputStream aIS2 = null;
        
        try {
            aIS2 = AudioSystem.getAudioInputStream
                            (new File("./dingdong/aol.wav").getAbsoluteFile());
        } catch (final UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        try {
            myEndClip = AudioSystem.getClip();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            myEndClip.open(aIS2);
        } catch (final LineUnavailableException | IOException e) {
            e.printStackTrace();
        }


    }
    
    /**
     * What happens when the exit game is called. 
     */
    private void exitGame() {
        System.exit(0);
    }

}
