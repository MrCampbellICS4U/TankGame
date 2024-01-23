//Create frame/panel/drawing panel
//collect input
//draw everything

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.Math;

public class Main extends JFrame implements ActionListener {

    final int S = 0; // sand
    final int H = 1; // hole
    final int W = 2; // wall
    final int C = 3; // cracked wall
    final int P = 4; // player
    final int E = 5; // enemy

    int screenHeight, screenWidth;

    DrawingPanel drawing = new DrawingPanel();

    Image sand;
    Image hole;
    Image wall;
    Image crackedWall;
    Image tank;
    Image enemyTank;
    static Image bomb;
    public Image bombred;
    public Image explosion1;
    public Image explosion2;
    public Image explosion3;
    public Image explosion4;
    public Image explosion5;
    public Image explosion6;
    public Image explosion7;

    BufferedImage background;

    boolean w, a, s, d;

    Timer timer;
    int TIMERSPEED = 1;

    int size;

    int[][] grid = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, P, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, E, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, W, S, S, H, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, C, S, S, H, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, W, S, S, H, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };

    ArrayList<Wall> walls = new ArrayList<Wall>();

    ArrayList<Tank> tanks = new ArrayList<Tank>();

    Tank player;

    double length;
    double dx, dy, speed, speed2;

    static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(filename + " failed to load");
        }
        return img;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    Main() {
        setTitle("Tank Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        screenHeight = getHeight();
        screenWidth = getWidth();
        add(drawing);
        drawing.setBounds(0, 0, screenWidth, screenHeight);
        size = screenHeight / grid.length;
        if (size > screenWidth / grid[0].length)
            size = screenWidth / grid[0].length;
        speed = size / 20;
        speed2 = speed / Math.sqrt(speed * speed + speed * speed) * speed;

        sand = loadImage("Resources\\sand.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        hole = loadImage("Resources\\hole.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        wall = loadImage("Resources\\wall.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        crackedWall = loadImage("Resources\\crackedWall.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        tank = loadImage("Resources\\tank.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        enemyTank = loadImage("Resources\\enemyTank.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        bomb = loadImage("Resources\\bomb.png").getScaledInstance(size / 2, size / 2, Image.SCALE_DEFAULT);
        bombred = loadImage("Resources\\bomb2.png").getScaledInstance(size / 2, size / 2, Image.SCALE_DEFAULT);
        explosion1 = loadImage("Resources\\explosion1.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion2 = loadImage("Resources\\explosion2.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion3 = loadImage("Resources\\explosion3.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion4 = loadImage("Resources\\explosion4.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion5 = loadImage("Resources\\explosion5.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion6 = loadImage("Resources\\explosion6.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion7 = loadImage("Resources\\explosion7.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);

        updateBackground();

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyChar();
                if (keyCode == 'w')
                    w = true;
                if (keyCode == 'a')
                    a = true;
                if (keyCode == 's')
                    s = true;
                if (keyCode == 'd')
                    d = true;
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    System.exit(0); // PAUSE MENU
            }

            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyChar();
                if (keyCode == 'w')
                    w = false;
                if (keyCode == 'a')
                    a = false;
                if (keyCode == 's')
                    s = false;
                if (keyCode == 'd')
                    d = false;
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    length = Math.sqrt((e.getX() - player.x) * (e.getX() - player.x)
                            + (e.getY() - player.y) * (e.getY() - player.y));
                    player.shoot((e.getX() - player.x) / length * 15, (e.getY() - player.y) / length * 15);
                }
                if (e.getButton() == MouseEvent.BUTTON3)
                    player.bomb();
            }
        });

        timer = new Timer(TIMERSPEED, this);
        timer.start();

    }

    public void updateBackground() {
        background = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = background.getGraphics();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                switch (grid[y][x]) {
                    case S:
                        g.drawImage(sand, x * size, y * size, null);
                        break;
                    case H:
                        g.drawImage(hole, x * size, y * size, null);
                        walls.add(new Wall(x * size, y * size, size, size, H));
                        break;
                    case W:
                        g.drawImage(wall, x * size, y * size, null);
                        walls.add(new Wall(x * size, y * size, size, size, W));
                        break;
                    case C:
                        g.drawImage(crackedWall, x * size, y * size, null);
                        walls.add(new Wall(x * size, y * size, size, size, C));
                        break;
                    case P:
                        g.drawImage(sand, x * size, y * size, null);
                        if (player == null)
                            player = new Tank(x * size, y * size, size, size, 1, 5, 3);
                        break;
                    case E:
                        g.drawImage(sand, x * size, y * size, null);
                        tanks.add(new Tank(x * size, y * size, size, size, 1, 3, 0));
                        break;
                    default:
                        System.out.println("ERROR - Map load error");
                }

            }
        }
        g.dispose();
    }

    private class DrawingPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            super.paintComponent(g);
            g.drawImage(background, 0, 0, null);
            for (Tank t : tanks) {
                g.drawImage(enemyTank, (int) t.x, (int) t.y, null);
                for (Bullet b : t.bullets) {
                    g.fillRect((int) b.x, (int) b.y, b.width, b.height);
                }
            }
            for (Bullet b : player.bullets) {
                g.fillRect((int) b.x, (int) b.y, b.width, b.height);
            }
            g.drawImage(tank, (int) player.x, (int) player.y, null);

            for (Bomb b : player.bombs) {
                b.bombTick++;
                if (b.bombTick < 500)
                    g.drawImage(bomb, (int) b.x - size / 4, (int) b.y - size / 4, null);
                else if (b.bombTick < 700) {
                    if (b.bombTick / 25 % 2 == 0)
                        g.drawImage(bombred, (int) b.x - size / 4, (int) b.y - size / 4, null);
                    else
                        g.drawImage(bomb, (int) b.x - size / 4, (int) b.y - size / 4, null);
                } else {
                    if (b.bombTick <= 705)
                        g.drawImage(explosion1, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                    else if (b.bombTick <= 710)
                        g.drawImage(explosion2, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                    else if (b.bombTick <= 715)
                        g.drawImage(explosion3, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                    else if (b.bombTick <= 720)
                        g.drawImage(explosion4, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                    else if (b.bombTick <= 725)
                        g.drawImage(explosion5, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                    else if (b.bombTick <= 730)
                        g.drawImage(explosion6, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                    else if (b.bombTick <= 735)
                        g.drawImage(explosion7, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                    else
                        b.tank.bombs.remove(b);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Bullet b : player.bullets) {
            b.move();
        }
        dx = 0;
        dy = 0;
        if (w) {
            if (a) {
                dx = -speed2;
                dy = -speed2;
            } else if (d) {
                dx = speed2;
                dy = -speed2;
            } else
                dy = -speed;
        } else if (s) {
            if (a) {
                dx = -speed2;
                dy = speed2;
            } else if (d) {
                dx = speed2;
                dy = speed2;
            } else
                dy = speed;
        } else if (a)
            dx = -speed;
        else if (d)
            dx = speed;
        player.move(dx, dy);

        // wall collisions
        for (Wall w : walls) {
            if (w.intersects(player)) {
                if (w.contains(player.x + speed + 1, player.y) || w.contains(player.x + size - speed - 1, player.y)) {
                    player.y = w.y + size;
                } else if (w.contains(player.x, player.y + speed + 1)
                        || w.contains(player.x, player.y + size - speed - 1)) {
                    player.x = w.x + size;
                } else if (w.contains(player.x + speed + 1, player.y + size)
                        || w.contains(player.x + size - speed - 1, player.y + size)) {
                    player.y = w.y - size;
                } else if (w.contains(player.x + size, player.y + size - speed - 1)
                        || w.contains(player.x + size, player.y + speed + 1)) {
                    player.x = w.x - size;
                } else if (w.contains(player.x, player.y)) {
                    player.x = w.x + size;
                    player.y = w.y + size;
                } else if (w.contains(player.x + size, player.y)) {
                    player.x = w.x - size;
                    player.y = w.y + size;
                } else if (w.contains(player.x, player.y + size)) {
                    player.x = w.x + size;
                    player.y = w.y - size;
                } else if (w.contains(player.x + size, player.y + size)) {
                    player.x = w.x - size;
                    player.y = w.y - size;
                }
            }
            repaint();
        }
    }
}