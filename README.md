# java-reinforcement-learning-flappy-bird

Demo of the [java-reinforcement-learning](https://github.com/chen0040/java-reinforcement-learning) package. library using flappy bird

The project can be considered a Java port of the [https://github.com/chncyhn/flappybird-qlearning-bot](https://github.com/chncyhn/flappybird-qlearning-bot) 
for python, with the reinforcement learning component replaced by [java-reinforcement-learning](https://github.com/chen0040/java-reinforcement-learning)

# Usage

Git clone the project, run make.ps1 or make.sh to build the reinforcement-learning-flappy-bird.jar in the bin folder. 

Next run the following java command:

```bash
java -jar bin/reinforcement-learning-flappy-bird.jar
```

With the flappy bird game opened, press the "Start" button to start, "Accelerate" button to accelerate the game and learning,
"Stop" button to stop the game.

The demo video can be found in the image link below:

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/XigrhpTFnh8/0.jpg)](https://www.youtube.com/watch?v=XigrhpTFnh8)

# Q Learning with Flappy Bird

The following java codes show the implementation of the Q-learn bot for flappy bird TD-learning using [java-reinforcement-learning](https://github.com/chen0040/java-reinforcement-learning) 


```java
public class QBot extends Bot {

    private final QLearner agent;

    public QBot(Game game, QLearner agent) {
        super(game);
        this.agent = agent;

    }

    @Override
    protected int selectAction(int state) {
        double q0 = this.agent.getModel().getQ(state, 0);
        double q1 = this.agent.getModel().getQ(state, 1);
        if(q0 >= q1) return 0;
        return 1;
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
            } else if(high_death_flag && move.action == 1) {
                r = -1000;
                high_death_flag = false;
            }

            agent.update(move.oldState, move.action, move.newState, r);
        }

        reset();

    }
}
```

The main challenge is to map the distance to the pipes and velocity of the flappy bird into a state. This project addresses
the state mapping using the solution from [https://github.com/chncyhn/flappybird-qlearning-bot](https://github.com/chncyhn/flappybird-qlearning-bot) and
converts the state string to a state integer for the QLearner. The state mapping implementation can be found in GameStates.java
