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
import com.qualcomm.robotcore.util.RobotLog;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.Commands;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.ContinuousPIDController;
import org.firstinspires.ftc.teamcode.DrivePoints;
import org.firstinspires.ftc.teamcode.OpModes.Holder;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class DriveBase extends SubsystemBase {
    private Follower follower;

    private double targetDist = 0;
    private final PoseFactory p = PoseFactory.degrees(); // add this

    private final ContinuousPIDController turnController = new ContinuousPIDController(1,0,0);

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
                follower.pose().heading() + (Holder.getAlliance() == Holder.Alliance.RED ? 0 : Math.PI)
        );
        follower.manual(powers);
    }

    public void robotOrientedManual(double forward, double lateral, double turn){
        follower.manual(forward,lateral,turn);
    }
    public void periodic(){
        follower.update();
        RobotLog.dd("Pose","x " + follower.pose().x() + " y " + follower.pose().y());
    }

    private Path getPath(Pose target){
        return line(follower.pose(),target).linear(follower.pose(),target);
    }

    public FollowPathCommand driveTo(Pose target){
        return new FollowPathCommand(follower, getPath(target));
    }

    public FollowPathCommand followPath(Path path){
        return new FollowPathCommand(follower, path);
    }

    public void setHeading(double heading){
        follower.setHeading(Math.toRadians(heading));
    }

    private void autoAlignTo(){
        Pose target, robotToTarget;
        if(follower.pose().y()>72 && Holder.getAlliance() == Holder.Alliance.RED){
            target = DrivePoints.Hive.redUp;
        } else if (follower.pose().y()<72 && Holder.getAlliance() == Holder.Alliance.RED){
            target = DrivePoints.Hive.redPark;
        } else if (follower.pose().y()<72 && Holder.getAlliance() == Holder.Alliance.BLUE){
            target = DrivePoints.Hive.blueUp;
        } else{
            target = DrivePoints.Hive.bluePark;
        }

        robotToTarget = target.minus(follower.pose());
        targetDist = robotToTarget.toVector2D().magnitude();
        double angle;
        if(Holder.getAlliance() == Holder.Alliance.RED){
            angle = (Math.atan2(robotToTarget.y(), robotToTarget.x()))+Math.PI;
        } else {
            angle = Math.atan2(robotToTarget.y(), robotToTarget.x()) + Math.PI;
        }


        turnController.setSetPoint(angle);
        follower.manual(0,0, turnController.calculate(follower.pose().heading(),angle));
    }

    public void setPosition(Pose pos){
        Pose temp = pos;
        //temp.withHeading(Math.toRadians(pos.heading()));
        follower.setPose(temp);
    }

    public double getTargetDist(){
        return targetDist;
    }
    public void resetOdo(){
        if(Holder.getAlliance() == Holder.Alliance.BLUE){
            follower.setX(31);
            follower.setY(140);
        } else{
            follower.setX(58);
            follower.setY(12);
        }
    }

    public boolean aligned(){
        return false;
        //return Math.abs(turnController.getPositionError())<Math.toRadians(10) && follower.velocity().omega<.5;
    }

    public Command stop(){
        return new InstantCommand(()->{},this);
    }


    public Command autoAlign(){
        return Commands.run(
                ()->autoAlignTo(),
                this
        ).interruptOn(()->aligned());
    }

    public Command help(){
        return Commands.startRun(()->robotOrientedManual(.5,0,0),
                ()->robotOrientedManual(.5,0,0)).withTimeout(500)
                .whenFinished(()->robotOrientedManual(0,0,0));


    }



}
