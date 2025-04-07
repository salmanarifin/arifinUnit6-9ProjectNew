import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WordleBoard extends JPanel {

    private static final int ROWS = 6;
    private static final int COLS = 5;
    private LetterTile[][] tiles;
    private String targetWord;
    private int currentRow;
    private int currentCol;
    private boolean gameWon;

    public WordleBoard(String targetWord) {
        this.targetWord = targetWord.toUpperCase();
        this.currentRow = 0;
        this.currentCol = 0;
        this.gameWon = false;

        setLayout(new GridLayout(ROWS, COLS, 5, 5));
        setPreferredSize(new Dimension(300, 360));

        tiles = new LetterTile[ROWS][COLS];
        initializeTiles();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    public void initializeTiles() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                tiles[row][col] = new LetterTile();
                add(tiles[row][col]);
            }
        }
    }

    public void handleKeyPress(KeyEvent e) {
        if (gameWon || currentRow >= ROWS) return;

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && currentCol > 0) {
            currentCol--;
            tiles[currentRow][currentCol].setLetter(' ');
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && currentCol == COLS) {
            checkGuess();
        } else if (Character.isLetter(e.getKeyChar()) && currentCol < COLS) {
            tiles[currentRow][currentCol].setLetter(Character.toUpperCase(e.getKeyChar()));
            currentCol++;
            repaint();
        }
    }

    public void checkGuess() {
        StringBuilder guess = new StringBuilder();
        for (int i = 0; i < COLS; i++) {
            guess.append(tiles[currentRow][i].getLetter());
        }

        String guessStr = guess.toString();

        if (guessStr.equals(targetWord)) {
            colorRow(currentRow, TileState.CORRECT);
            gameWon = true;
            showEndDialog("You successfully guessed the word!", true);
        } else {
            boolean[] markedTarget = new boolean[COLS];
            boolean[] markedGuess = new boolean[COLS];

            // First pass: mark exact matches
            for (int i = 0; i < COLS; i++) {
                if (guessStr.charAt(i) == targetWord.charAt(i)) {
                    tiles[currentRow][i].setState(TileState.CORRECT);
                    markedTarget[i] = true;
                    markedGuess[i] = true;
                }
            }

            // Second pass: mark present/absent
            for (int i = 0; i < COLS; i++) {
                if (!markedGuess[i]) {
                    char c = guessStr.charAt(i);
                    boolean found = false;

                    // Check remaining letters in target
                    for (int j = 0; j < COLS; j++) {
                        if (!markedTarget[j] && targetWord.charAt(j) == c) {
                            markedTarget[j] = true;
                            found = true;
                            break;
                        }
                    }

                    tiles[currentRow][i].setState(found ? TileState.PRESENT : TileState.ABSENT);
                }
            }

            currentRow++;
            currentCol = 0;

            if (currentRow >= ROWS) {
                showEndDialog("You lost! The word was: " + targetWord, false);
            }
        }
    }

    public void colorRow(int row, TileState state) {
        for (int i = 0; i < COLS; i++) {
            tiles[row][i].setState(state);
        }
    }

    public void showEndDialog(String message, boolean win) {
        ImageIcon icon;
        if (win) {
            icon = new ImageIcon(getClass().getResource("4627b40bc348b750f5897b1f32c85884-Photoroom.jpg")); // Replace with your winning image
        } else {
            icon = new ImageIcon(getClass().getResource("4627b40bc348b750f5897b1f32c85884-Photoroom.png")); // Replace with your losing image
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                message + "\nWould you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    public void restartGame() {
        currentRow = 0;
        currentCol = 0;
        gameWon = false;
        targetWord = new WordList().getRandomWord().toUpperCase();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                tiles[row][col].setLetter(' ');
                tiles[row][col].setState(TileState.EMPTY);
            }
        }

        repaint();
        requestFocusInWindow();
    }
}
