package com.github.chen0040.jrl.flappybird.bots;

public class Bot {

    public static String mapState(double xdif, double ydif, int vel) {

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

        return ((int)(xdif)) + "_" + ((int)(ydif)) + "_" + vel;
    }
}
