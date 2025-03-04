import javax.swing.*;
// Main Game class
public class Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private JFrame window;
    private GamePanel gamePanel;

    public Game() {
        window = new JFrame("Tower Defense");
        gamePanel = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gamePanel);
        window.pack();
        window.setVisible(true);

        gamePanel.startGame();
    }

    public static void main(String[] args) {
        new Game();
    }
}
