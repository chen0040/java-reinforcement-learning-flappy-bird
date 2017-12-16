package com.github.chen0040.jrl.flappybird.bots;

import com.github.chen0040.jrl.flappybird.Game;
import com.github.chen0040.rl.learning.qlearn.QLearner;

public class QBot extends Bot {

    private final QLearner agent;

    public QBot(Game game, QLearner agent) {
        super(game);
        this.agent = agent;

    }

    @Override
    protected int selectAction(int state) {
        return this.agent.selectAction(state).getIndex();
    }

    @Override
    public void updateStrategy() {


        //agent.update(lastState, lastAction, game.stateText(0, 0, 10), -1000);

         // Flag if the bird died in the top pipe
        int y_dif = Integer.parseInt(game.stateText(moves.get(moves.size()-1).newState).split("_")[1]);
        boolean high_death_flag = y_dif > 120;

        for(int i=moves.size()-1; i >=0; --i){
            Move move = moves.get(i);
            double r = move.reward;
            if(i == moves.size()-1 || i == moves.size() - 2) {
                r = -1000;
            } else if(high_death_flag) {
                r = -1000;
            }

            agent.update(move.oldState, move.action, move.newState, r);
        }

        reset();

    }
}
