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
import javax.swing.Timer;

import model.Board;

/**
 * Creates how scoring is decided.
 * 
 * @author Jake Knowles
 * @version 10 December 2015
 */
public class KeepScore extends JPanel {
    
    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 1L;
    
    /** Font size of words in Score Panel. */
    private static final int FONT_SIZE = 18; 
    
    /** Lines to complete to level up. */
    private static final int LINES = 2; 
    
    /** Score for each line completed. */
    private static final int LINE_POINTS = 10; 

    /** Array of strings displayed. */
    private static final String[] STATS = {
        "100 Points  >>>  1 Line",  
        "BONUS 100 Each Level!",
        "Total Lines Cleared: ",
        "Lines To Clear: ",
        "Score: ",  
        "Level: ",  
    }; 

    /** Lines for BONUS. */
    private static final int BONUS_LINES = 4; 
     
    /** Speed change after levels advance. */
    private static final int DELAY = 75; 
     
    /** Level Bonus. */
    private static final int LEVEL_BONUS = 100; 
    
    /** GOLD color constant. */
    private static final Color GOLD = new Color(255, 215, 0);
    
    /** Constant for placing words perfectly. */
    private static final int POSITIONING = 40;
    
    /** Constant for placing words perfectly. */
    private static final int POSITIONING1 = 45;
    
    /** Constant for placing words perfectly. */
    private static final int POSITIONING2 = 57;
    
    /** Constant for placing words perfectly. */
    private static final int POSITIONING3 = 79;
    
    /** Constant for placing words perfectly. */
    private static final int POSITIONING4 = 80;
    
    /** Constant for placing words perfectly. */
    private static final int SCORE_LEVEL_SIZE = 19;
    
    /** The board used. */
    private Board myBoard; 
    
    /** Changing board size user later on. */
    private int mySetBoardSize; 
     
    /** My score. */
    private int myScore; 
     
    /** My level. */
    private int myLevel; 
    
    /** My timer for the blocks to fall down. */
    private Timer myTimer; 
    
    /** Set pace. */
    private int myTimerPace; 
     
    /** Completed lines. */
    private int myCompletedLines;
 
    /** Keep Score constructor initializes the whole score panel.
     * 
     * @param theBoard theBoard is the board being used.
     * @param theTimer theTimer is the used timer.
     * @param theTimerPace theTimerPace is the pace the timer is set at.
     */
    public KeepScore(final Board theBoard, final Timer theTimer, final int theTimerPace) { 
        super(); 
        myBoard = theBoard; 
        myTimer = theTimer; 
        myTimerPace = theTimerPace;  
        mySetBoardSize = 0; 
        myLevel = 1;
        myScore = 0;
        myCompletedLines = 0; 
    } 
 
    /** PaintComponent paints and draws the scoring panel.
     * 
     * theGraphics theGraphics is the passed in object.
     */
    @Override  
    public void paintComponent(final Graphics theGraphics) {          
        checkState();
        super.paintComponent(theGraphics); 
        final Graphics2D g2d = (Graphics2D) theGraphics; 
 
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON); 
        g2d.setColor(GOLD);
        for (int i = 0; i <= 1; i++) { 
            g2d.drawString(STATS[i], POSITIONING, (i + 1) * FONT_SIZE); 
        } 
        
        final int toComplete = LINES - (myCompletedLines % LINES);  
        int i = 1 + 1; 
        
        g2d.drawString(STATS[i] + myCompletedLines, POSITIONING1, (i++ + 1) * FONT_SIZE);
        g2d.drawString(STATS[i] + toComplete, POSITIONING2, (i++ + 1) * FONT_SIZE); 
        g2d.drawString(STATS[i] + myScore, POSITIONING3, (i++ + 1) * SCORE_LEVEL_SIZE);
        g2d.drawString(STATS[i] + myLevel, POSITIONING4, (i++ + 1) * SCORE_LEVEL_SIZE); 
    }      
   
    /** Checks state of score components-- Gives user points, and increases the level. */
    private void checkState() { 
        final int newBoard = myBoard.getFrozenBlocks().size(); 
        if (mySetBoardSize < newBoard) { 
            mySetBoardSize = newBoard; 
        } else { 
            final int completed = mySetBoardSize - newBoard; 
            myCompletedLines += completed;
            myScore += LINE_POINTS * completed * LINE_POINTS; 
            
            if ((myCompletedLines / LINES) + 1 > myLevel) { 
                myScore += LEVEL_BONUS;
                myLevel++; 
                myTimer.setDelay(myTimerPace - DELAY);
            } 
            
            if (BONUS_LINES == completed) { 
                myScore += LINE_POINTS; 
            } 
            mySetBoardSize = newBoard; 
        } 
    } 
     
    /** Reset's score panel for new game. */
    public void reset() { 
        mySetBoardSize = 0; 
        myScore = 0;
        myLevel = 1; 
        myCompletedLines = 0; 
    } 
}