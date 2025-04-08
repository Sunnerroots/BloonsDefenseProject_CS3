import java.awt.*;
// Player class to manage their curency
public class Player
{
    private int gold;
    private int reloadTime;
    private int level;

    public Player()
    {
        gold = 500;
        level = 1;
    }
    public void draw(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.drawString("Gold: " + gold, 10, 40);
    }

    public int getGold()
    {
        return gold;
    }

    public void setGold(int g)
    {
        gold += g;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int l)
    {
        level += l;
    }

}
