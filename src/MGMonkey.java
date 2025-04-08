public class MGMonkey
{
    private int reloadTime;
    private int dmg;
    private int x, y;
    private int range;
    public MGMonkey(int x, int y)
    {
        reloadTime = 1;
        dmg = 10;
        range = 20;
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
