/*
 * TCSS 305 - Autumn 2015
 * Tetris
 */

package view;

/** 
* Class for running the whole Tetris game.
*  
* @author Jake Knowles
* @version 10 December 2015
*/ 
public final class TetrisMain { 
    
    /** Private Constructor. */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    
    /** Main method for starting Tetris.
     * @param theArgs the Args passed in arguments.
     */
    public static void main(final String[] theArgs) {
        new GUI().start();
    }

    
}
