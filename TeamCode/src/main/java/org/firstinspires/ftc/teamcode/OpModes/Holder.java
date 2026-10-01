package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.math.Pose;

import lombok.Getter;
import lombok.Setter;

public class Holder {


    public enum Alliance{
        RED,
        BLUE,
        NONE
    }
    @Getter @Setter private static Alliance alliance = Alliance.NONE;
    @Getter @Setter private static Pose pos = new Pose(0,0,0);
}
