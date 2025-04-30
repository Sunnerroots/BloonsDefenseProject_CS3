package turret;

import tank.BaseTank;

import java.awt.*;

public class Projectile {
    private double x, y;
    private double dx, dy;
    private double speed = 5;
    private int size = 8;
    private int damage;

    public Projectile(int startX, int startY, int targetX, int targetY, int damage) {
        this.x = startX;
        this.y = startY;
        this.damage = damage;

        double angle = Math.atan2(targetY - startY, targetX - startX);
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval((int)x - size / 2, (int)y - size / 2, size, size);
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public int getDamage() {
        return damage;
    }

    public boolean intersects(BaseTank tank) {
        Rectangle projectileRect = new Rectangle((int)x - 4, (int)y - 4, 8, 8); // assuming 8x8 projectile
        Rectangle tankRect = new Rectangle(
                tank.getX() + 6,
                tank.getY() + 6,
                tank.getWidth() - 12,
                tank.getHeight() - 12); // shrink box by 4px padding

        return projectileRect.intersects(tankRect);
    }

}
