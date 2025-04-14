import java.util.List;

public class Level {
    private int levelNumber;
    private String backgroundImagePath;
    private int startingGold;
    private List<Wave> waves;

    public Level(int number, String background, int gold, List<Wave> waves) {
        this.levelNumber = number;
        this.backgroundImagePath = background;
        this.startingGold = gold;
        this.waves = waves;
    }

    public List<Wave> getWaves() { return waves; }
    public int getLevelNumber() { return levelNumber; }
    public String getBackgroundImagePath() { return backgroundImagePath; }
    public int getStartingGold() { return startingGold; }
}
