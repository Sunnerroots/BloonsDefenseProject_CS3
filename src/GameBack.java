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

        pathPoints = new ArrayList<>();

        pathPoints.add(new Point(0, 300));    // start
        pathPoints.add(new Point(200, 300));
        pathPoints.add(new Point(200, 500));
        pathPoints.add(new Point(600, 500));
        pathPoints.add(new Point(600, 100));
        pathPoints.add(new Point(800, 100));  // exit at right side

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
        window.setColor(Color.MAGENTA);
        for (Point p : pathPoints) {
            window.fillOval(p.x - 5, p.y - 5, 10, 10);
        }


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
            window.setColor(Color.RED);
            window.drawString("GAME OVER", getWidth()/2 - 150, getHeight()/2);
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

    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gameLogic();
            repaint();
        }
    }

    private void gameLogic() {
        // Mouse click to place turret
        if (mouseClicked) {
            if (mouseButton == MouseEvent.BUTTON1 && player.getGold() >= 50) {
                turrets.add(new Turret1(mouseX, mouseY));
                player.setGold(-50);
            }
            else if (mouseButton == MouseEvent.BUTTON3 && player.getGold() >= 75) {
                turrets.add(new Turret2(mouseX, mouseY));
                player.setGold(-75);
            }
            else if (mouseButton == MouseEvent.BUTTON2 && player.getGold() >= 100) {
                turrets.add(new Turret3(mouseX, mouseY));
                player.setGold(-100);
            }
            mouseClicked = false;
        }

        // Turrets attack tanks
        for (BaseTurret turret : turrets) {
            for (BaseTank tank : tanks) {
                double distance = Math.hypot(turret.getX() - tank.getX(), turret.getY() - tank.getY());
                if (distance <= turret.getRange()) {
                    tank.takeDamage(turret.getDamage());
                    break; // Attack one tank at a time
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
            }
        }

        // Hide level start message after duration
        if (showLevelStartMessage) {
            if (System.currentTimeMillis() - levelStartMessageTime > LEVEL_MESSAGE_DURATION) {
                showLevelStartMessage = false;
            }
        }


    }

    private BaseTank getTankForCurrentLevel(int level) {
        Random rand = new Random();
        if (level == 1) {
            return new Tank1(0, 300);
        } else if (level == 2) {
            if (rand.nextBoolean()) return new Tank1(0, 300);
            else return new Tank2(0, 300);
        } else {
            int roll = rand.nextInt(3);
            if (roll == 0) return new Tank1(0, 300);
            else if (roll == 1) return new Tank2(0, 300);
            else return new Tank3(0, 300);
        }
    }


    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
        mouseX = e.getX();
        mouseY = e.getY();
        mouseButton = e.getButton();
        if (startWaveButtonRect.contains(e.getPoint()) && !waveInProgress) {
            if (currentWaveNumber > wavesPerLevel) {
                currentLevelNumber++;
                if (currentLevelNumber == 2) {
                    levelStartMessage = "Level 2 Starting!";
                }
                else if (currentLevelNumber == 3) {
                    levelStartMessage = "Final Level!";
                }
                showLevelStartMessage = true;
                levelStartMessageTime = System.currentTimeMillis();

                currentWaveNumber = 1;

                // Check for level advancement and update path points
                if (currentLevelNumber == 2 || currentLevelNumber == 3) {
                    pathPoints.clear();
                    // new hardlevel path
                    pathPoints.add(new Point(0, 400));
                    pathPoints.add(new Point(200, 400));
                    pathPoints.add(new Point(200, 600));
                    pathPoints.add(new Point(600, 600));
                    pathPoints.add(new Point(600, 200));
                    pathPoints.add(new Point(800, 200));
                }
            }

            currentWave = new Wave(10, 1000); // Start a NEW WAVE
            waveInProgress = true;
        }

    }


    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
