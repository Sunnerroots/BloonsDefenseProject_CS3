import java.awt.*;

public class BabyTank
{
    private int health;
    private int dmg;
    private int x, y;
    private int speed;
    private int reloadSpeed;
    private int width;
    private int height;

    public BabyTank(int x, int y)
    {
        health = 150;
        dmg = 40;
        speed = 10;
        reloadSpeed = 5;
        this.x = x;
        this.y = y;
        width = 30;
        height = 30;
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
