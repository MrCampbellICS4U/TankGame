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

    boolean w, a, s, d;

    Timer timer;
    int TIMERSPEED = 1;

    int size;

    int[][] grid = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, P, S, S, S, S, S, S, S, S, S, S, E, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };

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
                    player.shoot((e.getX() - player.x) / length * 5, (e.getY() - player.y) / length * 5);
                }
                if (e.getButton() == MouseEvent.BUTTON3)
                    System.out.println(); // bomb();
            }
        });

        timer = new Timer(TIMERSPEED, this);
        timer.start();

    }

    private class DrawingPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            super.paintComponent(g);
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
                                player = new Tank(x * size, y * size, size, size, 1, 5);
                            break;
                        case E:
                            g.drawImage(sand, x * size, y * size, null);
                            tanks.add(new Tank(x * size, y * size, size, size, 1, 3));
                            break;
                        default:
                            System.out.println("ERROR - Map load error");
                    }

                }
            }

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
        for (Wall w : walls) {
            if (w.contains(player.x, player.y)) {

            }
        }
        repaint();
    }
}