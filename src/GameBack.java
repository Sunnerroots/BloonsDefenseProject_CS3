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
    private Path board;
    private final static int SCALE = 50;
    private final static int BOARDWIDTH = 6;
    private final static int BOARDLENGTH = 7;
    private final static int WCOUNT = 4;
    private static int width = SCALE * BOARDWIDTH;
    private static int length = SCALE * BOARDLENGTH;


    //Same constructor as tic-tac-toe
    public GameBack()
    {
        mouseClicked = false;
        mouseX = mouseY = 0;
        mouseButton = 0;
        prevMouseButton = -1;

        board = new Grid(BOARDWIDTH, BOARDLENGTH);

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
        //Sets colors and creates initial screen with empty board
        window.setColor(Color.white);
        window.fillRect(0, 0, 640, 480);
        window.setFont(new Font("TERMINAL", Font.BOLD, 12));
        window.setColor(Color.blue);
        window.drawString("Connect 4", 550, 55);
        window.setColor(Color.red);
        window.drawString("left click: red ⊛", 550, 85);
        window.setColor(Color.green);
        window.drawString("right click: green ◍", 550, 105);
        window.setColor(Color.blue);

        for (int i = 0; i < board.getNumRows(); i++)
        {
            for (int j = 0; j < board.getNumCols(); j++)
            {
                window.drawRect(SCALE * (j + 1), SCALE * (i + 1), SCALE, SCALE);
            }
        }

        //Marks board with clicks
        if (mouseClicked)
        {
            markBoard();
            board.drawGrid(window);

            mouseClicked = false;
        }

        determineWinner(window);
    }

    //Method that actually draws the colored circle
    public void markBoard()
    {
        //Checks mouse button and draws the right turn accordingly
        if (mouseX >= length / BOARDLENGTH && mouseX <= length + length /
           BOARDLENGTH && mouseY >= width / BOARDWIDTH && mouseY <= width + width / BOARDWIDTH)
        {
            int c = mouseX / 50 - 1;
            for (int r = board.getNumRows() - 1; r >= 0; r--)
            {
                Piece piece = (Piece) board.getSpot(r, c);
                if (mouseButton == MouseEvent.BUTTON1 && prevMouseButton != mouseButton)
                {
                    if (piece == null)
                    {
                        board.setSpot(r, c, new Piece(c * 50 + 55, r * 50 + 55, length / BOARDLENGTH - 10, width / BOARDWIDTH - 10, "⊛", Color.BLUE));
                        prevMouseButton = mouseButton;
                    }
                    else
                    {
                        continue;
                    }
                }
                if (mouseButton == MouseEvent.BUTTON3 && prevMouseButton != mouseButton)
                {
                    if (piece == null)
                    {
                        board.setSpot(r, c, new Piece(c * 50 + 55, r * 50 + 55, length / BOARDLENGTH - 10, width / BOARDWIDTH - 10, "◍", Color.GREEN));
                        prevMouseButton = mouseButton;
                    }
                }
            }
        }
    }

    //Figures out who won by checking each possibility
    public boolean determineWinner(Graphics window)
    {
        String winner = "";
        ArrayList<Piece> sequence = new ArrayList<>(WCOUNT);

        //horizontal
        for (int r = 0; r < board.getNumRows(); r++)
        {
            for (int c = 0; c < board.getNumCols() - WCOUNT + 1; c++)
            {
                for (int i = 0; i < WCOUNT; i++)
                {
                    if (board.getSpot(r, c + i) == null)
                    {
                        sequence.add(i, new Piece(""));
                    }
                    else
                    {
                        sequence.add(i, (Piece) board.getSpot(r, c + i));
                    }
                }

                boolean same = false;
                for (int i = 0; i < WCOUNT; i++)
                {
                    if (sequence.get(i).getName().equalsIgnoreCase(sequence.get(0).getName()) && !sequence.get(i).getName().equals(""))
                    {
                        same = true;
                    }
                    else
                    {
                        same = false;
                        break;
                    }
                }

                if (same)
                {
                    winner = sequence.get(0).getName() + " wins horizontally!";
                }

                sequence.clear();
            }
        }

        //vertical
        for (int c = 0; c < board.getNumCols(); c++)
        {
            for (int r = 0; r < board.getNumRows() - WCOUNT + 1; r++)
            {
                for (int i = 0; i < WCOUNT; i++)
                {
                    if (board.getSpot(r + i, c) == null)
                    {
                        sequence.add(i, new Piece(""));
                    }
                    else
                    {
                        sequence.add(i, (Piece) board.getSpot(r + i, c));
                    }
                }

                boolean same = false;
                for (int i = 0; i < WCOUNT; i++)
                {
                    if (sequence.get(i).getName().equalsIgnoreCase(sequence.get(0).getName()) && !sequence.get(i).getName().equals(""))
                    {
                        same = true;
                    }
                    else
                    {
                        same = false;
                        break;
                    }
                }

                if (same)
                {
                    winner = sequence.get(0).getName() + " wins vertically!";
                }

                sequence.clear();
            }
        }


        //Next two diagonal
        if (winner.equals(""))
        {
            for (int r = 0; r < board.getNumRows() - WCOUNT + 1; r++)
            {
                for (int c = 0; c < board.getNumCols() - WCOUNT + 1; c++)
                {
                    for (int i = 0; i < WCOUNT; i++) {
                        if (board.getSpot(r + i, c + i) == null)
                        {
                            sequence.add(i, new Piece(""));
                        }
                        else
                        {
                            sequence.add(i, (Piece) board.getSpot(r + i, c + i));
                        }
                    }

                    boolean same = false;
                    for (int i = 0; i < WCOUNT; i++)
                    {
                        if (sequence.get(i).getName().equalsIgnoreCase(sequence.get(0).getName()) && !sequence.get(i).getName().equals(""))
                        {
                            same = true;
                        }
                        else
                        {
                            same = false;
                            break;
                        }
                    }

                    if (same)
                    {
                        winner = sequence.get(0).getName() + " wins diagonally!";
                    }

                    sequence.clear();
                }
            }
        }

        //check diagonal
        if (winner.equals(""))
        {
            for (int r = board.getNumRows() - 1; r >= WCOUNT - 1; r--)
            {
                for (int c = 0; c < board.getNumCols() - WCOUNT + 1; c++)
                {
                    for (int i = 0; i < WCOUNT; i++) {
                        if (board.getSpot(r - i, c + i) == null)
                        {
                            sequence.add(i, new Piece(""));
                        }
                        else
                        {
                            sequence.add(i, (Piece) board.getSpot(r - i, c + i));
                        }
                    }

                    boolean same = false;
                    for (int i = 0; i < WCOUNT; i++)
                    {
                        if (sequence.get(i).getName().equalsIgnoreCase(sequence.get(0).getName()) && !sequence.get(i).getName().equals(""))
                        {
                            same = true;
                        }
                        else
                        {
                            same = false;
                            break;
                        }
                    }

                    if (same)
                    {
                        winner = sequence.get(0).getName() + " wins diagonally!";
                    }

                    sequence.clear();
                }
            }
        }

        //Tie
        if (winner.indexOf("No name") > -1)
        {
            board.drawGrid(window);
            return false;
        }
        else if (board.drawGrid(window) && winner.length() == 0)
        {
            winner = "Tie!\n\n";
        }

        if (winner.length() > 0)
        {
            window.setFont(new Font("TERMINAL", Font.BOLD, 22));
            window.setColor(Color.orange);
            window.drawString(winner, 120, 395);
            try {
                Thread.currentThread().sleep(1500);
            } catch (Exception e) {
            }
            repaint();
            return true;
        }
        return false;
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
