import java.awt.*;
// Tower class for the tower postion
public class Tower
{
    private int x, y;
    private int range;
    private int health;
    
    public Tower(int x, int y, int lvl)
    {
        this.x = x;
        this.y = y;
        range = 100;
        switch(lvl)
        {
            case 1:
                health = 1300;
                break;
            case 2:
                health = 1700;
                break;
            case 3:
                health = 2300;
                break;
            default:
                health = 2500;
                break;
        }


    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(x - 20, y - 20, 40, 40);
    }
}
