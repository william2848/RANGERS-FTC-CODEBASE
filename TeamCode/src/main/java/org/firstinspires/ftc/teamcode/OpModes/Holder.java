package org.firstinspires.ftc.teamcode.OpModes;

import lombok.Getter;
import lombok.Setter;

public class Holder {
    public enum Alliance{
        RED,
        BLUE,
        NONE
    }
    @Getter @Setter private static Alliance alliance = Alliance.NONE;
}
