//(c) A+ Computer Science
//www.apluscompsci.com
//Name - Sunrut Mohanty

//Runs the game
import javax.swing.JFrame;
public class Game extends JFrame
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    public Game()
    {
        super("Bloons Defense CS3");
        setSize(WIDTH,HEIGHT);
        getContentPane().add(new GameBack());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main( String args[] )
    {
        Game run = new Game();
    }
}
