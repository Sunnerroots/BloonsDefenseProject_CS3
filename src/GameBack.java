import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.Canvas;

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
    private static ArrayList<Tank> bad = new ArrayList<>();


    //Creates background
    public GameBack()
    {
        mouseClicked = false;
        mouseX = mouseY = 0;
        mouseButton = 0;
        prevMouseButton = -1;
        player = new Player();

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
        //Sets colors and creates initial screen with empty board
        window.setColor(Color.white);
        window.fillRect(0, 0, 640, 480);
        //Insert the game map
        window.setFont(new Font("TERMINAL", Font.BOLD, 12));
        window.setColor(Color.blue);
        window.drawString("Tower Defense CS3", 550, 55);
        window.setColor(Color.yellow);
        window.drawString("Gold: " + player.getGold(), 55, 55);

        if(mouseClicked)
        {
            markBoard();
        }


    }

    //Method that detects the click
    public void markBoard()
    {
        if(player.getLevel() == 1)
        {
            bad.add(new Tank(mouseX, mouseY));
        }
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
