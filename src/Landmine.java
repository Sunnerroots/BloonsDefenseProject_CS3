import java.awt.*;
import tank.BaseTank;

public class Landmine {
    private int x, y;
    private int radius = 40; // explosion trigger radius
    private boolean exploded = false;
    private final int damage = 1000;
    private static final int cost = 200;
    private static Image image;


    public Landmine(int x, int y) {
        this.x = x;
        this.y = y;
        if (image == null) {
            try {
                image = Toolkit.getDefaultToolkit().getImage("src/images/Mine.png");
            } catch (Exception e) {
                System.err.println("Failed to load mine image: " + e.getMessage());
            }
        }

    }

    public boolean checkCollision(BaseTank tank) {
        double distance = Math.hypot(tank.getX() - x, tank.getY() - y);
        return distance <= radius;
    }

    public void explode(BaseTank tank) {
        tank.takeDamage(damage);
        exploded = true;
    }

    public boolean hasExploded() {
        return exploded;
    }

    public void draw(Graphics g) {
        if (!exploded) {
            if (image != null) {
                g.drawImage(image, x - 16, y - 16, 32, 32, null); // adjust size/center
            }

            else {
                g.setColor(Color.ORANGE);
                g.fillOval(x - 10, y - 10, 20, 20);
                g.setColor(Color.BLACK);
                g.drawOval(x - 10, y - 10, 20, 20);
            }
        }
    }


    public int getX() { return x; }
    public int getY() { return y; }
    public static int getCost() { return cost; }
}
