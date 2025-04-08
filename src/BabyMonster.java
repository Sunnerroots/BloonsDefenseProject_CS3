public class BabyMonster
{
    private int health;
    private int dmg;
    private int x, y;
    public BabyMonster(int x, int y)
    {
        health = 150;
        dmg = 40;
        this.x = x;
        this.y = y;
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

}
