package com.github.chen0040.jrl.flappybird;

import java.awt.*;
import javax.swing.JFrame;


public class Application extends JFrame {

    public Application() {

        initUI();
    }

    private void initUI() {

        add(new GameBoard(new Game()));

        setTitle("Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
    }
}