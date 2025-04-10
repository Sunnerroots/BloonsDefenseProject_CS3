import java.awt.*;

public class Tank
{
    private int x;
    private int y;
    private int health;
    private int speed;
    private int dmg;
    private int reloadSpeed;
    private int width;
    private int height;

    public Tank(int x, int y)
    {
        this.x = x;
        this.y = y;
        health = 450;
        speed = 7;
        dmg = 120;
        reloadSpeed = 14;
        width = 55;
        height = 55;
    }

    public int getDmg()
    {
        return dmg;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int hit)
    {
        health -= hit;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
