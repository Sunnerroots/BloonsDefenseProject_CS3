import java.awt.*;
// Player class to manage their curency
public class Player {
    private int health = 100;
    private int gold = 500;
    private int reloadTime;

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Health: " + health, 10, 20);
        g.drawString("Gold: " + gold, 10, 40);
    }
}
