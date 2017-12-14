package com.github.chen0040.jrl.flappybird;

import com.github.chen0040.jrl.flappybird.utils.ResourceFileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GameAssets {
    private static GameAssets instance;
    private Map<String, Image> images = new HashMap<>();
    public static synchronized GameAssets getInstance() {
        if(instance==null){
            instance = new GameAssets();
        }
        return instance;
    }

    private GameAssets(){
        loadImage("background-day","sprites/background-day.png");

        images.put("background", getImage("background-day"));
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
}
