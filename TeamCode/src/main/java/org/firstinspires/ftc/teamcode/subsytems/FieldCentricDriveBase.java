package org.firstinspires.ftc.teamcode.subsytems;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.DeferredCommand;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.util.Collections;


public class FieldCentricDriveBase extends SubsystemBase {
    private final Follower follower;

    public FieldCentricDriveBase(HardwareMap hardwareMap) {
        follower = Constants.create(hardwareMap);
    }

    public void manual(double forward, double lateral, double turn) {
        DrivePowers powers = ManualDrive.fieldCentric(
                forward,
                -lateral,
                -turn,
                follower.pose().heading()
        );
        follower.manual(powers);
    }

    public void resetHeading() {
        Pose current = follower.pose();
        follower.setPose(new Pose(current.x(), current.y(), Math.toRadians(90)));
    }

    @Override
    public void periodic() {
        follower.update();
    }

    private Path getPath(Pose target) {
        return line(follower.pose(), target).linear(follower.pose(), target);
    }

    public Command driveTo(Pose target) {
        return new DeferredCommand(
                () -> new FollowPathCommand(follower, getPath(target)),
                Collections.<Subsystem>singletonList(this)
        );
    }

    public void setStartingPose(Pose pose) {
        follower.setPose(pose);
    }

    public Pose getPose() {
        return follower.pose();
    }
}