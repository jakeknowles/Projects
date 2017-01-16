/*
 * TCSS 305 - Autumn 2015
 * Tetris
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * KeyPanel displays the keyboard controls inside an inner panel.
 * 
 * @author Jake Knowles
 * @version 10 December 2015
 */
public class KeyPanel extends JPanel {
    
    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 1L;
    
    /** Font size for text in KeyPanel. */
    private static final int FONT_SIZE = 30; 
    
    /** Color Purple Constant. */
    private static final Color PURPLE = new Color(75, 0, 130);
    
    /** Word Placement. */
    private static final int PLACEMENT = 55;
    
    /** Text for keys in KeyPanel. */ 
    private static final String[] KEYS =  
        {"Left: ", "Right: ", "Down: ", "Rotate: ",  
            "Drop: ", "Pause: ", "New Game: ", "Exit: "}; 
    
    /** Text for key values in KeyPanel. */
    private static final String[] KEY_VALUES =  
        {"Left Arrow", "Right Arrow", "Down Arrow", "Up Arrow", 
            "Spacebar ", "P", "N", "Esc"}; 
    
    /** Constructor for TetrisPanel. */
    public KeyPanel() { 
        super(); 
    } 

    /** PaintComponent writes all the given keys and their values
     * to the KeyPanel.
     * 
     * @param theGraphics theGraphics is the passed in object.
     */
    @Override  
    public void paintComponent(final Graphics theGraphics) {  
        super.paintComponent(theGraphics); 
        final Graphics2D g2d = (Graphics2D) theGraphics; 

        //Helps text look better
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON); 
        g2d.setColor(PURPLE);
        for (int i = KEYS.length - 1; i >= 0; i--) { 
            g2d.drawString(KEYS[i] + KEY_VALUES[i], PLACEMENT, (i + 1) * FONT_SIZE); 
        }         
    } 
} 