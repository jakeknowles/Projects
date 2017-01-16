/*
 * TCSS 305 - Autumn 2015
 * Tetris
 */

package model;

//By adding colors, the import needed to be changed from the previous one.
import java.awt.Color;

/**
 * The different types of pieces that can be stored in a Board's grid.
 * 
 * @author Alan Fowler
 * @version Autumn 2015
 */
public enum Block {
    
    //I added my piece colors into this enumeration because it made more sense to me instead
    // of having my for loop find which letter, then paint.
    /** AN empty space in the grid. */
    EMPTY("", new Color(255, 255, 255)),
    /** A Block from an IPiece. */
    I("I", new Color(0, 255, 255)),
    /** A Block from a JPiece. */
    J("J", new Color(0, 0, 255)),
    /** A Block from an LPiece. */
    L("L", new Color(255, 128, 0)),
    /** A Block from an OPiece. */
    O("O", new Color(255, 255, 0)),
    /** A Block from an SPiece. */
    S("S", new Color(0, 255, 0)),
    /** A Block from a TPiece. */
    T("T", new Color(127, 0, 255)),
    /** A Block from a ZPiece. */
    Z("Z", new Color(255, 0, 0));
    
    //Needed field for colors.
    /** Color for each piece. */
    private Color myColor;
    
    //Needed field for colors.
    /** Letter for each piece. */
    private String myLetter;
       
    
    //Constructor for my implementation.
    /** Block Constructor.
     * 
     * @param theLetter theLetter for the piece.
     * @param theColor theColor for the piece.
     */
    Block(final String theLetter, final Color theColor) {
        myLetter = theLetter;
        myColor = theColor;
    }
    
    // Gets color.
    /** Gets color for piece.
     * 
     * @return myColor myColor is the pieces color.
     */
    public Color getColor() {
        return myColor;
    }

    /**toString returns the letter for the piece.
     * 
     * @return myLetter myLetter is the letter for the piece.
     */
    @Override
    public String toString() {
        return myLetter;
    }
}
