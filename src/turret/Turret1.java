package turret;

public class Turret1 extends BaseTurret {
    public static final int COST = 75;

    public Turret1(int x, int y) {
        super(x, y, 200, 1000, 15, "src/images/turrets/p1Final.png");
    }

    public static String getStats() {
        BaseTurret dummy = new Turret1(0, 0);
        return String.format("LMB: Turret1 ($%d)  |  Range %d  Dmg %d  Rld %.2fs",
                COST, dummy.getRange(), dummy.getDamage(), dummy.getReloadTime() / 1000.0);
    }
}
