/*
 * TCSS 305 - Autumn 2015
 * Tetris
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import model.AbstractPiece;
import model.Board;
import model.Piece;

/**
 * Creates the pieces for the waiting panel.
 * 
 * @author Jake Knowles
 * @version 10 December 2015
 */
public class WaitingPiecePanel extends JPanel {

    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 1L;

    /** Block size. */
    private static final int PIECE_SIZE = 25; 
    
    /** Waiting Piece Panel height. */
    private static final int HEIGHT = 5; 
    
    /** Waiting Piece Panel width. */
    private static final int WIDTH = 10;
    
    /** Grid sizing, vertical and horizontal. */
    private static final int GRID_SIZE = 25;
    
    /**  New Color GOLD. */
    private static final Color GOLD = new Color(255, 215, 0);
    
    /** Board for WaitingPiecePanel to work on. */
    private Board myBoard;      
    
    /** Constructor.
     * 
     * @param theBoard theBoard is the board for the waiting piece panel.
     */
    public WaitingPiecePanel(final Board theBoard) { 
        super(); 
        myBoard = theBoard;
    } 
    
    /** PaintComponent paints the waiting piece. 
     *  PaintComponent is from power paint. *
     *  
     *  theGraphics theGraphics is the passed in object.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {  
        super.paintComponent(theGraphics); 
        final Graphics2D g = (Graphics2D) theGraphics; 
 
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON); 
        //Draws grid under blocks
        g.setColor(GOLD); 
        
        for (int i = 0; i < HEIGHT; i++) { 
            for (int j = 0; j < WIDTH; j++) {
                final int vertical = GRID_SIZE * i;
                final int horizontal = GRID_SIZE * j; 
                
                final Line2D grid =  
                    new Line2D.Double(0, vertical, PIECE_SIZE * WIDTH, vertical); 
                g.draw(grid); 
                
                final Line2D grid2 =  
                    new Line2D.Double(horizontal, 0, horizontal, PIECE_SIZE * HEIGHT); 
                g.draw(grid2); 
            } 
        } 
        
        drawCurrentPiece(g, myBoard.getNextPiece()); 
    }
 
    /** DrawCurrentPiece draws the current piece in the waiting panel.
     *    
     * @param theG2D theG2D is the passed in object.
     * @param theNextPiece theNextPiece is the nextPiece getting painted.
     */
    private void drawCurrentPiece(final Graphics2D theG2D, final Piece theNextPiece) {
        final int[][] currentPiece = ((AbstractPiece) theNextPiece).getRotation(); 

        final int pieceConstant = 4; 
        
        for (int rows = 0; rows < pieceConstant; rows++) { 
            
            for (int columns = 0; columns < pieceConstant; columns++) { 
                final int draw = columns; 
                
                for (int i = 0; i < currentPiece.length; i++) { 
                    
                    if (currentPiece[i][0] == columns 
                                    && 
                                    currentPiece[i][1] == rows) {                          
                        
                        //Sets panel background white
                        theG2D.setColor(Color.WHITE); 
                        final Rectangle2D rect =  
                                new Rectangle2D.Double((pieceConstant * PIECE_SIZE) 
                                                       - (draw * PIECE_SIZE),  
                                                       (pieceConstant * PIECE_SIZE)  
                                                       - ((rows + 1) * PIECE_SIZE), 
                                                       PIECE_SIZE,  
                                                       PIECE_SIZE); 
                        theG2D.fill(rect); 
                         
                        //Gets color for shape to be painted
                        theG2D.setColor(((AbstractPiece) theNextPiece).getBlock().getColor()); 
                        rect.setRect((pieceConstant * PIECE_SIZE)  
                                     - (draw * PIECE_SIZE),  
                                     (pieceConstant * PIECE_SIZE)  
                                     - ((rows + 1) * PIECE_SIZE), 
                                     PIECE_SIZE - 1,  
                                     PIECE_SIZE - 1); 
                        
                        theG2D.fill(rect); 
                         
                        theG2D.setColor(Color.BLACK); 
                        rect.setRect((pieceConstant * PIECE_SIZE)  
                                     - (draw * PIECE_SIZE),  
                                     (pieceConstant * PIECE_SIZE) - ((rows + 1) * PIECE_SIZE), 
                                     PIECE_SIZE,  
                                     PIECE_SIZE); 
                        
                        theG2D.draw(rect); 
                    } 
                } 
            } 
        }
    }
}