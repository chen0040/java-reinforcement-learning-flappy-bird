package com.github.chen0040.jrl.flappybird;

import com.github.chen0040.jrl.flappybird.bots.Bot;
import com.github.chen0040.jrl.flappybird.bots.QBot;
import com.github.chen0040.jrl.flappybird.utils.CrashInfo;
import com.github.chen0040.jrl.flappybird.utils.Cycle;
import com.github.chen0040.jrl.flappybird.utils.GameOverInfo;
import com.github.chen0040.jrl.flappybird.utils.IterTools;
import com.github.chen0040.rl.actionselection.GreedyActionSelectionStrategy;
import com.github.chen0040.rl.learning.qlearn.QLearner;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Consumer;

public class Game extends JPanel {

    public static final int FPS = 60;
    public static final int SCREEN_WIDTH = 288;
    public static final int SCREEN_HEIGHT = 512;

    // amount by which base can maximum shift to left
    public static final int PIPE_GAP_SIZE = 100; // gap between upper and lower part of pipe
    public static final int BASE_Y = (int)(SCREEN_HEIGHT * 0.79);

    public static final Random random = new Random(42);

    public static final int PIPE_HEIGHT = 320;

    private GameStates states = new GameStates();

    private int playerx;
    private int playery;

    private int basex;
    private int baseShift;

    private int pipeVelX = -4;

    // player velocity, max velocity, downward accleration, accleration on flap
    private int playerVelY    =  -9;   // player's velocity along Y, default same as playerFlapped
    private int playerMaxVelY =  10;   // max vel along Y, max descend speed
    private int playerMinVelY =  -8;   // min vel along Y, max ascend speed
    private int playerAccY    =   1;   // players downward accleration
    private int playerFlapAcc =  -9;   // players speed on flapping
    private boolean playerFlapped = false; // True when player flaps

    private Pipe[] upperPipes;
    private Pipe[] lowerPipes;

    private int score;

    private int loopIter;
    private int playerIndex;

    private Cycle playerIndexGen;

    private Bot bot;

    private GameAssets assets = new GameAssets();

    private int generation = 1;

    private QLearner learner;

    public Game() {
        int actionCount = 2;
        learner = new QLearner(states.count(), actionCount);
        //learner.setActionSelection(GreedyActionSelectionStrategy.class.getCanonicalName());
        learner.getModel().setAlpha(0.7);
        learner.getModel().setGamma(1.0);
    }

    public int getGeneration() { return generation; }

    private void start() {


        bot = new QBot(this, learner);
        assets.reload();
        playerx = (int) (SCREEN_WIDTH * 0.2);
        playery = (SCREEN_HEIGHT - getPlayerHeight()) / 2;
        basex = 0;
        baseShift = getBase().getWidth(null) - assets.getBackground().getWidth(null);

        Pipe[] pipe_set1 = getRandomPipe(SCREEN_WIDTH + 200);
        Pipe[] pipe_set2 = getRandomPipe(SCREEN_WIDTH + 200 + SCREEN_WIDTH / 2);

        upperPipes = new Pipe[]{pipe_set1[1], pipe_set2[1]};
        lowerPipes = new Pipe[]{pipe_set1[0], pipe_set2[0]};

        pipeVelX = -4;

        // player velocity, max velocity, downward accleration, accleration on flap
        playerVelY = -9;   // player's velocity along Y, default same as playerFlapped
        playerMaxVelY = 10;   // max vel along Y, max descend speed
        playerMinVelY = -8;   // min vel along Y, max ascend speed
        playerAccY = 1;   // players downward acceleration
        playerFlapAcc = -9;   // players speed on flapping
        playerFlapped = false; // True when player flaps

        score = 0;

        playerIndexGen = IterTools.cycle(new int[]{0, 1, 2, 1});

        loopIter = 0;
        playerIndex = 0;
    }

    public void run(Consumer<GameOverInfo> onCompleted) {
        start();
        new Thread(() -> {
            while(true) {
                Pipe myPipe;
                if (-playerx + lowerPipes[0].x > -30) {
                    myPipe = lowerPipes[0];
                } else {
                    myPipe = lowerPipes[1];
                }

                if (bot.act(-playerx + myPipe.x, -playery + myPipe.y, playerVelY) == 1) {
                    if (playery > -2 * getPlayerHeight()) {
                        playerVelY = playerFlapAcc;
                        playerFlapped = true;
                    }
                }

                CrashInfo crashInfo = GamePhysics.checkCrash(playerx, playery, lowerPipes, upperPipes, assets);

                if(crashInfo.isCrashed()){
                    bot.updateStrategy();
                    generation++;
                    onCompleted.accept(new GameOverInfo(
                            playery,
                            crashInfo.isGroundCrashed(),
                            basex,
                            upperPipes,
                            lowerPipes,
                            score,
                            playerVelY
                    ));
                    break;
                }

                // check for score
                int playerMidPos = playerx + getPlayerWidth() / 2;
                for (Pipe pipe : upperPipes) {
                    int pipeMidPos = pipe.x + getPlayerWidth() / 2;
                    if (pipeMidPos <= playerMidPos && playerMidPos < pipeMidPos + 4) {
                        score += 1;
                    }
                }


                // playerIndex basex change
                if ((loopIter + 1) % 3 == 0) {
                    playerIndex = playerIndexGen.next();
                }
                loopIter = (loopIter + 1) % 30;
                basex = -((-basex + 100) % baseShift);

                // player's movement
                if (playerVelY < playerMaxVelY && !playerFlapped) {
                    playerVelY += playerAccY;
                }

                if (playerFlapped) {
                    playerFlapped = false;
                }
                playery += Math.min(playerVelY, BASE_Y - playery - getPlayerHeight());


                // move pipes to left
                for (Pipe pipe : upperPipes) {
                    pipe.x += pipeVelX;
                }
                for (Pipe pipe : lowerPipes) {
                    pipe.x += pipeVelX;
                }


                // add new pipe when first pipe is about to touch left of screen
                if (0 < upperPipes[0].x && upperPipes[0].x < 5) {
                    Pipe[] newPipe = getRandomPipe();
                    upperPipes = append(upperPipes, newPipe[1]);
                    lowerPipes = append(lowerPipes, newPipe[0]);
                }


                // remove first pipe if its out of the screen
                if (upperPipes[0].x < -getPipeWidth()) {
                    upperPipes = pop(upperPipes);
                    lowerPipes = pop(lowerPipes);
                }


                if(generation > 500) { // skip the display of the first 500 epoches
                    try {
                        Thread.sleep(20L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                repaint();
            }
        }).start();

    }

    private Pipe[] append(Pipe[] current, Pipe newPipe){
        Pipe[] result = new Pipe[current.length+1];
        for(int i=0; i < current.length; ++i){
            result[i] = current[i];
        }
        result[current.length] = newPipe;
        return result;
    }

    private Pipe[] pop(Pipe[] current) {
        Pipe[] result = new Pipe[current.length-1];
        for(int i=1; i < current.length; ++i) {
            result[i-1] = current[i];
        }
        return result;
    }

    public Image getBase() {
        return assets.getImage("base");
    }



    public int getPlayerHeight() {
        return assets.getPlayerHeight();
    }

    public int getPlayerWidth() {
        return assets.getPlayerWidth();
    }

    public int getPipeWidth() {
        return assets.getPipeWidth();
    }

    public Pipe[] getRandomPipe() {
        // returns a randomly generated pipe
        // y of gap between upper and lower pipe
        int gapY = random.nextInt((int) (BASE_Y * 0.6 - PIPE_GAP_SIZE));
        gapY += (int) (BASE_Y * 0.2);

        int pipeX = SCREEN_WIDTH + 10;

        return new Pipe[]{
                new Pipe(pipeX, gapY + PIPE_GAP_SIZE),
                new Pipe(pipeX, gapY - PIPE_HEIGHT)
        };
    }

    public Pipe[] getRandomPipe(int x) {
        Pipe[] pipes = getRandomPipe();
        for(Pipe pipe : pipes) {
            pipe.x = x;
        }
        return pipes;
    }


    public int mapState(int xdif, int ydif, int vel) {
        return states.mapState(xdif, ydif, vel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(assets.getBackground(), 0, 0, this);

        for(Pipe pipe : upperPipes) {
            g.drawImage(assets.getPipe()[0], pipe.x, pipe.y, this);
        }

        for(Pipe pipe : lowerPipes) {
            g.drawImage(assets.getPipe()[1], pipe.x, pipe.y, this);
        }

        Toolkit.getDefaultToolkit().sync();

        g.drawImage(assets.getImage("base"), basex, BASE_Y, this);

        // print score so player overlaps the score
        //showScore(score)
        g.drawImage(assets.getPlayer()[playerIndex], playerx, playery, this);

        g.drawString("Score: " + score, 100, 100);
        g.drawString("Generation: " + generation, 100, 80);
    }

    public String stateText(int stateIdx) {
        return states.stateText(stateIdx);
    }
}
