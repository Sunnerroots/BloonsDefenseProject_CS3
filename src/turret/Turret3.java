package turret;

public class Turret3 extends BaseTurret {
    public static final int COST = 125;

    public Turret3(int x, int y) {
        super(x, y, 350, 600, 50, "src/images/turrets/p3Final.png");
    }

    public static String getStats() {
        BaseTurret dummy = new Turret3(0, 0);
        return String.format("MMB: Turret3 ($%d) |  Range %d  Dmg %d  Rld %.2fs",
                COST, dummy.getRange(), dummy.getDamage(), dummy.getReloadTime() / 1000.0);
    }
}
