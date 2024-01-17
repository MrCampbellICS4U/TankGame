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
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener {

    final int S = 0; // sand
    final int H = 1; // hole
    final int W = 2; // wall
    final int C = 3; // cracked wall
    final int P = 4; // player
    final int E1 = 5; // enemy 1

    int screenHeight, screenWidth;

    Background background = new Background();
    Foreground foreground = new Foreground();

    JLayeredPane onion = new JLayeredPane();

    BufferedImage sand = loadImage("Resources\\sand.png");
    BufferedImage hole = loadImage("Resources\\hole.png");
    BufferedImage wall = loadImage("Resources\\wall.png");
    BufferedImage crackedWall = loadImage("Resources\\crackedWall.png");
    BufferedImage tank1 = loadImage("Resources\\tank1.png");

    boolean w, a, s, d;
    boolean repaint = true;

    Timer timer;
    int TIMERSPEED = 1;

    int size;

    int[][] grid = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, P, S, S, S, S, W, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, C, S, S, S, S, H, S, S, W },
            { W, S, S, S, S, S, S, C, S, S, S, H, H, S, S, W },
            { W, S, S, S, S, S, S, H, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };

    ArrayList<Wall> walls = new ArrayList<Wall>();

    Tank player;

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
        add(onion);
        onion.setBounds(0, 0, screenWidth, screenHeight);
        onion.setOpaque(false);
        onion.add(background, 1);
        background.setBounds(0, 0, screenWidth, screenHeight);
        background.setOpaque(false);
        onion.add(foreground, 0);
        foreground.setBounds(0, 0, screenWidth, screenHeight);
        foreground.setOpaque(false);
        size = screenHeight / grid.length;
        if (size > screenWidth / grid[0].length)
            size = screenWidth / grid[0].length;

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

        timer = new Timer(TIMERSPEED, this);
        timer.start();

    }

    private class Background extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            if (repaint) {
                super.paintComponent(g);
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid[0].length; x++) {
                        switch (grid[y][x]) {
                            case S:
                                g.drawImage(sand, x * size, y * size, size, size, null);
                                break;
                            case H:
                                g.drawImage(hole, x * size, y * size, size, size, null);
                                walls.add(new Wall(x * size, y * size, size, size, H));
                                break;
                            case W:
                                g.drawImage(wall, x * size, y * size, size, size, null);
                                walls.add(new Wall(x * size, y * size, size, size, W));
                                break;
                            case C:
                                g.drawImage(crackedWall, x * size, y * size, size, size, null);
                                walls.add(new Wall(x * size, y * size, size, size, C));
                                break;
                            case P:
                                g.drawImage(sand, x * size, y * size, size, size, null);
                                if (player == null)
                                    player = new Tank(x * size, y * size, 1, 5);
                                break;
                            default:
                                System.out.println("ERROR - Map load error");
                        }

                    }
                }
                repaint = true;
            }
        }
    }

    private class Foreground extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            // super.paintComponent(g);
            g.drawImage(tank1, (int) player.x, (int) player.y, size, size, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (w)
            player.y -= size / 10;
        if (a)
            player.x -= size / 10;
        if (s)
            player.y += size / 10;
        if (d)
            player.x += size / 10;
        foreground.repaint();
        background.repaint();
    }
}
