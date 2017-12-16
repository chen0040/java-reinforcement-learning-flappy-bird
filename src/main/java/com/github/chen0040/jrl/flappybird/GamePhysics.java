package com.github.chen0040.jrl.flappybird;

import com.github.chen0040.jrl.flappybird.utils.CrashInfo;

import java.awt.*;

public class GamePhysics {

    private static Pipe[] zip(Pipe[] pipes1, Pipe[] pipes2) {
        Pipe[] result = new Pipe[pipes1.length + pipes2.length];
        for(int i=0; i < pipes1.length; ++i) {
            result[i] = pipes1[i];
        }
        for(int i=0; i < pipes2.length; ++i) {
            result[pipes1.length + i] = pipes2[i];
        }
        return result;
    }

    // returns True if player collides with base or pipes.
    public static CrashInfo checkCrash(int player_x, int player_y, Pipe[] upperPipes, Pipe[] lowerPipes, GameAssets assets) {
        int player_width = assets.getPlayerWidth();
        int player_height = assets.getPlayerHeight();
        // if player crashes into ground
        if((player_y + player_height >= Game.BASE_Y - 1 ) || (player_y + player_height <= 0)){
            return new CrashInfo(true, true);
        } else {
            Rectangle playerRect = new Rectangle(player_x, player_y, player_width, player_height);
            int pipe_width = assets.getPipeWidth();
            int pipe_height = assets.getPipeHeight();
            for(Pipe pipe : zip(upperPipes, lowerPipes)) {
                Rectangle pipeRect = new Rectangle(pipe.x, pipe.y, pipe_width, pipe_height);
                if(playerRect.intersects(pipeRect)){
                    return new CrashInfo(true, false);
                }
            }
            return new CrashInfo(false, false);
        }
    }


}
