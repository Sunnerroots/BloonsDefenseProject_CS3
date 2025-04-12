import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.Canvas;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import tank.Tank1;


//Same as the tic-tac-toe game, however the board is scaled up with more spots, new shapes, and logic to account for empty spots below clicks

public class GameBack extends Canvas implements MouseListener
{

    //Setting up variables for board dimensions, scaling of the board for display size, and mouse coords
    private int mouseX, mouseY;
    private boolean mouseClicked, gameOver;
    private int mouseButton, prevMouseButton;
    private final static int SCALE = 50;
    private final static int BOARDWIDTH = 6;
    private final static int BOARDLENGTH = 7;
    private final static int WCOUNT = 4;
    private static int width = SCALE * BOARDWIDTH;
    private static int length = SCALE * BOARDLENGTH;
    private static Player player;
    private BufferedImage backgroundImage;


    //Creates background
    public GameBack()
    {
        mouseClicked = false;
        mouseX = mouseY = 0;
        mouseButton = 0;
        prevMouseButton = -1;
        player = new Player();

        try {
            backgroundImage = ImageIO.read(new File("src/images/bgs/level1.png")); // Update path accordingly
        } catch (IOException e) {
            System.err.println("Background image load error: " + e.getMessage());
        }

        addMouseListener(this);
        setBackground(Color.WHITE);

    }

    //Checks mouse click locations
    public void mouseClicked(MouseEvent e)
    {
        mouseClicked = true;
        mouseX = e.getX();
        mouseY = e.getY();
        mouseButton = e.getButton();
        repaint();
    }


    //Paints the inputs on the screen
    //First creates board by scaling up initial dimensions and drawing out the matrix
    public void paint(Graphics window)
    {
        boolean playGame = true;

        // ✅ Draw background image if loaded
        if (backgroundImage != null) {
            window.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            window.setColor(Color.white);
            window.fillRect(0, 0, 640, 480);
        }

        Font boldFont = new Font("TERMINAL", Font.BOLD, 16);
        window.setFont(boldFont);

// Bottom Y position
        int bottomY = getHeight() - 20;

// Gold text string
        String goldText = "Gold: " + player.getGold();
        int goldWidth = window.getFontMetrics().stringWidth(goldText);
        int goldX = getWidth() - goldWidth - 150;

// Draw gold (bottom-right)
        window.setColor(Color.BLACK);
        window.drawString(goldText, goldX + 1, bottomY - 20 + 1); // shadow above title
        window.setColor(Color.YELLOW);
        window.drawString(goldText, goldX, bottomY - 20);

        // Draw title below gold
        String title = "Tower Defense CS3";
        int titleWidth = window.getFontMetrics().stringWidth(title);
        int titleX = getWidth() - titleWidth - 150;

        window.setColor(Color.BLACK);
        window.drawString(title, titleX + 1, bottomY + 1); // shadow
        window.setColor(Color.BLUE);
        window.drawString(title, titleX, bottomY);

    }

    //Method that actually draws the colored circle
    public void markBoard()
    {

    }

    //Figures out who won by checking each possibility
    public boolean determineWinner(Graphics window)
    {
        String winner = "";
        return true;
    }





    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
