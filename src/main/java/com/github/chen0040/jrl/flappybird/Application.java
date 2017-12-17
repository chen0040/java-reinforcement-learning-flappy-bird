package com.github.chen0040.jrl.flappybird;

import java.awt.*;
import javax.swing.*;


public class Application extends JFrame {


    public Application() {

        initUI();
    }

    private void initUI() {

        Game game = new Game();
        add(game, BorderLayout.CENTER);

        JPanel commands = new JPanel(new BorderLayout());
        add(commands, BorderLayout.SOUTH);

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(e -> runGame(game));
        commands.add(btnStart, BorderLayout.WEST);

        JButton btnAccelerateLearning = new JButton("Accelerate");
        btnAccelerateLearning.addActionListener(e -> game.accelerate());
        commands.add(btnAccelerateLearning, BorderLayout.CENTER);

        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(e -> game.stop());
        commands.add(btnStop, BorderLayout.EAST);

        setTitle("Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT + commands.getHeight()));


    }

    private void runGame(Game game){
        game.run(gameOverInfo -> {
            runGame(game);
        });
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}