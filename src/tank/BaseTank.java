package tank;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BaseTank {
    protected int x, y;
    protected int health;
    protected int speed;
    protected int damage;
    protected int reloadSpeed;
    protected int width, height;
    protected BufferedImage image;

    public BaseTank(int x, int y, int health, int speed, int damage, int reloadSpeed, String imagePath) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.reloadSpeed = reloadSpeed;

        loadImage(imagePath);
    }

    private void loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            System.err.println("Failed to load tank image: " + e.getMessage());
            width = height = 40; // fallback size
        }
    }

    public void update() {
        x += speed;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
    }

    public int getX() {
        return x;
    }
}
