package com.github.chen0040.jrl.flappybird.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrashInfo {
    private boolean crashed;
    private boolean groundCrashed;

    public CrashInfo(boolean crashed, boolean groundCrashed) {
        this.crashed = crashed;
        this.groundCrashed = groundCrashed;
    }


}
