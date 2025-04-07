
import javax.swing.*;
import java.awt.*;

public class LetterTile extends JPanel {
    private char letter;
    private TileState state;

    public LetterTile() {
        this.letter = ' ';
        this.state = TileState.EMPTY;
        setPreferredSize(new Dimension(50, 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
    }

    public void setLetter(char letter) {
        this.letter = letter;
        repaint();
    }

    public char getLetter() {
        return letter;
    }

    public void setState(TileState state) {
        this.state = state;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        switch (state) {
            case CORRECT:
                setBackground(Color.GREEN);
                break;
            case PRESENT:
                setBackground(Color.ORANGE);
                break;
            case ABSENT:
                setBackground(Color.DARK_GRAY);
                break;
            default:
                setBackground(Color.WHITE);
        }

        g2d.setColor(state == TileState.EMPTY ? Color.BLACK : Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(String.valueOf(letter))) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(String.valueOf(letter), x, y);
    }
}
