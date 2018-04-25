/*
 * TCSS 305 - Winter 2018
 * Assignment 5 - Tetris
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;

/**
 * This is the class that will connect the entire GUI together. 
 * 
 * @author Sally Ho
 * @version February 27, 2018
 */
public class MainGUI extends JFrame implements Observer {

    /** SerialUID for Java. */
    private static final long serialVersionUID = -5056050661369885282L;
    
    /** 1000 milliseconds in a second for timer.*/
    private static final int MILLI = 1000;
    
    /** */
    private static final int WIDTH = 600; 
    
    /** */
    private static final int LENGTH = 900; 
    
    /** panel for instructions. */
    private PanelInstruction myInstruct; 
    
    /** Overall panel to add all the other panels. */
    private JPanel myOverallPanel;
    
    /** Menu bar class for menu. */
    private MenuBar myMenu; 
    
    /** the Board panel which draws shapes. */
    private PanelBoard myBoard;
    
    /** Panel for the new piece. */
    private PanelNewPiece myPiece; 
    
    /** the Timer.*/
    private Timer myTimer;
    
    /** Board class for  back end code.*/
    private Board myPlayBoard; 

    /** Map to put keys inside.*/
    private Map<Integer, Runnable> myKeys;
    
    /** Toggles the pause button.*/
    private boolean myToggle; 
    
    /** Clip for audio.*/
    private Clip myClip; 
    
//    /** */
//    private boolean myGame;
        
    /**
     * Constructor for the Main Frame.
     */
    public MainGUI() {
        super();
        helpConstruct();
        disableKey();
        setKeys();
    }
    
    /**
     * Method to help the constructor since it was getting full.
     */
    private void helpConstruct() {
        myPlayBoard = new Board();
        myOverallPanel = new JPanel();
        myInstruct = new PanelInstruction();
        myBoard = new PanelBoard();
        myPiece = new PanelNewPiece(); 
        myToggle = true; 
        addObservers();
        //every second timer goes off, action goes off. 
        myTimer = new Timer(MILLI, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                myPlayBoard.step();   
            }
        }); 
        myMenu = new MenuBar(myPlayBoard, myTimer);
        myKeys = new HashMap<>();
        
        final Object[] msg = {"I don't want to..", "I guess so..."};
        JOptionPane.showOptionDialog(this, "Let's play a game", "...",
                                     JOptionPane.YES_OPTION,
                                     JOptionPane.QUESTION_MESSAGE,
                                     new ImageIcon("./images/realsaw.PNG"), msg, msg[0]);
        
        final Object[] msg2 = {""};
        JOptionPane.showOptionDialog(this, "Interesting, I'll see you soon.", "....",
                                     JOptionPane.YES_OPTION,
                                     JOptionPane.QUESTION_MESSAGE,
                                     new ImageIcon("./images/interesting.PNG"),
                                     msg2, msg2[0]);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    /**
     * To create the overall GUI to send into frame. 
     */
    public void createGUI() {
        setTitle("Tetris");
        setPreferredSize(new Dimension(WIDTH, LENGTH));
        setResizable(false);
        myOverallPanel.add(myBoard, BorderLayout.WEST);
        myOverallPanel.setBackground(Color.BLACK);
        final JPanel sidePanel = new JPanel();
        final JLabel help = new JLabel(new ImageIcon("./images/0413.png"));
        myOverallPanel.add(help, BorderLayout.NORTH);
        sidePanel.add(myPiece, BorderLayout.WEST);
        sidePanel.add(myInstruct, BorderLayout.EAST);
        sidePanel.setBackground(Color.GRAY);
        myOverallPanel.add(sidePanel, BorderLayout.EAST);
        add(myOverallPanel);
        setJMenuBar(myMenu);
        pack();
        setVisible(true);
    }

    /**
     * Private helper method to help use key events. 
     */
    private void setKeys() {
        //Move the pieces left/right. 
        enableKey();
        myKeys.put(KeyEvent.VK_P, this::toggleTimer);
        
        addKeyListener(new KeyAdapter() {     
            
            @Override
            public void keyPressed(final KeyEvent theEvent) {
                super.keyPressed(theEvent);
                if (myKeys.containsKey(theEvent.getKeyCode())) {
                    myKeys.get(theEvent.getKeyCode()).run();
                }                
            }
        }); 
    }
    
    /** 
     * Method to add the observers. 
     */
    private void addObservers() {
        myPlayBoard.addObserver(myBoard);    
        myPlayBoard.addObserver(myPiece);
        myPlayBoard.addObserver(this);
    }
    
    /** 
     * Toggles the pause button. 
     */
    private void toggleTimer() {
        if (myToggle) {
            myTimer.stop();
            final Object[] msg = {"I don't know"};
            JOptionPane.showOptionDialog(this, "Why did you pause..?", "Come back and play",
                                         JOptionPane.YES_OPTION,
                                         JOptionPane.QUESTION_MESSAGE,
                                         new ImageIcon("./images/annabelle.PNG"), msg, msg[0]);
            disableKey();
        } else {
            myTimer.start();
            enableKey();
        }
        myToggle = !myToggle;
    }
    
    
    /**
     * Private helper method to enable the keys. 
     */
    private void enableKey() {
        myKeys.put(KeyEvent.VK_LEFT, this::handleLeft);
        myKeys.put(KeyEvent.VK_A, this::handleLeft);
        myKeys.put(KeyEvent.VK_RIGHT, this::handleRight);
        myKeys.put(KeyEvent.VK_D, this::handleRight);
        //Drop the piece. 
        myKeys.put(KeyEvent.VK_SPACE, this::handleDrop);
        //Downs the piece. 
        myKeys.put(KeyEvent.VK_DOWN, this::handleStep);
        myKeys.put(KeyEvent.VK_S, this::handleStep);
        //Rotates the piece clockwise. 
        myKeys.put(KeyEvent.VK_W, this::handleRotate);
        myKeys.put(KeyEvent.VK_UP, this::handleRotate);
    }
    
    /** 
     * Private helper method to disable the keys. 
     */
    private void disableKey() {
        myKeys.remove(KeyEvent.VK_LEFT);
        myKeys.remove(KeyEvent.VK_A);
        myKeys.remove(KeyEvent.VK_RIGHT);
        myKeys.remove(KeyEvent.VK_D);
        //Drop the piece. 
        myKeys.remove(KeyEvent.VK_SPACE);
        //Downs the piece. 
        myKeys.remove(KeyEvent.VK_DOWN);
        myKeys.remove(KeyEvent.VK_S);
        //Rotates the piece clockwise. 
        myKeys.remove(KeyEvent.VK_W);
        myKeys.remove(KeyEvent.VK_UP);
    }
    
    /**
     * private method to handle the left movements. 
     */
    private void handleLeft() {
        if (myTimer.isRunning()) {
            myPlayBoard.left();            
        }
    }
    
    /**
     * private method to handle the right movements.
     */
    private void handleRight() {
        if (myTimer.isRunning()) {
            myPlayBoard.right();            
        }
    }
    
    /**
     * private method to handle the rotate method. 
     */
    private void handleRotate() {
        if (myTimer.isRunning()) {
            myPlayBoard.rotateCW();            
        }
    }
    
    /**
     * private method to handle the step.  
     */
    private void handleStep() {
        if (myTimer.isRunning()) {
            myPlayBoard.step();            
        }
    }
    
    /**
     * private method to handle the drop.
     */
    private void handleDrop() {
        if (myTimer.isRunning()) {
            myPlayBoard.drop();            
        }
    }
    
    @Override
    public void update(final Observable arg0, final Object arg1) {
        if (arg1 instanceof Boolean) {
            myTimer.stop();
            myMenu.endGame();
            
//            try {
//                final AudioInputStream aIS = AudioSystem.getAudioInputStream
//                                (new File("./dingdong/lavvytown.wav").getAbsoluteFile());
//            } catch (final UnsupportedAudioFileException | IOException e) {
//                e.printStackTrace();
//            }
            try {
                myClip = AudioSystem.getClip();
            } catch (final LineUnavailableException e) {
                e.printStackTrace();
            }
            myClip.stop();
            
        }   
    }
    
}
