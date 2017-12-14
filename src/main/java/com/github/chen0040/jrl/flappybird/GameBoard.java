package com.github.chen0040.jrl.flappybird;

import com.github.chen0040.jrl.flappybird.utils.ResourceFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameBoard extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(GameBoard.class);

    private Game game;

    public GameBoard(Game game){
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(GameAssets.getInstance().getImage("background"), 0, 0, null);
    }
}