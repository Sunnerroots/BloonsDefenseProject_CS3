import tank.BaseTank;

import java.util.List;

public class Wave {
    public int tankCount;
    public int spawnInterval; // in ms
    public int tanksSpawned = 0;
    public long lastSpawnTime = System.currentTimeMillis();

    public Wave(int count, int interval) {
        this.tankCount = count;
        this.spawnInterval = interval;
    }

    public boolean shouldSpawn() {
        return tanksSpawned < tankCount &&
                System.currentTimeMillis() - lastSpawnTime >= spawnInterval;
    }

    public void onSpawn() {
        tanksSpawned++;
        lastSpawnTime = System.currentTimeMillis();
    }

    public boolean isComplete(List<BaseTank> tanks) {
        return tanksSpawned >= tankCount && tanks.isEmpty();
    }
}
