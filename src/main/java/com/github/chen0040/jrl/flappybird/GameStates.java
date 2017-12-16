package com.github.chen0040.jrl.flappybird;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStates {

    private static final Logger logger = LoggerFactory.getLogger(GameStates.class);

    protected Map<String, Integer> state_word2idx = new HashMap<>();
    protected Map<Integer, String> state_idx2word = new HashMap<>();

    public GameStates() {
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

    public int count() {
        return state_idx2word.size();
    }

    public String stateText(int stateIdx) {
        return state_idx2word.get(stateIdx);
    }

    public int mapState(int xdif, int ydif, int vel) {

        // Map the (xdif, ydif, vel)to the respective state, with regards to the grids
        // The state is a string, "xdif_ydif_vel"
        // X ->[-40, -30.. .120]U[140, 210 ...420]
        // Y ->[-300, -290 ...160]U[180, 240 ...420]

        if(xdif < 140) {
            xdif = xdif - (xdif % 10);
        } else {
            xdif = xdif - (xdif % 70);
        }

        if(ydif < 180) {
            ydif = ydif - (ydif % 10);
        } else {
            ydif = ydif - (ydif % 60);
        }

        String state_word = xdif + "_" + ydif + "_" + vel;

        //logger.info(state_word);

        return state_word2idx.get(state_word);
    }
}
