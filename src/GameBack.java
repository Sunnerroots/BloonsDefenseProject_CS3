import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import java.util.*;
import tank.*;
import turret.*;

public class GameBack extends Canvas implements MouseListener {
    private int mouseX, mouseY, mouseButton;
    private boolean mouseClicked;
    private BufferedImage backgroundImage;
    private BufferedImage hardLevelBackground;
    private static Player player;
    private ArrayList<Level> lvl;
    private ArrayList<BaseTurret> turrets;
    private ArrayList<BaseTank> tanks;
    private Timer clock;
    private Wave currentWave;
    private int waveTimer;
    private final int WAVE_DELAY = 5000; // 5 seconds delay between waves
    private int currentWaveNumber;
    private int wavesPerLevel = 5;
    private int currentLevelNumber;
    private boolean isGameOver;
    private ArrayList<Point> pathPoints;
    private Rectangle startWaveButtonRect;
    private boolean waveInProgress;
    private boolean showLevelStartMessage;
    private String levelStartMessage;
    private long levelStartMessageTime;
    private final int LEVEL_MESSAGE_DURATION = 2000; // Show for 2 seconds (2000 ms)
    private boolean hasWon;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private boolean showPlacementError = false;
    private String placementErrorMessage = "";
    private long placementErrorStartTime;
    private final int PLACEMENT_ERROR_DURATION = 5000;



    public GameBack()
    {
        mouseClicked = false;
        mouseX = mouseY = 0;
        player = new Player();
        lvl = new ArrayList<>();
        turrets = new ArrayList<>();
        tanks = new ArrayList<>();
        clock = new Timer(20, new TimerListener());
        clock.start();
        waveTimer = 0;



        try {
            backgroundImage = ImageIO.read(new File("src/images/bgs/level1.png"));
            hardLevelBackground = ImageIO.read(new File("src/images/bgs/HARDLEVEL.png"));
        } catch (IOException e) {
            System.err.println("Background image load error: " + e.getMessage());
        }

        for (int i = 0; i < 3; i++) {
            if(i == 0)
                lvl.add(new Level(i + 1, "src/images/bgs/level1.png", 500, new ArrayList<>()));
        }

        addMouseListener(this);
        setBackground(Color.WHITE);

        startWaveButtonRect = new Rectangle(50, 700, 120, 40); // X, Y, Width, Height
        waveInProgress = false;


        currentWaveNumber = 0;
        currentLevelNumber = 1;
        isGameOver = false;


        //1st Map
        pathPoints = new ArrayList<>();
        pathPoints.add(new Point(75, 0));
        pathPoints.add(new Point(75, 315));
        pathPoints.add(new Point(300, 315));
        pathPoints.add(new Point(515, 315));
        pathPoints.add(new Point(515,115));
        pathPoints.add(new Point(315, 115));
        pathPoints.add(new Point(315, 800));

    }

    public void paint(Graphics window) {
        // Draw background
        if (currentLevelNumber == 1) {
            if (backgroundImage != null) {
                window.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        }
        else if (hardLevelBackground != null)
        {
                window.drawImage(hardLevelBackground, 0, 0, getWidth(), getHeight(), null);
        }

        else {
            window.setColor(Color.white);
            window.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw waypoints for testing
        /*
        window.setColor(Color.MAGENTA);
        for (Point p : pathPoints) {
            window.fillOval(p.x - 5, p.y - 5, 10, 10);
        }

         */


        // Draw all turrets
        for (BaseTurret t : turrets) {
            t.draw(window);
        }

        // Draw all tanks
        for (BaseTank tank : tanks) {
            tank.draw(window);
            // Draw tank health bar
            window.setColor(Color.RED);
            window.fillRect(tank.getX(), tank.getY() - 10, 40, 5);
            window.setColor(Color.GREEN);
            int hpWidth = Math.max(0, (int) (40 * (tank.getHealth() / 150.0)));
            window.fillRect(tank.getX(), tank.getY() - 10, hpWidth, 5);
        }

        for (Projectile p : projectiles) {
            p.draw(window);
        }

        // Draw HUD
        Font boldFont = new Font("TERMINAL", Font.BOLD, 16);
        window.setFont(boldFont);
        int bottomY = getHeight() - 20;
        String goldText = "Gold: " + player.getGold();
        // Draw Player HP
        String hpText = "HP: " + player.getHP();
        int hpWidth = window.getFontMetrics().stringWidth(hpText);
        int hpX = getWidth() - hpWidth - 150;
        window.setColor(Color.BLACK);
        window.drawString(hpText, hpX + 1, getHeight() - 60 + 1); // Shadow
        window.setColor(Color.RED);
        window.drawString(hpText, hpX, getHeight() - 60);

        // Draw Current Wave Number
        if (!isGameOver) {
            String waveText = "Wave: " + currentWaveNumber + " / 5";
            window.setColor(Color.BLACK);
            window.drawString(waveText, 50 + 1, getHeight() - 150 + 1); // slight shadow
            window.setColor(Color.WHITE);
            window.drawString(waveText, 50, getHeight() - 150);
        }


        int goldWidth = window.getFontMetrics().stringWidth(goldText);
        int goldX = getWidth() - goldWidth - 150;
        window.setColor(Color.BLACK);
        window.drawString(goldText, goldX + 1, bottomY - 20 + 1);
        window.setColor(Color.YELLOW);
        window.drawString(goldText, goldX, bottomY - 20);

        String title = "Tower Defense CS3";
        int titleWidth = window.getFontMetrics().stringWidth(title);
        int titleX = getWidth() - titleWidth - 150;
        window.setColor(Color.BLACK);
        window.drawString(title, titleX + 1, bottomY + 1);
        window.setColor(Color.BLUE);
        window.drawString(title, titleX, bottomY);

        if (isGameOver) {
            Font bigFont = new Font("Arial", Font.BOLD, 48);
            window.setFont(bigFont);
            if (hasWon) {
                window.setColor(Color.GREEN);
                window.drawString("YOU WIN!", getWidth()/2 - 150, getHeight()/2);
            } else {
                window.setColor(Color.RED);
                window.drawString("GAME OVER", getWidth()/2 - 150, getHeight()/2);
            }
        }


        // Draw Start Wave Button
        if (!isGameOver) {
            window.setColor(waveInProgress ? Color.GRAY : Color.GREEN);
            window.fillRect(startWaveButtonRect.x, startWaveButtonRect.y, startWaveButtonRect.width, startWaveButtonRect.height);
            window.setColor(Color.BLACK);
            window.drawString("Start Wave", startWaveButtonRect.x + 10, startWaveButtonRect.y + 25);
        }

        // Draw Level Start Message
        if (showLevelStartMessage) {
            Font bigFont = new Font("Arial", Font.BOLD, 48);
            window.setFont(bigFont);
            window.setColor(Color.ORANGE);
            int messageWidth = window.getFontMetrics().stringWidth(levelStartMessage);
            window.drawString(levelStartMessage, (getWidth() - messageWidth) / 2, getHeight() / 2 - 100);
        }

        if (showPlacementError) {
            window.setColor(Color.BLACK);
            window.setFont(new Font("Arial", Font.BOLD, 20));
            int msgWidth = window.getFontMetrics().stringWidth(placementErrorMessage);
            window.drawString(placementErrorMessage, (getWidth() - msgWidth) / 2, getHeight() - 80);
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gameLogic();
            repaint();
        }
    }

    private void gameLogic() {
        // Mouse click to place turret
        // Check to place turret on the grass
        if (mouseClicked) {
            boolean isOnGrass = isGrassTile(mouseX, mouseY);
            boolean isOnStartButton = startWaveButtonRect.contains(mouseX, mouseY);

            if (isOnGrass && !isOnStartButton) {
                if (mouseButton == MouseEvent.BUTTON1 && player.getGold() >= 50) {
                    turrets.add(new Turret1(mouseX, mouseY));
                    player.setGold(-50);
                } else if (mouseButton == MouseEvent.BUTTON3 && player.getGold() >= 75) {
                    turrets.add(new Turret2(mouseX, mouseY));
                    player.setGold(-75);
                } else if (mouseButton == MouseEvent.BUTTON2 && player.getGold() >= 100) {
                    turrets.add(new Turret3(mouseX, mouseY));
                    player.setGold(-100);
                }
            } else {
                placementErrorMessage = "Can't place turret on road!";
                showPlacementError = true;
                placementErrorStartTime = System.currentTimeMillis();
            }

            mouseClicked = false;
        }

        // Turrets attack tanks
        for (BaseTurret turret : turrets) {
            BaseTank closestTank = null;
            double closestDistance = Double.MAX_VALUE;

            for (BaseTank tank : tanks) {
                double dist = Math.hypot(tank.getX() - turret.getX(), tank.getY() - turret.getY());
                if (dist <= turret.getRange() && dist < closestDistance) {
                    closestTank = tank;
                    closestDistance = dist;
                }
            }

            if (closestTank != null) {
                turret.aimAt(closestTank.getX(), closestTank.getY());

                if (turret.canShoot()) {
                    projectiles.add(new Projectile(turret.getX(), turret.getY(), closestTank.getX(), closestTank.getY(), turret.getDamage()));
                    turret.markShotFired();
                }
            }
        }

        Iterator<Projectile> iter = projectiles.iterator();
        while (iter.hasNext()) {
            Projectile p = iter.next();
            p.update();

            for (BaseTank tank : tanks) {
                if (p.intersects(tank)) {
                    tank.takeDamage(p.getDamage());
                    iter.remove(); // remove projectile on hit
                    break;
                }
            }
        }
        // Update tanks
        Iterator<BaseTank> tankIterator = tanks.iterator();
        while (tankIterator.hasNext()) {
            BaseTank tank = tankIterator.next();
            tank.update();
            if (tank.getCurrentWaypoint() >= pathPoints.size())
            {
                player.loseHP(1);
                tankIterator.remove();
            }

        }

        if (player.getHP() <= 0) {
            isGameOver = true;
        }




        // Remove dead tanks
        tanks.removeIf(t -> t.getHealth() <= 0);

        // Spawn tanks from wave
        if (currentWave != null && !isGameOver) {
            if (currentWave.shouldSpawn()) {
                BaseTank newTank = getTankForCurrentLevel(currentLevelNumber);
                newTank.setPath(pathPoints);
                tanks.add(newTank);
                currentWave.onSpawn();
            }

            //Change waves upon completion
            if (currentWave.allTanksSpawned() && currentWave.isComplete(tanks)) {
                waveInProgress = false;
                currentWaveNumber++;
                currentWave = null;
                player.setGold(100); // Give 100 bonus gold after every wave
            }

        }

        // Hide level start message after duration
        if (showLevelStartMessage) {
            if (System.currentTimeMillis() - levelStartMessageTime > LEVEL_MESSAGE_DURATION) {
                showLevelStartMessage = false;
            }
        }

        if (showPlacementError && System.currentTimeMillis() - placementErrorStartTime > PLACEMENT_ERROR_DURATION) {
            showPlacementError = false;
        }
    }

    private BaseTank getTankForCurrentLevel(int level)
    {
        Random rand = new Random();
        Point start = pathPoints.get(0);
        if (level == 1)
        {
            return new Tank1(start.x, start.y);
        }
        else if (level == 2)
        {
            if (rand.nextBoolean())
                new Tank1(start.x, start.y);
            else
                new Tank2(start.x, start.y);
        }
        else
        {
            int roll = rand.nextInt(3);
            if (roll == 0)
                return new Tank1(start.x, start.y);
            else if (roll == 1)
                return new Tank2(start.x, start.y);
            else
                return new Tank3(start.x, start.y);
        }
        return new Tank3(start.x, start.y);
    }


    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
        mouseX = e.getX();
        mouseY = e.getY();
        mouseButton = e.getButton();
        if (startWaveButtonRect.contains(e.getPoint()) && !waveInProgress)
        {
            if (currentWaveNumber > wavesPerLevel)
            {
                currentLevelNumber++;

                // Reset and increase gold for new level
                if (currentLevelNumber == 2) {
                    player.setGold(1000); // Start Level 2 with 1000 gold
                }
                else if (currentLevelNumber == 3) {
                    player.setGold(1500); // Start Level 3 with 1500 gold
                }

                if (currentLevelNumber == 2 || currentLevelNumber == 3)
                {
                    startWaveButtonRect = new Rectangle(getWidth() - 170, 50, 120, 40); // top right corner
                }


                if (currentLevelNumber > 3)
                {
                    isGameOver = true;
                    hasWon = true;
                    return; // exit mouseClicked so we don't start a new wave
                }

                if (currentLevelNumber == 2) {
                    levelStartMessage = "Level 2 Starting!";
                }
                else if (currentLevelNumber == 3)
                {
                    levelStartMessage = "Final Level!";
                }
                showLevelStartMessage = true;
                levelStartMessageTime = System.currentTimeMillis();

                currentWaveNumber = 1;

                // Check for level advancement and update path points
                if (currentLevelNumber == 2 || currentLevelNumber == 3) {
                    pathPoints.clear();
                    // new hardlevel path
                    pathPoints.add(new Point(550, 0));
                    pathPoints.add(new Point(550, 230));
                    pathPoints.add(new Point(475,230));
                    pathPoints.add(new Point(475,150));
                    pathPoints.add(new Point(165,150));
                    pathPoints.add(new Point(165,400));
                    pathPoints.add(new Point(500,400));
                    pathPoints.add(new Point(500,575));
                    pathPoints.add(new Point(70,575));
                    pathPoints.add(new Point(70,800));
                }
            }

            currentWave = new Wave(10, 1000); // Start a NEW WAVE
            waveInProgress = true;
        }

    }

    private boolean isGrassTile(int x, int y) {
        if (backgroundImage == null) return false;

        // Adjust if your background is scaled (optional)
        int scaledX = (int)((x / (double)getWidth()) * backgroundImage.getWidth());
        int scaledY = (int)((y / (double)getHeight()) * backgroundImage.getHeight());

        if (scaledX < 0 || scaledY < 0 || scaledX >= backgroundImage.getWidth() || scaledY >= backgroundImage.getHeight()) {
            return false;
        }

        int rgb = backgroundImage.getRGB(scaledX, scaledY);
        Color color = new Color(rgb);

        // Basic check: greenish grass, not too much red/blue
        return color.getGreen() > 80 && color.getRed() < 100 && color.getBlue() < 100;
    }


    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
