package com.github.chen0040.jrl.flappybird.bots;

import com.github.chen0040.jrl.flappybird.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Bot {

    protected int lastState;
    protected int lastAction;
    protected List<Move> moves = new ArrayList<>();
    protected final Game game;


    public static final int PLAYER_HEIGHT = 32;
    public static final int PLAYER_WIDTH = 64;

    public Bot(Game game) {
        this.game = game;


    }



    protected abstract int selectAction(int state);

    public abstract void updateStrategy();

    public int act(int xdif, int ydif, int vel){

        //Chooses the best action with respect to the current state - Chooses 0 (don't flap) to tie-break

        int state = game.mapState(xdif, ydif, vel);

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
    }
}
