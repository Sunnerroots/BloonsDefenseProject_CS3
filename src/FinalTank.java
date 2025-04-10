import java.awt.*;

public class FinalTank
{
    private int x, y;
    private int speed = 2;
    private int width = 40, height = 40;
    
    public FinalTank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        x += speed; // Moves enemy to the right
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
