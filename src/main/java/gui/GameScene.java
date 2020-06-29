package gui;

import javax.swing.*;
import java.awt.*;

public class GameScene extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT =600;

    public GameScene() {
        super();
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.BasicUI();

    }

    public void BasicUI() {
        JButton b = new JButton("New Game");
        b.setBounds(10, 500, 100, 25);
        this.add(b);
    }

    public static void main(String[] args) {
        GameScene gui = new GameScene();
        JPanel j = new JPanel();
        j.setSize(5, 5); j.setLocation(100, 100);
        j.setBackground(Color.BLACK);

        for (int i=0; i<10; i++) {
            Point p = new Point();
            p.setLocation(10 + i*15,10); p.setBackground(Color.RED);
            gui.add(p);
        }

    }


}
