/*
 * TCSS 305 - Autumn 2015
 * Tetris
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;

/**
 * The GUI setup visually for Tetris.
 * 
 * @author Jake Knowles
 * @version 10 December 2015
 */

public class GUI implements Observer {
    
    /** Timer speed of falling blocks. */
    private static final int SPEED = 400;
    
    /** JFrame Size to fit game. */
    private static final Dimension FRAME_SIZE = new Dimension(490, 550);
    
    /** Simple background color. */
    private static final Color BACKGROUND_COLOR = Color.white;
    
    /** Size of game pieces. */
    private static final int PIECE_SIZE = 25;
    
    /** Spacing between WaitingPanel and KeyPanel. */
    private static final int SPACING = 3;
    
    /** Width of WaitingPiecePanel. */
    private static final int WAITPANEL_WIDTH = 100;
    
    /** Width of KeyPanel. */
    private static final int KEYPANEL_WIDTH = 80;
    
    /** KeyPanel height. */
    private static final int KEYPANEL_HEIGHT = 262;
    
    /** ScorePanel height. */
    private static final int SCOREPANEL_HEIGHT = 130;
    
    /** Required Tetris board width. */
    private static final int WIDTH = 10;
    
    /** Required Tetris board height. */
    private static final int HEIGHT = 20;
    
    /** Color Purple Constant. */
    private static final Color PURPLE = new Color(75, 0, 130);
    
    /** Color Gold Constant. */
    private static final Color GOLD = new Color(255, 215, 0);
    
    /** Tetris JFrame. */
    private JFrame myFrame;
    
    /** Used for making Tetris board. */
    private Board myBoard;
    
    /** Allows pieces to fall. */
    private Timer myTimer;
    
    /** Panel where Tetris game is played. */
    private JPanel myActionPanel;
    
    /** Waiting piece panel. */
    private JPanel myWaitingPiecePanel;
    
    /** Key Panel. */
    private JPanel myKeyPanel;
    
    /** Boolean to see if game is over. */
    private boolean myGameOver;
    
    /** Boolean for if game is paused or not. */
    private boolean myGamePaused;
    
    /** ScorePanel with score contents. */
    private JPanel myScorePanel;
       
    /** Constructor. */
    public GUI() {
        begin();
    }
    
    /** Sets up the frame and panels. */
    private void begin() {
        myFrame = new JFrame();
        myBoard = new Board(WIDTH, HEIGHT);
        myBoard.addObserver(this);
        
        // Three Main Panels within Frame
        myWaitingPiecePanel = new WaitingPiecePanel(myBoard);
        myActionPanel = new TPanel(myBoard);
        myKeyPanel = new KeyPanel();
       
        //Create timer and set game paused and game over states.
        myTimer = new Timer(SPEED, new TimerTracker(myBoard));
        myScorePanel = new KeepScore(myBoard, myTimer, SPEED);       
        
        myGamePaused = false;
        final SetControls mka = new SetControls();
        myFrame.addKeyListener(mka);
        
    }
    
    /** Initializes JFrame features. */
    public void start() {

        setupTetris();
        addKeyAndWaitingPanel();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLocationByPlatform(true);
        myFrame.pack();
        myFrame.setMinimumSize(FRAME_SIZE);
        myFrame.setTitle("Tetris");
        myFrame.setVisible(true);  
        final int startGame = JOptionPane.showConfirmDialog(myFrame, "Start a new game?", 
                                                    "Goodluck!", JOptionPane.YES_NO_OPTION);
        if (startGame == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else {
            myTimer.start();
        }
    }
    
    /** Creates the main panel for the game. */
    public void setupTetris() {
        final JPanel tetrisPanel = new JPanel();
        tetrisPanel.setBackground(PURPLE);
        myActionPanel.setPreferredSize(new Dimension(myBoard.getWidth() * PIECE_SIZE,
                                                     myBoard.getHeight() * PIECE_SIZE));
        myActionPanel.setBackground(BACKGROUND_COLOR);
              
        tetrisPanel.add(myActionPanel);
        myFrame.add(tetrisPanel);
    }
    
    /** Sets up panels. */
    public void addKeyAndWaitingPanel() {
        
        final Box waitingBox = new Box(BoxLayout.Y_AXIS);
        final JPanel nextPiece = new JPanel();
        nextPiece.setBackground(GOLD);       
        myWaitingPiecePanel.setBackground(PURPLE);
        myWaitingPiecePanel.setPreferredSize(new Dimension(WAITPANEL_WIDTH * 2, 
                                                             WAITPANEL_WIDTH));
        waitingBox.add(myWaitingPiecePanel);        
        
        //http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
        // Looked up box layout features, createVerticalStrut *
        waitingBox.add(Box.createVerticalStrut(SPACING * SPACING));
        
        myKeyPanel.setBackground(GOLD);
        myKeyPanel.setPreferredSize(new Dimension(KEYPANEL_WIDTH, KEYPANEL_HEIGHT));
        myKeyPanel.setBorder(BorderFactory.createLineBorder(PURPLE, WIDTH));
        
        waitingBox.add(myKeyPanel);
        
        myScorePanel.setBackground(PURPLE);
        myScorePanel.setPreferredSize(new Dimension(WAITPANEL_WIDTH, SCOREPANEL_HEIGHT));
       
        waitingBox.add(myScorePanel);

        nextPiece.add(waitingBox);
        
        myFrame.add(nextPiece, BorderLayout.WEST);
    }
    
    
    /** Update updates each panel.
     * @param theObject theObject needing updating.
     * @param theArg theArg passed in.
     */
    @Override
    public void update(final Observable theObject, final Object theArg) {
        myWaitingPiecePanel.repaint();
        myActionPanel.repaint();
        myScorePanel.repaint();
        myGameOver =  myBoard.isGameOver();
    }
    
    /** Class to connect keys Tetris blocks. */
    public class SetControls extends KeyAdapter {
        
        /** Constructor. */
        public SetControls() {
            super();
        }
        
        /** Creates Escape and Pause keys, and also the five
         * keys to move the pieces. */
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                if (myGamePaused) {
                    myTimer.start();
                    myGamePaused = !myGamePaused;
                    ((TPanel) myActionPanel).isPaused();
                } else {
                    myTimer.stop();
                    myGamePaused = !myGamePaused;
                    ((TPanel) myActionPanel).isPaused();
                }
            }

            if (myGameOver && theEvent.getKeyCode() == KeyEvent.VK_N) {
                final int newGame = JOptionPane.showConfirmDialog(myFrame, "New game?", 
                                                "Try Again?", JOptionPane.YES_NO_OPTION);
                if (newGame == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }             
                
                myBoard.newGame(WIDTH, HEIGHT, null);
                ((TPanel) myActionPanel).reset();
                ((KeepScore) myScorePanel).reset();
                myTimer.stop();
                myGamePaused = false;
                helper();
                
            }
            
            if (!myGamePaused) {
                switch (theEvent.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        myBoard.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        myBoard.moveRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        myBoard.moveDown();
                        break;
                    case KeyEvent.VK_SPACE:
                        myBoard.hardDrop();
                        break;                   
                    case KeyEvent.VK_UP:
                        myBoard.rotate();
                        break;
                    default:
                        break;
                }
            }
            
            if (theEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ((TPanel) myActionPanel).isPaused();
                myGameOver = true;
                myGamePaused = true;
                myActionPanel.repaint();
                myTimer.stop();
                if (JOptionPane.showConfirmDialog(myFrame, 
                                                  "Are you sure you want to exit Tetris?",
                            "Exit", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                                
                    myFrame.setVisible(false);
                    System.exit(0);   
                }                 

            }
        }  
                
        /** Freezes blocks until user clicks "Yes" on new game. */
        public void helper() {
            myTimer.start();
        }
    }    
}