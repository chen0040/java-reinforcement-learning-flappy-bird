package com.github.chen0040.jrl.flappybird.bots;

import com.github.chen0040.jrl.flappybird.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Bot {

    protected int lastState;
    protected int lastAction;
    protected List<Move> moves = new ArrayList<>();
    protected final Game game;
    protected int playerX;
    protected int playerY;

    public static final int PLAYER_HEIGHT = 32;
    public static final int PLAYER_WIDTH = 64;

    public Bot(Game game) {
        this.game = game;
    }

    public static int mapState(double xdif, double ydif, int vel) {

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

        return ((int)(xdif)) * 1000000 + ((int)(ydif)) * 1000 + vel;
    }

    protected abstract int selectAction(int state);

    public abstract void updateStrategy();

    public int act(double xdif, double ydif, int vel){

        //Chooses the best action with respect to the current state - Chooses 0 (don't flap) to tie-break

        int state = mapState(xdif, ydif, vel);

        if(lastState != -1) {
            this.moves.add(new Move(lastState, lastAction, state, 1)); // Add the experience to the history
        }

        int action = selectAction(state);

        lastState = state; // Update the last_state with the current state
        lastAction = action;

        return action;
    }

    public void reset() {
        lastState = -1;
        lastAction = -1;
        moves.clear();



        playerX = (int)(Game.SCREEN_WIDTH * 0.2);
        playerY = (Game.SCREEN_HEIGHT - PLAYER_HEIGHT) / 2;


    }
}
