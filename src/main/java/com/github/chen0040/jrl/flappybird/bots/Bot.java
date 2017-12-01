package com.github.chen0040.jrl.flappybird.bots;

public class Bot {

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

    public int act(double xdif, double ydif, int vel){

    //Chooses the best action with respect to the current state - Chooses 0 (don't flap) to tie-break

    String state = self.map_state(xdif, ydif, vel)

            self.moves.append( [self.last_state, self.last_action, state] ) # Add the experience to the history

    self.last_state = state # Update the last_state with the current state

        if self.qvalues[state][0] >= self.qvalues[state][1]:
    self.last_action = 0
            return 0
            else:
    self.last_action = 1
            return 1
    }
}
