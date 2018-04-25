/*
 * TCSS 305 - Winter 2018
 * Assignment 5 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is the class that will show the instructions for keys. 
 * 
 * @author Sally Ho
 * @version February 27, 2018
 */
public class PanelInstruction extends JPanel {

    /** SerialUID for Java.*/
    private static final long serialVersionUID = 5447772692319158247L;
    
    /** */
    private static final int DIMENSION_WIDTH = 120; 
    
    /** */
    private static final int DIMENSION_LENGTH = 200;
    
    /** */
    private static final String NEW_LINE = "\n";

    /**
     * Construct the Instructions. 
     */
    public PanelInstruction() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(DIMENSION_LENGTH, DIMENSION_WIDTH));
        setBackground(Color.GRAY);
        addText();
        
    }
    
    /** 
     * This is a private method to create the instructions. 
     */
    private void addText() {
        final JLabel left = new JLabel("Move Left: left arrow, 'a', and 'A'" + NEW_LINE);
        final JLabel right = new JLabel("Move Right: right arrow, 'd', and 'D'" + NEW_LINE);
        final JLabel rotate = new JLabel("Rotate: up arrow, 'w' and 'W'" + NEW_LINE);
        final JLabel down = new JLabel("Down: down arrow, 's' and 'S'" + NEW_LINE);
        final JLabel drop = new JLabel("Drop: spacebar" + NEW_LINE);
        final JLabel pause = new JLabel("Pause: 'p'/'P'" + NEW_LINE);
        
        add(left);
        add(right);
        add(rotate);
        add(down);
        add(drop);
        add(pause);
                        
    }
}
