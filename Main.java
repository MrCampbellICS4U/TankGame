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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;

public class Main extends JFrame implements ActionListener {

    final int S = 0; // sand
    final int H = 1; // hole
    final int W = 2; // wall
    final int C = 3; // cracked wall
    final static int P = 4; // player
    final static int E = 5; // enemy

    int screenHeight, screenWidth;

    DrawingPanel drawing = new DrawingPanel();

    JLabel scoreLabel = new JLabel("0");

    Image sand;
    Image hole;
    Image wall;
    Image crackedWall;
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

    boolean updtTanks;

    Timer timer;
    int TIMERSPEED = 1;
    double bombx,bomby;
    static int size;
    int angle;

    //Game Layout
        //Level 1: 1 Enemy
    int[][] grid = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, C, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, P, S, C, S, S, S, S, S, S, S, H, S, S, S, S, S, E, S, W },
            { W, S, S, S, C, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, W },
            { W, S, S, S, W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, W, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, W },
            { W, S, S, S, W, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, C, W, C, W, C, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };
            //Level 2: 1 Enemy
        int[][] grid2 = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, C, S, S, S, S, S, H, S, S, S, S, S, C, S, S, S, W },
            { W, S, S, S, C, S, S, S, S, S, H, S, S, S, S, S, C, S, S, S, W },
            { W, S, S, S, S, S, S, W, S, S, S, S, S, W, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, W, S, S, S, S, S, W, S, S, S, S, S, S, W },
            { W, S, S, S, P, S, S, W, S, S, S, S, S, W, S, S, E, S, S, S, W },
            { W, S, S, S, S, S, S, W, S, S, S, S, S, W, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, W, S, S, S, S, S, W, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, C, S, S, S, S, S, H, S, S, S, S, S, C, S, S, S, W },
            { W, S, S, S, C, S, S, S, S, S, H, S, S, S, S, S, C, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };
            //Level 3: 2 enemies
        int[][] grid3 = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, E, S, S, W },
            { W, S, S, S, S, W, S, S, S, S, S, W, C, C, W, W, S, S, S, S, W },
            { W, S, S, S, S, W, S, S, S, S, S, S, S, S, S, W, S, S, S, S, W },
            { W, S, S, S, S, C, S, S, S, S, S, S, S, S, S, C, S, S, S, S, W },
            { W, S, S, S, S, C, S, S, S, S, S, S, S, S, S, C, S, S, S, S, W },
            { W, S, S, S, S, W, S, S, S, S, S, S, S, S, S, W, S, S, S, S, W },
            { W, S, S, S, S, W, W, C, C, W, S, S, S, S, S, W, S, S, S, S, W },
            { W, S, P, S, S, S, S, S, S, S, S, S, S, S, S, S, S, E, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };    
            //Level 4: 2 Enemies
            int[][] grid4 = {
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, W, W, W, W, W, W, W, W, W, W, W, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, H, S, S, S, S, S, S, E, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, P, S, S, S, S, S, S, H, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, H, S, S, S, S, S, S, E, S, S, W },
            { W, S, S, S, S, W, W, W, W, W, W, W, W, W, W, W, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
            { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } };
            //Level 5: 3 Enemies
            int[][] grid5 = {
                { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, E, S, S, S, S, W },
                { W, S, P, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, C, C, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, C, S, S, S, S, S, S, S, S, S, S, S, S, S, E, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, C, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, C, C, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, E, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } }; 
                //Level 6: 3 Enemies
            int[][] grid6 = {
                { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
                { W, S, S, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, E, S, S, S, S, S, H, S, S, S, S, S, E, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, H, H, H, H, H, S, S, S, S, H, H, H, H, H, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, P, S, S, S, S, S, H, S, S, S, S, S, E, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, S, S, W },
                { W, S, S, S, S, S, S, S, S, S, H, S, S, S, S, S, S, S, S, S, W },
                { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } }; 
                //Level 7: W
                int[][] grid7 = {
                    { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W },
                    { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                    { W, S, S, S, S, S, W, S, S, S, S, S, S, S, W, S, S, S, S, S, W },
                    { W, S, S, S, S, S, W, S, S, S, W, S, S, S, W, S, S, S, S, S, W },
                    { W, S, S, S, S, S, W, S, S, S, W, S, S, S, W, S, S, S, S, S, W },
                    { W, S, S, S, S, S, S, W, S, W, S, W, S, W, S, S, S, S, S, S, W },
                    { W, S, S, S, S, S, S, W, S, W, S, W, S, W, S, S, S, S, S, S, W },
                    { W, S, S, S, S, S, S, S, W, S, S, S, W, S, S, S, S, S, S, S, W },
                    { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                    { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                    { W, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, S, W },
                    { W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W } }; 

    ArrayList<Wall> walls = new ArrayList<Wall>();

    ArrayList<Tank> tanks = new ArrayList<Tank>();

    Tank player;

    double dx, dy, speed, speed2;
    double edx, edy;

    int score;

    Random random = new Random();


    //Load Images
    static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(filename + " failed to load");
        }
        return img;
    }


    //Rotate Images
    public static BufferedImage rotateImage(BufferedImage imag, int n) { // n rotation in radians

        double rotationRequired = Math.toRadians(n);
        double locationX = imag.getWidth() / 2;
        double locationY = imag.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage newImage = new BufferedImage(imag.getWidth(), imag.getHeight(), BufferedImage.TYPE_INT_ARGB);
        op.filter(imag, newImage);
        return (newImage);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public double normalize(boolean x, double targetX, double targetY, double originX, double originY) {
        double length = Math.sqrt(Math.pow(targetX - originX, 2) + Math.pow(targetY - originY, 2));
        if (x)
            return (targetX - originX) / length;
        else
            return (targetY - originY) / length;
    }
    public int[][] getMap(){
        switch(score){
            case 0: return grid;
            case 1: return grid2;
            case 2: return grid3;
            case 3: return grid3;
            case 4: return grid4;
            case 5: return grid4;
            case 6: return grid5;
            case 7: return grid5;
            case 8: return grid5;
            case 9: return grid6;
            case 10: return grid6;
            case 11: return grid6;
            case 12: return grid7;
            default: return null;
        }
    }

    Main() {
        //Setup
        score = 0;
        setTitle("Tank Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        screenHeight = getHeight();
        screenWidth = getWidth();
        scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 64));
        add(scoreLabel);
        scoreLabel.setBounds(25, 25, 50, 50);
        add(drawing);
        drawing.setBounds(0, 0, screenWidth, screenHeight);
        
        //Set size relative to screen
        size = screenHeight / grid.length;
        if (size > screenWidth / grid[0].length)
            size = screenWidth / grid[0].length;
        speed = size / 20;
        speed2 = speed / Math.sqrt(speed * speed + speed * speed) * speed;

        sand = loadImage("Resources\\sand.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        hole = loadImage("Resources\\hole.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        wall = loadImage("Resources\\wall.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        crackedWall = loadImage("Resources\\crackedWall.png").getScaledInstance(size, size, Image.SCALE_DEFAULT);
        bomb = loadImage("Resources\\bomb.png").getScaledInstance(size / 2, size / 2, Image.SCALE_DEFAULT);
        bombred = loadImage("Resources\\bomb2.png").getScaledInstance(size / 2, size / 2, Image.SCALE_DEFAULT);
        explosion1 = loadImage("Resources\\explosion1.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion2 = loadImage("Resources\\explosion2.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion3 = loadImage("Resources\\explosion3.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion4 = loadImage("Resources\\explosion4.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion5 = loadImage("Resources\\explosion5.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion6 = loadImage("Resources\\explosion6.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);
        explosion7 = loadImage("Resources\\explosion7.png").getScaledInstance(3 * size, 3 * size, Image.SCALE_DEFAULT);

        updateBackground(getMap(), true);

        for (Tank t : tanks) {
            t.rotation = 90;
            t.topRotation = 180;
            t.rotateTank = rotateImage(t.tank, t.rotation).getScaledInstance((int) (size * 1.5),
                    (int) (size * 1.5), Image.SCALE_DEFAULT);
            t.rotateTop = rotateImage(t.top, t.topRotation).getScaledInstance((int) (size * 1.5),
                    (int) (size * 1.5), Image.SCALE_DEFAULT);
        }

        //Key listener
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

        //Mouse listener
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    double length = Math
                            .sqrt((e.getX() - (player.x * 2 + size) / 2) * (e.getX() - (player.x * 2 + size) / 2)
                                    + (e.getY() - (player.y * 2 + size) / 2) * (e.getY() - (player.y * 2 + size) / 2));
                    player.shoot((e.getX() - (player.x * 2 + size) / 2) / length * 10,
                            (e.getY() - (player.y * 2 + size) / 2) / length * 10);
                }
                if (e.getButton() == MouseEvent.BUTTON3)
                    player.bomb();
            }
        });

        timer = new Timer(TIMERSPEED, this);
        timer.start();

    }

    //Draw all background tiles as single image
    public void updateBackground(int[][] map, boolean updateTanks) {
        background = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = background.getGraphics();
        walls = new ArrayList<Wall>();
        tanks = new ArrayList<Tank>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                switch (map[y][x]) {
                    case S:
                        g.drawImage(sand, x * size, y * size, null);
                        break;
                    case H:
                        g.drawImage(hole, x * size, y * size, null);
                        walls.add(new Wall(x * size, y * size, size, size, H, x, y));
                        break;
                    case W:
                        g.drawImage(wall, x * size, y * size, null);
                        walls.add(new Wall(x * size, y * size, size, size, W, x, y));
                        break;
                    case C:
                        g.drawImage(crackedWall, x * size, y * size, null);
                        walls.add(new Wall(x * size, y * size, size, size, C, x, y));
                        break;
                    case P:
                        g.drawImage(sand, x * size, y * size, null);
                        if (player == null)
                            player = new Tank(P, x * size, y * size, size, size, 1, 3, 3, x, y);
                            else if (updateTanks = true){
                                player.x = x * size;
                                player.y = y * size;
                            }
                        tanks.add(0, player);
                        break;
                    case E:
                        g.drawImage(sand, x * size, y * size, null);
                        tanks.add(new Tank(E, x * size, y * size, size, size, 1, 2, 0, x, y));
                        if(updateTanks = true){
                            for(Tank t : tanks){
                                if (t != player){
                                    t.x = x * size;
                                    t.y = y * size;
                                }
                            }
                        }
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
            
            //Bomb animation
            for (Tank t : tanks) {
                for (Bomb b : t.bombs) {
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
                        else if (b.bombTick <= 735) {
                            g.drawImage(explosion7, (int) b.x - size * 3 / 2, (int) b.y - size * 3 / 2, null);
                            //Destroy cracked walls
                            for (int i = 0; i < walls.size(); i++) {
                                Wall w = walls.get(i);
                                if (w.intersects(b.explosion) && w.type == C) {
                                    getMap()[w.gridy][w.gridx] = S;
                                    w.type = S;
                                    updateBackground(getMap(), false);
                                }
                            }
                            
                            //Destroy tanks
                            for(int i = 0; i < tanks.size(); i++) {
                                Tank t2 = tanks.get(i);
                                if(t2 == player){
                                    score = 0;
                                    updateBackground(grid, true);
                                }
                                if (t2.intersects(b.explosion)) {
                                    score++;
                                    switch(score){
                                        case 1: updateBackground(getMap(), true); break;
                                        case 2: updateBackground(getMap(), true); break;
                                        case 3: break;
                                        case 4: updateBackground(getMap(), true); break;
                                        case 5: break;
                                        case 6: updateBackground(getMap(), true); break;
                                        case 7: break;
                                        case 8: break;
                                        case 9: updateBackground(getMap(), true); break;
                                        case 10: break;
                                        case 11: break;
                                        case 12: updateBackground(getMap(), true); break;
                                    }
                                }
                            }
                        }
    
                        else if  (b.bombTick == 736){
                            bombx = 0;
                            bomby = 0;
                        }
                    }
                }
                //Draw tanks
                g.drawImage(t.rotateTank, (int) t.x - size / 4, (int) t.y - size / 4, null);
                g.drawImage(t.rotateTop, (int) t.x - size / 4, (int) t.y - size / 4, null);
               
                //Draw bullets
                for (Bullet b : t.bullets) {
                    g.fillRect((int) b.x - size / 10, (int) b.y - size / 10, b.width, b.height);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreLabel.setText(Integer.toString(score));
        dx = 0;
        dy = 0;
        int oldRotation = player.rotation;

        //Movement Logic
        if (w) {
            if (a) {
                dx = -speed2;
                dy = -speed2;
                player.rotation = 315;
            } else if (d) {
                dx = speed2;
                dy = -speed2;
                player.rotation = 45;
            } else {
                dy = -speed;
                player.rotation = 0;
            }
        } else if (s) {
            if (a) {
                dx = -speed2;
                dy = speed2;
                player.rotation = 225;
            } else if (d) {
                dx = speed2;
                dy = speed2;
                player.rotation = 135;
            } else {
                dy = speed;
                player.rotation = 180;
            }
        } else if (a) {
            dx = -speed;
            player.rotation = 270;
        } else if (d) {
            dx = speed;
            player.rotation = 90;
        }

        //Rotate player Tank
        if (player.rotation != oldRotation)
            player.rotateTank = rotateImage(player.tank, player.rotation).getScaledInstance((int) (size * 1.5),
                    (int) (size * 1.5),
                    Image.SCALE_DEFAULT);
        player.topRotation = (int) Math.toDegrees(Math.atan2(getMousePosition().y - (player.y * 2 + size * 1.5) / 2,
                getMousePosition().x - (player.x * 2 + size * 1.5) / 2)) + 90;
        player.rotateTop = rotateImage(player.top, player.topRotation).getScaledInstance((int) (size * 1.5),
                (int) (size * 1.5),
                Image.SCALE_DEFAULT);
        player.move(dx, dy);

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);

            //Bullet collisions
            for (int ii = 0; ii < t.bullets.size(); ii++) {
                Bullet b = t.bullets.get(ii);
                b.move();
                for (int iii = 0; iii < tanks.size(); iii++) {
                    Tank t2 = tanks.get(iii);
                    if (b.intersects(t2)) {
                        t.bullets.remove(b);
                        tanks.remove(t2);
                        if(t2 == player) {
                            score = 0;
                            updateBackground(grid, false);
                        }
                        else score++;
                        switch(score){
                            case 1: updateBackground(getMap(), true); break;
                            case 2: updateBackground(getMap(), true); break;
                            case 3: break;
                            case 4: updateBackground(getMap(), true); break;
                            case 5: break;
                            case 6: updateBackground(getMap(), true); break;
                            case 7: break;
                            case 8: break;
                            case 9: updateBackground(getMap(), true); break;
                            case 10: break;
                            case 11: break;
                            case 12: updateBackground(getMap(), true); break;
                        }
                    }
                    for (int iv = 0; iv < t2.bullets.size(); iv++) {
                        Bullet b2 = t2.bullets.get(iv);
                        if (b.intersects(b2) && b != b2) {
                            t.bullets.remove(b);
                            t2.bullets.remove(b2);
                        }
                    }
                }
            }
           
            // wall collisions
            for (Wall w : walls) {
                if (w.intersects(t)) {
                    if (w.contains(t.x + speed + 1, player.y) || w.contains(t.x + size - speed - 1, t.y)) {
                        t.y = w.y + size;
                    } else if (w.contains(t.x, t.y + speed + 1)
                            || w.contains(t.x, t.y + size - speed - 1)) {
                        t.x = w.x + size;
                    } else if (w.contains(t.x + speed + 1, t.y + size)
                            || w.contains(t.x + size - speed - 1, t.y + size)) {
                        t.y = w.y - size;
                    } else if (w.contains(t.x + size, t.y + size - speed - 1)
                            || w.contains(t.x + size, t.y + speed + 1)) {
                        t.x = w.x - size;
                    }
                }

                for (int ii = 0; ii < t.bullets.size(); ii++) {
                    Bullet b = t.bullets.get(ii);
                    if (b.intersects(w) && w.type != H) {
                        b.bounces--;
                        if (b.bounces >= 0) {
                            if (b.x - b.dx >= w.x + size){
                                 b.dx *= -1;
                                 b.move();
                            }
                            if (b.x - b.dx <= w.x){
                                b.dx *= -1;
                                b.move();
                            }
                            if (b.y - b.dy <= w.y){
                                b.dy *= -1;
                                b.move();
                            }
                            if (b.y - b.dy >= w.y + size){
                                b.dy *= -1;
                                b.move();
                            }
                        } else {
                            t.bullets.remove(b);
                        }
                    }
                }
            }

            //Enemy AI
            if (t.type == E) {
                t.topRotation = (int) Math.toDegrees(Math.atan2(((player.y * 2 + size) / 2) - ((t.y * 2 + size) / 2),
                        ((player.x * 2 + size) / 2) - ((t.x * 2 + size) / 2))) + 90;
                t.rotateTop = rotateImage(t.top, t.topRotation).getScaledInstance((int) (size * 1.5),
                        (int) (size * 1.5),
                        Image.SCALE_DEFAULT);
                if (t.cooldown <= 0) {
                    double length = Math
                            .sqrt(((player.x * 2 + size) / 2 - (t.x * 2 + size) / 2) * ((player.x * 2 + size) / 2
                                    - (t.x * 2 + size) / 2)
                                    + ((player.y * 2 + size) / 2 - (t.y * 2 + size) / 2)
                                            * ((player.y * 2 + size) / 2 - (t.y * 2 + size) / 2));
                    // t.shoot(((player.x * 2 + size) / 2 - ((t.x * 2 + size) / 2)) / length * 10,
                    //         ((player.y * 2 + size) / 2 - ((t.y * 2 + size) / 2)) / length * 10);
                    t.cooldown = 50;

                    int r = random.nextInt(4);
                    int rr = random.nextInt(2);

                    if (r == 0) {
                        if (rr == 0) {
                            edx = -speed2;
                            edy = -speed2;
                            t.rotation = 315;
                        } else if (rr == 1) {
                            edx = speed2;
                            edy = -speed2;
                            t.rotation = 45;
                        } else {
                            edy = -speed;
                            t.rotation = 0;
                        }
                    } else if (r == 1) {
                        if (rr == 0) {
                            edx = -speed2;
                            edy = speed2;
                            t.rotation = 225;
                        } else if (rr == 1) {
                            edx = speed2;
                            edy = speed2;
                            t.rotation = 135;
                        } else {
                            edy = speed;
                            t.rotation = 180;
                        }
                    } else if (r == 2) {
                        edx = -speed;
                        t.rotation = 270;
                    } else if (r == 3) {
                        edx = speed;
                        t.rotation = 90;
                    }
                    t.rotateTank = rotateImage(t.tank, t.rotation).getScaledInstance((int) (size * 1.5),
                            (int) (size * 1.5),
                            Image.SCALE_DEFAULT);

                }
                t.move(edx, edy);
                t.cooldown--;
            }
        }
        repaint();
    }
}