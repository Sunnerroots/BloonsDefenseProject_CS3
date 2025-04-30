package turret;

public class Turret2 extends BaseTurret {
    public static final int COST = 100;

    public Turret2(int x, int y) {
        super(x, y, 300, 800, 30, "src/images/turrets/p2Final.png");
    }

    public static String getStats() {
        BaseTurret dummy = new Turret2(0, 0);
        return String.format("RMB: Turret2 ($%d)  |  Range %d  Dmg %d  Rld %.2fs",
                COST, dummy.getRange(), dummy.getDamage(), dummy.getReloadTime() / 1000.0);
    }
}
