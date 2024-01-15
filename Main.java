//Create frame/panel/drawing panel
//collect input
//draw everything

import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

    int screenHeight, screenWidth;

    DrawingPanel drawing = new DrawingPanel();

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
    }

    private class DrawingPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenWidth - 20, screenHeight - 20);
        }
    }

}
