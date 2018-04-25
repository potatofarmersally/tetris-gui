/*
 * TCSS 305 - Winter 2018
 * Assignment 5 - Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Point;
import model.TetrisPiece;

/**
 * This is the Class to display the new piece. 
 * 
 * @author Sally Ho
 * @version February 27, 2018
 */
public class PanelNewPiece extends JPanel implements Observer {

    /** SerialUID for Java.*/
    private static final long serialVersionUID = 3883641047953482556L;
    
    /** Different shade of purple for z block.*/
    private static final Color Z_BLOCK = new Color(76, 82, 127);
    
    /**  Different shade of purple for l block.*/
    private static final Color L_BLOCK = new Color(102, 113, 204);
    
    /** Different shade of purple for j block.*/
    private static final Color J_BLOCK = new Color(0, 8, 76);
    
    /** Different shade of purple for t block.*/
    private static final Color T_BLOCK = new Color(20, 25, 60);
    
    /** Dimension size.*/
    private static final int DIMENSION = 200; 
    
    /** Block size.*/
    private static final int BLOCK = 30; 
    
    /** Moves og x by 60. */
    private static final int X_MOVE = 60;
    
    /** Moves 40 for center.*/
    private static final int MOVE_FORTY = 40;
    
    /** Moves 140 down. */
    private static final int MOVE_ONEFORTY = 140;
    
    /** Moves 120 for center.*/
    private static final int MOVE_ONETWENTY = 120;
   
    /** The tetrispiece enum.*/
    private TetrisPiece myPiece;
    
    /** Array of points. */
    private Point[] myPoint; 

    
    /**
     * Constructor to construct a new panel for the new piece.
     */
    public PanelNewPiece() {
        super();
        setPreferredSize(new Dimension(DIMENSION, DIMENSION));
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
    }

    /**
     * update method for observers. 
     * 
     * @param theO is the observer. 
     * @param theArg is argument being passed in. 
     */
    @Override
    public void update(final Observable theO, final Object theArg) {
        if (theArg instanceof TetrisPiece) {
            myPiece = (TetrisPiece) theArg;
            myPoint = myPiece.getPoints();
            repaint();
        }
    }
    
    /**
     * paint component to paint the shape and stuff. 
     * 
     * @param theGraphics is the graphics in which i am painting in.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics; 
        if (myPoint != null) {
            for (int i = 0; i < myPiece.getPoints().length; i++) {
                setColors(myPiece, g2d);
                final Point point = myPiece.getPoints()[i];
                int x = point.x() * BLOCK + X_MOVE;
                int y = -point.y() * BLOCK + MOVE_ONETWENTY;
                if (myPiece == TetrisPiece.O) {
                    x = point.x() * BLOCK + MOVE_FORTY;
                    y = -point.y() * BLOCK + MOVE_ONETWENTY;
                } else if (myPiece == TetrisPiece.I) {
                    x = point.x() * BLOCK + MOVE_FORTY;
                    y = -point.y() * BLOCK + MOVE_ONEFORTY;
                }
                g2d.fillRect(x, y, BLOCK, BLOCK);                
                final Shape grid = new Rectangle2D.Double(x, y, BLOCK, BLOCK);
                g2d.setPaint(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
                g2d.draw(grid);
            }
        }
    }
    
    /** 
     * Sets the color for the pieces. 
     * 
     * @param theBlock is the TetrisPiece being passed in. 
     * @param theG is the Graphics2D
     */
    private void setColors(final TetrisPiece theBlock, final Graphics2D theG) {
        if (theBlock == TetrisPiece.I) {
            theG.setPaint(Color.DARK_GRAY);
        } else if (theBlock == TetrisPiece.O) {
            theG.setPaint(Color.LIGHT_GRAY);
        } else if (theBlock == TetrisPiece.S) {
            theG.setPaint(Color.BLACK);
        } else if (theBlock == TetrisPiece.Z) {
            theG.setPaint(Z_BLOCK);
        } else if (theBlock == TetrisPiece.L) {
            theG.setPaint(L_BLOCK);
        } else if (theBlock == TetrisPiece.J) {
            theG.setPaint(J_BLOCK);
        } else if (theBlock == TetrisPiece.T) {
            theG.setPaint(T_BLOCK);
        }
    }
    
}
