
import javax.swing.*;
import java.awt.*;

public class WordleGame {
    private WordleBoard board;
    private WordList wordBank;
    private String targetWord;

    public WordleGame() {
        wordBank = new WordList();
        targetWord = wordBank.getRandomWord();
        board = new WordleBoard(targetWord);
    }

    public void start() {
        JFrame frame = new JFrame("Welcome to Wordle!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}