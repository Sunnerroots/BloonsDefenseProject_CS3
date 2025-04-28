package tank;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BaseTank {
    protected int x, y;
    protected int health;
    protected int speed;
    protected int damage;
    protected int reloadSpeed;
    protected int width, height;
    protected BufferedImage image;
    protected int currentWaypoint = 0;
    protected ArrayList<Point> path;


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
        if (path == null || currentWaypoint >= path.size()) {
            x += speed; // fallback if no path
            return;
        }

        Point target = path.get(currentWaypoint);

        int dx = target.x - x;
        int dy = target.y - y;
        double distance = Math.sqrt(dx*dx + dy*dy);

        if (distance < speed) {
            // Close enough to move to next waypoint
            x = target.x;
            y = target.y;
            currentWaypoint++;
        } else {
            // Move towards waypoint
            x += (int)(speed * dx / distance);
            y += (int)(speed * dy / distance);
        }
    }


    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        if (path != null && currentWaypoint < path.size()) {
            Point target = path.get(currentWaypoint);
            //Finds angle using the horizontal and vertical displacement
            double angle = Math.atan2(target.y - y, target.x - x);


            //rotates when a small translation is found indicating a turn
            g2d.translate(x + width/2, y + height/2);
            g2d.rotate(angle);

            if (image != null) {
                g2d.drawImage(image, -width/2, -height/2, null);
            } else {
                g2d.setColor(Color.RED);
                g2d.fillRect(-width/2, -height/2, width, height);
            }
        }
        g2d.dispose();
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

    public int getY() {
        return y;
    }

    public int getCurrentWaypoint()
    {
        return currentWaypoint;
    }

    // --- Static Spawner Methods ---

    public static BaseTank getRandomTank() {
        Random rand = new Random();
        int choice = rand.nextInt(3); // 0, 1, or 2
        switch (choice) {
            case 0:
                return new Tank1(0, 300); // adjust y as needed
            case 1:
                return new Tank2(0, 300);
            case 2:
            default:
                return new Tank3(0, 300);
        }
    }

    public static BaseTank getTankForWave(int waveNumber) {
        Random rand = new Random();
        int roll;

        if (waveNumber <= 2) {
            // Early waves: 70% Tank1, 30% Tank2
            roll = rand.nextInt(100);
            if (roll < 70) return new Tank1(0, 300);
            else return new Tank2(0, 300);
        } else {
            // Later waves: 60% Tank2, 40% Tank3
            roll = rand.nextInt(100);
            if (roll < 60) return new Tank2(0, 300);
            else return new Tank3(0, 300);
        }
    }

    public void setPath(ArrayList<Point> path) {
        this.path = path;
    }

}
