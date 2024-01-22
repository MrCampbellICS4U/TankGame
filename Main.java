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

public class Main extends JFrame implements ActionListener {

    final int S = 0; // sand
    final int H = 1; // hole
    final int W = 2; // wall
    final int C = 3; // cracked wall
    final int P = 4; // player
    final int E1 = 5; // enemy 1

    int screenHeight, screenWidth;

    DrawingPanel drawing = new DrawingPanel();

    Image sand;
    Image hole;
    Image wall;
    Image crackedWall;
    Image tank1;;
    Image bomb;
    Image bombred;
    Image explosion1;
    Image explosion2;
    Image explosion3;
    Image explosion4;
    Image explosion5;
    Image explosion6;
    Image explosion7;
    boolean w, a, s, d;
    
    Timer timer;
    int TIMERSPEED = 1;
    int bombCount = 0;
    int maxBombs = 2;
    int size;
    double xb,xy;

    int[][] grid = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, P, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, C, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, W, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, H, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, W, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };

    ArrayList<Wall> walls = new ArrayList<Wall>();

    Tank player;

    double slope;

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

        sand = loadImage("Resources\\sand.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        hole = loadImage("Resources\\hole.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        wall = loadImage("Resources\\wall.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        crackedWall = loadImage("Resources\\crackedWall.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        tank1 = loadImage("Resources\\tank1.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        bomb = loadImage("Resources\\bomb.png").getScaledInstance(size/2, size/2, Image.SCALE_DEFAULT);
        bombred = loadImage("Resources\\bombred.png").getScaledInstance(size/2, size/2, Image.SCALE_DEFAULT);
        explosion1 = loadImage("Resources\\explosion1adj.png").getScaledInstance(3*size, 3*size, Image.SCALE_DEFAULT);
        explosion2 = loadImage("Resources\\explosion2adj.png").getScaledInstance(3*size, 3*size, Image.SCALE_DEFAULT);
        explosion3 = loadImage("Resources\\explosion3adj.png").getScaledInstance(3*size, 3*size, Image.SCALE_DEFAULT);
        explosion4 = loadImage("Resources\\explosion4adj.png").getScaledInstance(3*size, 3*size, Image.SCALE_DEFAULT);
        explosion5 = loadImage("Resources\\explosion5adj.png").getScaledInstance(3*size, 3*size, Image.SCALE_DEFAULT);
        explosion6 = loadImage("Resources\\explosion6adj.png").getScaledInstance(3*size, 3*size, Image.SCALE_DEFAULT);
        explosion7 = loadImage("Resources\\explosion7adj.png").getScaledInstance(3*size, 3*size, Image.SCALE_DEFAULT);
        

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
                    slope = e.getX() - player.x / e.getY() - player.y;
                    player.shoot(e.getX() - player.x, e.getY() - player.y);
                }
                if (e.getButton() == MouseEvent.BUTTON3){ // bomb();
                    xb = player.x;
                    xy = player.y;
           
                    if(bombCount <= maxBombs){
                        player.bombs.add (new Bomb(player.x, player.y));
                        bombCount ++;
                }
            }

            }
        });

        timer = new Timer(TIMERSPEED, this);
        timer.start();

    }

    private class DrawingPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.black);
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
                        default:
                            System.out.println("ERROR - Map load error");
                    }

                }
            }
            for (Bullet b : player.bullets) {
               
                g.fillRect((int) b.x, (int) b.y, b.width, b.height);
            }
            g.drawImage(tank1, (int) player.x, (int) player.y, null);

            
            for(Bomb b: player.bombs){
                if (b.bombTick <= 300 )g.drawImage(bomb,(int) b.x, (int) b.y, null);
                if (b.bombTick >= 300 ){
                    if (b.bombTick >= 300 && b.bombTick <= 325)g.drawImage(bombred,(int) b.x, (int) b.y, null);
                    if (b.bombTick >= 325 && b.bombTick <= 350)g.drawImage(bomb,(int) b.x, (int) b.y, null);
                    if (b.bombTick >= 350 && b.bombTick <= 375)g.drawImage(bombred,(int) b.x, (int) b.y, null);
                    if (b.bombTick >= 375 && b.bombTick <= 400)g.drawImage(bomb,(int) b.x, (int) b.y, null);
                    if (b.bombTick >= 400 && b.bombTick <= 425)g.drawImage(bombred,(int) b.x, (int) b.y, null);
                    if (b.bombTick >= 425 && b.bombTick <= 450)g.drawImage(bomb,(int) b.x, (int) b.y, null);
                    if (b.bombTick >= 450 && b.bombTick <= 475)g.drawImage(bombred,(int) b.x, (int) b.y, null);
                    if (b.bombTick >= 475 && b.bombTick <= 500)g.drawImage(bomb,(int) b.x, (int) b.y, null); 
                    if (b.bombTick == 501) bombCount--;
                    if (b.bombTick >= 501 && b.bombTick <= 505) g.drawImage(explosion1,(int) b.x-size*3/2, (int) b.y-size*3/2, null); 
                    if (b.bombTick >= 505 && b.bombTick <= 510) g.drawImage(explosion2,(int) b.x-size*3/2, (int) b.y-size*3/2, null); 
                    if (b.bombTick >= 510 && b.bombTick <= 515) g.drawImage(explosion3,(int) b.x-size*3/2, (int) b.y-size*3/2, null); 
                    if (b.bombTick >= 515 && b.bombTick <= 520) g.drawImage(explosion4,(int) b.x-size*3/2, (int) b.y-size*3/2, null); 
                    if (b.bombTick >= 520 && b.bombTick <= 525) g.drawImage(explosion5,(int) b.x-size*3/2, (int) b.y-size*3/2, null); 
                    if (b.bombTick >= 525 && b.bombTick <= 530) g.drawImage(explosion6,(int) b.x-size*3/2, (int) b.y-size*3/2, null); 
                    if (b.bombTick >= 535 && b.bombTick <= 540) g.drawImage(explosion7,(int) b.x-size*3/2, (int) b.y-size*3/2, null); 
                }
             
            }
           
           
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Bullet b : player.bullets) {
            b.move();
        }
        for (Bomb b : player.bombs) {
            b.bombTick ++;
            if ( player.x- b.x == size*3/4 && player.x- b.x == -size*3/4 &&  player.y- b.y == size*3/4){
                System.out.println("dead");
            }
        }
        
               
            
        if (w) {
            System.out.println("w");
            if (a)
                player.move(size / 30 * -1, size / 30 * -1);
            else if (d)
                player.move(size / 30, size / 30 * -1);
            else
                player.move(0, size / 15 * -1);
        } else if (s) {
            if (a)
                player.move(size / 30 * -1, size / 30);
            else if (d)
                player.move(size / 30, size / 30);
            else
                player.move(0, size / 15);
        } else if (a)
            player.move(size / 15 * -1, 0);
        else if (d)
            player.move(size / 15, 0);
        repaint();
    }
}
