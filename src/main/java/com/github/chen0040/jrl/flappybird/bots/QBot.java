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


        agent.update(lastState, lastAction, mapState(0, 0, 10), -1000);

        for(int i=moves.size()-1; i >=0; --i){
            Move move = moves.get(i);
            agent.update(move.oldState, move.action, move.newState, move.reward);
        }

    }
}
