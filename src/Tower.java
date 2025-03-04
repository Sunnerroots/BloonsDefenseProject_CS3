import java.awt.*;
// Tower class for the tower postion
public class Tower {
    private int x, y;
    private int range = 100;
    
    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x - 20, y - 20, 40, 40);
    }
}
