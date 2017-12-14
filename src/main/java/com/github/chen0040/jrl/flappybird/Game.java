package com.github.chen0040.jrl.flappybird;

import java.util.*;

public class Game {

    public static final int FPS = 60;
    public static final int SCREEN_WIDTH = 288;
    public static final int SCREEN_HEIGHT = 512;

    // amount by which base can maximum shift to left
    public static final int PIPE_GAP_SIZE = 100; // gap between upper and lower part of pipe
    public static final double BASE_Y = SCREEN_HEIGHT * 0.79;

    public static final Random random = new Random(42);

    public static final int PIPE_HEIGHT = 320;

    protected Map<String, Integer> state_word2idx = new HashMap<>();
    protected Map<Integer, String> state_idx2word = new HashMap<>();

    public Game() {
        List<Integer> xValues = new ArrayList<>();
        for(int i=-40; i < 140; i+=10){
            xValues.add(i);
        }
        for(int i=140; i < 421; i+=70) {
            xValues.add(i);
        }
        xValues.sort(Integer::compare);
        List<Integer> yValues = new ArrayList<>();
        for(int i=-300; i < 180; i+=10) {
            yValues.add(i);
        }
        for(int i=180; i < 421; i+=60) {
            yValues.add(i);
        }
        yValues.sort(Integer::compare);
        List<Integer> vValues = new ArrayList<>();
        for(int i=-10; i < 11; i++) {
            vValues.add(i);
        }

        int idx = 0;
        for(Integer x : xValues){
            for(Integer y : yValues) {
                for(Integer v : vValues) {
                    String word = x + "_" + y + "_" + v;
                    state_idx2word.put(idx, word);
                    state_word2idx.put(word, idx);
                    idx++;
                }
            }
        }
    }

    public int mapState(double xdif, double ydif, int vel) {

        // Map the (xdif, ydif, vel)to the respective state, with regards to the grids
        // The state is a string, "xdif_ydif_vel"
        // X ->[-40, -30.. .120]U[140, 210 ...420]
        // Y ->[-300, -290 ...160]U[180, 240 ...420]

        if(xdif < 140) {
            xdif = (int)xdif - ((int)(xdif) % 10);
        } else {
            xdif = (int)xdif - ((int)(xdif) % 70);
        }

        if(ydif < 180) {
            ydif = (int)ydif - ((int)(ydif) % 10);
        } else {
            ydif = (int)(ydif) - ((int)(ydif) % 60);
        }

        String state_word = xdif + "_" + ydif + "_" + vel;

        return state_word2idx.get(state_word);
    }


    public Pipe getRandomPipe() {
        // returns a randomly generated pipe
        // y of gap between upper and lower pipe
        int gapY = random.nextInt((int) (BASE_Y * 0.6 - PIPE_GAP_SIZE));
        gapY += (int) (BASE_Y * 0.2);

        int pipeX = SCREEN_WIDTH + 10;

        return new Pipe(pipeX, gapY + PIPE_GAP_SIZE, gapY - PIPE_HEIGHT);
    }

    public Pipe getRandomPipe(int x) {
        Pipe pipe = getRandomPipe();
        pipe.x = x;
        return pipe;
    }

    public Pipe[] getRandomPipes() {
        Pipe pipe1 = getRandomPipe(SCREEN_WIDTH + 200);
        Pipe pipe2 = getRandomPipe(SCREEN_WIDTH + 200 + SCREEN_WIDTH / 2);
        return new Pipe[] { pipe1, pipe2 };
    }


}
