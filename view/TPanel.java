/*
 * TCSS 305 - Autumn 2015
 * Tetris Project
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import model.AbstractPiece;
import model.Block;
import model.Board;
import model.Piece;

/**
 * Creates the pieces for the main T Panel.
 * 
 * @author Jake Knowles
 * @version 10 December 2015
 */
public class TPanel extends JPanel {
    
    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 1L;

    /** Empty String. */
    private static final String EMPTY = "";
    
    /** String (0) for drawing. */
    private static final int ZERO = 0;
    
    /** Size of "pause/game over" pop-up text. */
    private static final int WORD_SIZE = 20;
    
    /** String for filling rectangle. */
    private static final String COLOR = "fill";
    
    /** String for drawing rectangle. */
    private static final String DRAW = "draw";
    
    /** Size of the grid line spacing. */
    private static final int GRID_SIZING = 25;
    
    /** Good size for shapes to fit. */
    private static final int SIZE = 25;
    
    /** Boolean for if game is paused or not. */
    private static final Color PURPLE = new Color(75, 0, 130);
    
    /** Board for TPanel to be used on. */
    private Board myBoard;
    
    /** Game paused state. */
    private boolean myGamePaused;
    
    /** Game over state. */
    private boolean myGameOver;
    
    
    /** Constructor, sets the states for myGameOver and myGamePaused. 
     * 
     * @param theBoard theBoard is the passed in Tetris board.
     */
    public TPanel(final Board theBoard) {
        super();
        myBoard = theBoard;
        myGameOver = false;
        myGamePaused = false;
    }
    
    /** Paint Component draws the grid on the Tetris panel and then draws the 
     * desired piece.
     * 
     * @param theGraphics theGraphics is the passed in object.
     */
    @Override  
    public void paintComponent(final Graphics theGraphics) {  
        super.paintComponent(theGraphics); 
        final Graphics2D g2d = (Graphics2D) theGraphics; 
        
        final int width = myBoard.getWidth(); 
        final int height = myBoard.getHeight(); 
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON); 
        
        // draw purple game grid
        g2d.setColor(PURPLE); 
        for (int i = 0; i < Math.max(width, height); i++) { 
            final int lines = i * GRID_SIZING; 
            
            //draws horizontal lines
            g2d.draw(new Line2D.Double(lines, ZERO, lines, height * GRID_SIZING));
            
            //draws vertical lines
            g2d.draw(new Line2D.Double(ZERO, lines, width * GRID_SIZING, lines));
            
            if (myBoard.isGameOver()) { 
                myGameOver = true; 
                myGamePaused = true; 
            }
        } 
               
        //update blocks
        drawCurrentPiece(g2d, height, width);
        drawFrozenBlocks(g2d, height, width);
        handlePausedGame(g2d);
    }
      
    /** Draws pieces for Tetris to begin. 
     * 
     * @param theG2D theG2D is the passed in object.
     * @param theHeight theHeight is the height of the piece.
     * @param theWidth theWidth is the width of the piece.
     */
    private void drawCurrentPiece(final Graphics2D theG2D, final int theHeight, 
                                  final int theWidth) {
            
        final Piece piece = (AbstractPiece) myBoard.getCurrentPiece(); 
        final int[][] currentPiece = ((AbstractPiece) piece).getBoardCoordinates(); 
         
        //Calculates rows
        for (int row = 0; row < theHeight; row++) { 
            //Calculates columns
            for (int column = 0; column < theWidth; column++) { 
                //Creates blocks from column and rows
                for (int block = 0; block < currentPiece.length; block++) { 
                    if (currentPiece[block][1] == row  
                            && currentPiece[block][0] == column) { 
                         
                        theG2D.setColor(PURPLE); 
                        rectangle(theG2D, SIZE * column, 
                                 (SIZE * theHeight) - ((row + 1) * SIZE),  
                                 SIZE, SIZE, COLOR); 
                         
                        theG2D.setColor(((AbstractPiece) piece).getBlock().getColor()); 
                        rectangle(theG2D, 
                                  SIZE * column, 
                                 (SIZE * theHeight) - ((row + 1) * SIZE),  
                                 SIZE - 1, SIZE - 1, COLOR); 
                         
                        theG2D.setColor(PURPLE); 
                        rectangle(theG2D, SIZE * column, 
                                 (SIZE * theHeight) - ((row + 1) * SIZE),  
                                 SIZE, SIZE, DRAW); 
                    } 
                } 
            } 
        } 
    }
    
    /** DrawFrozenBlocks draws the frozen blocks that are already placed. 
     * 
     * @param theG2d theG2d is the passed in object.
     * @param theHeight theHeight is the height of the piece.
     * @param theWidth theWidht is the width of the piece.
     */
    private void drawFrozenBlocks(final Graphics2D theG2d, final int theHeight, 
                                  final int theWidth) {
        final List<Block[]> blocks = myBoard.getFrozenBlocks(); 
        //Calculates rows
        for (int row = 0; row < blocks.size(); row++) { 
            //Calculates columns
            for (int column = 0; column < theWidth; column++) { 
                
                //blocks.get requires (int index) Block[]
                final Block piece = blocks.get(row)[column];
                if (!piece.toString().equals(EMPTY)) {  
                    theG2d.setColor(PURPLE); 
                    rectangle(theG2d, column * SIZE, 
                             (theHeight * SIZE) - ((row + 1) * SIZE),  
                             SIZE, SIZE, DRAW); 
                    
                    theG2d.setColor(PURPLE); 
                    rectangle(theG2d, column * SIZE, 
                             (theHeight * SIZE) - ((row + 1) * SIZE),  
                             SIZE, SIZE, COLOR);
                     
                    theG2d.setColor(piece.getColor()); 
                    rectangle(theG2d, column * SIZE, 
                             (theHeight * SIZE) - ((row + 1) * SIZE),  
                             SIZE - 1, SIZE - 1, COLOR); 
                } 
            } 
        } 
    }
    
    /** HandlePausedGame handles a paused game situation.
     * 
     * @param theG2d theG2d is the passed in object.
     */
    private void handlePausedGame(final Graphics2D theG2d) {
        if (myGamePaused) { 
            String pauseText = "Game Paused!"; 
            
            if (myGameOver) { 
                pauseText = "Game Over!";
            } 
            
            final Rectangle2D rect = new Rectangle2D.Double();
            final Rectangle2D bounds = rect.getBounds2D();
            final Font font = new Font(Font.MONOSPACED, 
                                       Font.TYPE1_FONT + Font.HANGING_BASELINE, 
                                       WORD_SIZE);

            theG2d.setFont(font);          
            final int x = (int) (bounds.getWidth() + bounds.getHeight() + 64);
            final int y = (int) (bounds.getWidth() + bounds.getHeight() + 200);
            
            theG2d.fill(rect);
            theG2d.setColor(Color.BLACK);
            theG2d.drawString(pauseText, x, y);    

        }
    }
    
    /** Rectangle makes rectangles based on coloring or drawing.
     * 
     * @param theG2d theG2d is the object passed in for drawing/coloring.
     * @param theX theX the x coordinate.
     * @param theY theY the y coordinate.
     * @param theWidth theWidth the width of the rectangle.
     * @param theHeight theHeight of the rectangle.
     * @param theString theString is color or draw.
     */
    private void rectangle(final Graphics2D theG2d, final double theX,  
                          final double theY, final double theWidth,  
                          final double theHeight, final String theString) { 
        final Rectangle2D rect =  
                new Rectangle2D.Double(theX, theY, theWidth, theHeight); 
        if (DRAW.equals(theString)) { 
            theG2d.draw(rect); 
        } else { 
            theG2d.fill(rect); 
        } 
    }
    
    /** isPaused checks for if paused, and repaints. */
    public void isPaused() { 
        myGamePaused = !myGamePaused; 
        repaint(); 
    } 
    
    /** Sets game over state. */
    public void isGameOver() { 
        myGameOver = true;
        myGamePaused = true;         
    } 
    
    /** Needed to start a new game. */
    public void reset() { 
        myGameOver = false; 
        myGamePaused = false;       
    } 
}