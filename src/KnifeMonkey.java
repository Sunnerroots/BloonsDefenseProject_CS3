public class KnifeMonkey
{
    private int reloadTime;
    private int dmg;
    private int x, y;
    private int range;
    public KnifeMonkey(int x, int y)
    {
        reloadTime = 8;
        dmg = 15;
        range = 15;
        this.x = x;
        this.y = y;
    }

    public int getReloadTime()
    {
        return reloadTime;
    }

    public int getDmg()
    {
        return dmg;
    }
}
