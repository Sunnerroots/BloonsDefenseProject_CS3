package turret;

import java.awt.*;

public class BaseTurret {
    protected int x, y;
    protected int range;
    protected int reloadTime;
    protected int damage;

    public BaseTurret(int x, int y, int range, int reloadTime, int damage) {
        this.x = x;
        this.y = y;
        this.range = range;
        this.reloadTime = reloadTime;
        this.damage = damage;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x - 10, y - 10, 20, 20);
    }
}

