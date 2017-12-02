package com.github.chen0040.jrl.flappybird;

import java.util.Random;

public class Game {

    public static final int FPS = 60;
    public static final int SCREEN_WIDTH = 288;
    public static final int SCREEN_HEIGHT = 512;

    // amount by which base can maximum shift to left
    public static final int PIPE_GAP_SIZE = 100; // gap between upper and lower part of pipe
    public static final double BASE_Y = SCREEN_HEIGHT * 0.79;

    public static final Random random = new Random(42);

    public static final int PIPE_HEIGHT = 320;


    public Pipe getRandomPipe() {
        // returns a randomly generated pipe
        // y of gap between upper and lower pipe
        int gapY = random.nextInt((int) (BASE_Y * 0.6 - PIPE_GAP_SIZE));
        gapY += (int) (BASE_Y * 0.2);

        int pipeX = SCREEN_WIDTH + 10;

        return new Pipe(pipeX, gapY + PIPE_GAP_SIZE, gapY - PIPE_HEIGHT);
    }
}
