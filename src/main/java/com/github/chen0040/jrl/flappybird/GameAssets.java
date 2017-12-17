package com.github.chen0040.jrl.flappybird;

import com.github.chen0040.jrl.flappybird.utils.ResourceFileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameAssets {
    private Map<String, Image> images = new HashMap<>();
    private static final String[] background_colors = new String[] { "day","night"};
    private static final String[] bird_colors = new String[]{ "red", "blue", "yellow" };
    private static final String[] pipe_colors = new String[] { "green", "red" };
    private static final Random random = new Random();

    private String background_color = background_colors[0];
    private String player_color = bird_colors[0];
    private String pipe_color = pipe_colors[0];


    public GameAssets(){

        for(String background_color : background_colors) {
            loadImage("background-" + background_color, "sprites/background-" + background_color + ".png");
        }

        for(String bird_color : bird_colors) {
            loadImage(bird_color + "bird-downflap", "sprites/" + bird_color + "bird-downflap.png");
            loadImage(bird_color + "bird-midflap", "sprites/" + bird_color + "bird-midflap.png");
            loadImage(bird_color + "bird-upflap", "sprites/" + bird_color + "bird-upflap.png");
        }

        for(String pipe_color: pipe_colors) {
            loadImage("pipe-" + pipe_color, "sprites/pipe-" + pipe_color + ".png");
            loadImage("pipe-top-" + pipe_color, "sprites/pipe-top-" + pipe_color + ".png");
        }

        loadImage("base", "sprites/base.png");
        loadImage("message", "sprites/message.png");
    }

    public void reload() {
        reloadBackground();
        reloadPlayer();
        reloadPipe();


    }

    public void reloadBackground(){
        int index = random.nextInt(background_colors.length);
        background_color = background_colors[index];
    }

    public void reloadPlayer() {
        int index = random.nextInt(bird_colors.length);
        player_color = bird_colors[index];
    }

    public void reloadPipe() {
        int index = random.nextInt(pipe_colors.length);
        pipe_color = pipe_colors[index];
    }

    public Image getBackground() {
        return getImage("background-" + background_color);
    }

    public Image[] getPlayer() {
        return new Image[] {
                getImage(player_color+ "bird-downflap"),
                getImage(player_color + "bird-midflap"),
                getImage(player_color + "bird-upflap")
        };
    }

    public Image[] getPipe() {
        return new Image[] {
                getImage("pipe-top-" + pipe_color),
                getImage("pipe-" + pipe_color)
        };
    }



    public Image getImage(String name) {
        return images.get(name);
    }

    private Image loadImage(String name, String filePath) {

        Image img = null;
        InputStream inputStream = ResourceFileUtils.getResourceStream(filePath);
        try {
            img = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(img != null){
            images.put(name, img);
        }
        return img;

    }

    public int getPlayerHeight() {
        return getPlayer()[0].getHeight(null);
    }

    public int getPlayerWidth() {
        return getPlayer()[0].getWidth(null);
    }

    public int getPipeWidth() {
        return getPipe()[0].getWidth(null);
    }

    public int getPipeHeight() {
        return getPipe()[0].getHeight(null);
    }

    public Image getMessage() {
        return getImage("message");
    }

}
