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
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Block;

/**
 * This is the class that will show the main playing board. 
 * 
 * @author Sally Ho
 * @version February 27, 2018
 */
public class PanelBoard extends JPanel implements Observer {

    /** SerialUID for Java. */
    private static final long serialVersionUID = 7112595773772699823L;
    
    /** Different shade of purple for z block.*/
    private static final Color Z_BLOCK = new Color(76, 82, 127);
    
    /** Different shade of purple for l block. */
    private static final Color L_BLOCK = new Color(102, 113, 204);
    
    /** Different shade of purple for j block.*/
    private static final Color J_BLOCK = new Color(0, 8, 76);
    
    /** Different shade of purple for t block.*/
    private static final Color T_BLOCK = new Color(20, 25, 60);
    
    /** Width of the Board.*/
    private static final int BOARD_WIDTH = 300; 
    
    /** Length of the board.*/
    private static final int BOARD_LENGTH = 600; 
    
    /** How many blocks in the width. */
    private static final int BOARD_W_BLOCK = 10; 
    
    /** How many blocks in the length.*/
    private static final int BOARD_L_BLOCK = 20;
    
    /** Individual block sizes.*/
    private static final int BLOCK_SIZE = 30; 
    
    /** 2D array of blocks to paint the board.  */
    private Block[][] myBlock; 
    
    /** Clip for background sound.*/
    private Clip myClip;
    
    /**
     * Constructor for the playing board. 
     */
    public PanelBoard() {
        super();
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_LENGTH));
        setBackground(Color.WHITE); 
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        myBlock = new Block[BOARD_L_BLOCK][BOARD_W_BLOCK];
    }

    /**
     * update method to send to observer. 
     * 
     * @param theObservable is the observable. 
     * @param theArg is the argument being passed in from the observable.
     */
    @Override
    public void update(final Observable theObservable, final Object theArg) {
        if (theArg instanceof Block[][]) {
            myBlock = (Block[][]) theArg;
            repaint();
        } 
        if (theArg instanceof Integer[]) {
            AudioInputStream aIS = null;
            try {
                aIS = AudioSystem.getAudioInputStream
                                (new File("./dingdong/giggle.wav").
                                 getAbsoluteFile());
            } catch (final UnsupportedAudioFileException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                myClip = AudioSystem.getClip();
            } catch (final LineUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                myClip.open(aIS);
            } catch (final LineUnavailableException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            myClip.start();
        }

    }
    
    /**
     * the paint component to draw or paint the shapes and stuff. 
     * 
     * @param theG is the graphics being passed in to draw. 
     */
    @Override
    protected void paintComponent(final Graphics theG) {
        super.paintComponent(theG);
        final Graphics2D g2d = (Graphics2D) theG;         
        
        for (int i = myBlock.length - 1; i >= 0; i--) {
            for (int j = myBlock[i].length - 1; j >= 0; j--) {
                if (myBlock[i][j] != null) {
                    setColors(myBlock[i][j], g2d);

                    g2d.fillRect(j * getWidth() / BOARD_W_BLOCK, 
                                 -i * getHeight() / BOARD_L_BLOCK + getHeight() 
                                 - getHeight() / BOARD_L_BLOCK, 
                                 BLOCK_SIZE, BLOCK_SIZE);
                    
                    //Put the grid around the tetris pieces. 
                    final Shape grid = new Rectangle2D.Double(j * getWidth() / BOARD_W_BLOCK,
                                                              -i * getHeight() / BOARD_L_BLOCK
                                                              + getHeight() - getHeight() 
                                                              / BOARD_L_BLOCK, BLOCK_SIZE, 
                                                              BLOCK_SIZE);
                    g2d.setPaint(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.draw(grid);
                } 
                if (myBlock[i][j] == null) {
                    g2d.setPaint(Color.GRAY);
                    g2d.fillRect(j * getWidth() / BOARD_W_BLOCK,
                                 -i * getHeight() / BOARD_L_BLOCK
                                 + getHeight() - getHeight() 
                                 / BOARD_L_BLOCK, BLOCK_SIZE, 
                                 BLOCK_SIZE);
                }
                
            }
        }   
    }
    
    /** 
     * Sets the color for the pieces. 
     * 
     * @param theBlock is the Block object being passed in. 
     * @param theG is the Graphics2d being passed in.
     */
    private void setColors(final Block theBlock, final Graphics2D theG) {
        if (theBlock == Block.I) {
            theG.setPaint(Color.DARK_GRAY);
        } else if (theBlock == Block.O) {
            theG.setPaint(Color.LIGHT_GRAY);
        } else if (theBlock == Block.S) {
            theG.setPaint(Color.BLACK);
        } else if (theBlock == Block.Z) {
            theG.setPaint(Z_BLOCK);
        } else if (theBlock == Block.L) {
            theG.setPaint(L_BLOCK);
        } else if (theBlock == Block.J) {
            theG.setPaint(J_BLOCK);
        } else if (theBlock == Block.T) {
            theG.setPaint(T_BLOCK);
        }
    }

}
