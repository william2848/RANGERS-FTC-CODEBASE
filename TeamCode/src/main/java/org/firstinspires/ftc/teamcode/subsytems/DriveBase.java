package org.firstinspires.ftc.teamcode.subsytems;

import static com.pedropathing.api.Paths.line;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.view.contentcapture.DataShareWriteAdapter;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.curves.Line;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.pedro.Constants;

public class DriveBase extends SubsystemBase {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees(); // add this

    public DriveBase(HardwareMap hardwareMap) {
        follower = Constants.create(hardwareMap);
        follower.setHeading(Math.toRadians(270));
        register();
    }

    public void manual(double forward, double lateral, double turn){
        DrivePowers powers = ManualDrive.fieldCentric(
                forward,
                lateral,
                turn,
                follower.pose().heading()
        );
        follower.manual(powers);
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

    public void setHeading(double heading){
        follower.setHeading(Math.toRadians(heading));
    }



}
