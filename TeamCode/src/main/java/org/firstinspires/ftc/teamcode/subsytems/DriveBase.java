package org.firstinspires.ftc.teamcode.subsytems;

import static com.pedropathing.api.Paths.line;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.curves.Line;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

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

    private Path getPath(Pose target){
        return line(follower.pose(),target).linear(follower.pose(),target);
    }

    public FollowPathCommand driveTo(Pose target){
        return new FollowPathCommand(follower, getPath(target));
    }



}
