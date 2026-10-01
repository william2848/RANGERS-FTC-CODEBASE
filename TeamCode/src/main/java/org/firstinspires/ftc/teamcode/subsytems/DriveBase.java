package org.firstinspires.ftc.teamcode.subsytems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.pedro.Constants;

public class DriveBase extends SubsystemBase {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees(); // add this

    public DriveBase() {
        follower = Constants.create(hardwareMap);
        register();
    }

    public void manual(double forward, double lateral, double turn){
        follower.manual(forward, lateral, turn);
    }
    public void periodic(){
        follower.update();
    }



}
