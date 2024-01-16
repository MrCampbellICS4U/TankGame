//Create frame/panel/drawing panel
//collect input
//draw everything

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {

    final int S = 0;
    final int H = 1;
    final int W = 2;
    final int C = 3;

    int screenHeight, screenWidth;

    DrawingPanel drawing = new DrawingPanel();

    BufferedImage sand = loadImage("Resources\\sand.png");
    BufferedImage wall = loadImage("Resources\\wall.png");
    BufferedImage crackedWall = loadImage("Resources\\crackedWall.png");

    int gridHeight = 10;
    int gridWidth = 16;

    int size;

    int[][] grid = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, S, S, S, S, C, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, C, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };

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
        size = screenHeight / gridHeight;
        if (size > screenWidth / gridWidth)
            size = screenWidth / gridWidth;
    }

    private class DrawingPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int y = 0; y < gridHeight; y++) {
                for (int x = 0; x < gridWidth; x++) {
                    switch (grid[y][x]) {
                        case S:
                            g.drawImage(sand, x * size, y * size, size, size, null);
                            break;
                        case H:
                            g.drawImage(sand, x * size, y * size, size, size, null);
                            break;
                        case W:
                            g.drawImage(wall, x * size, y * size, size, size, null);
                            break;
                        case C:
                            g.drawImage(crackedWall, x * size, y * size, size, size, null);
                            break;
                        default:
                            System.out.println("ERROR - Map load error");
                    }

                }
            }

        }
    }

}
