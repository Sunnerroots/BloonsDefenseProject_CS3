package turret;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BaseTurret {
    protected int x, y;
    protected int range;
    protected int reloadTime;
    protected int damage;
    protected int width, height;
    protected BufferedImage image;
    protected double angle = 0;
    protected long lastShotTime = 0;

    public BaseTurret(int x, int y, int range, int reloadTime, int damage, String imagepath)
    {
        this.x = x;
        this.y = y;
        this.range = range;
        this.reloadTime = reloadTime;
        this.damage = damage;

        loadImage(imagepath);
    }

    private void loadImage(String path)
    {
        try
        {
            image = ImageIO.read(new File(path));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e)
        {
            System.err.println("Failed to load turret image: " + e.getMessage());
            width = height = 40; // fallback size
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x, y);
            g2.rotate(angle);
            g2.drawImage(image, -width / 2, -height / 2, null);
            g2.dispose();
        } else {
            g.setColor(Color.BLACK);
            g.fillOval(x - 10, y - 10, 20, 20);
        }
    }

    public void aimAt(int targetX, int targetY) {
        this.angle = Math.atan2(targetY - y, targetX - x) + Math.PI / 2;
    }

    public boolean canShoot() {
        return System.currentTimeMillis() - lastShotTime >= reloadTime;
    }

    public void markShotFired() {
        lastShotTime = System.currentTimeMillis();
    }
}

