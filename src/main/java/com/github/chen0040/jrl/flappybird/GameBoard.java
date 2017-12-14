package com.github.chen0040.jrl.flappybird;

import com.github.chen0040.jrl.flappybird.utils.ResourceFileUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

public class GameBoard extends JPanel {

    Image bardejov;

    private void initBoard() {

        loadImage();

        int w = bardejov.getWidth(this);
        int h =  bardejov.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon(ResourceFileUtils.getBytes("sprites/background-day.png"));
        bardejov = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(bardejov, 0, 0, null);
    }
}