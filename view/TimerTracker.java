/*
 * TCSS 305 - Autumn 2015
 * Tetris
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Board;

/**
 * Works with timer events.
 * 
 * @author Jake Knowles
 * @version 10 December 2015
 */
public class TimerTracker implements ActionListener {

    /** Board using the timer. */
    private Board myBoard;
    
    /** TimerTracker constructor.
     * 
     * @param theBoard theBoard using the timer.
     */
    public TimerTracker(final Board theBoard) {
        super();
        myBoard = theBoard;
    }
    
    /** Given step method to use for board action events.
     * 
     * @param theEvent theEvent is the passed in event.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myBoard.step();
    }
}