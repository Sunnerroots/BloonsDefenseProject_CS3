import java.awt.*;
// Player class to manage their curency
public class Player
{
    private int gold;
    private int reloadTime;
    private int level;
    private int hp;


    public Player() {
        gold = 300;
        level = 1;
        hp = 10; // Start with 10 HP
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

    public int getHP() {
        return hp;
    }

    public void loseHP(int amount) {
        hp -= amount;
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
