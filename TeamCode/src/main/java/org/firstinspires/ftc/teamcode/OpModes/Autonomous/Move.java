package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import com.pedropathing.math.Pose;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;


@Autonomous(name = "Move")
public class Move extends CommandOpMode {

    private DriveBase driveBase;



    public void initialize(){

        super.reset();
        driveBase = new DriveBase(hardwareMap);
        driveBase.setStartingPose(new Pose(0,0,0));

        driveBase.register();



        schedule(new SequentialCommandGroup(
                driveBase.driveTo(new Pose(0,35,0)),
                driveBase.driveTo(new Pose(79,46,0))
        ));
    }

    public void run(){
        super.run();
        telemetry.addData("x", driveBase.getPose().x());
        telemetry.addData("y", driveBase.getPose().y());
        telemetry.addData("heading(deg)", Math.toDegrees(driveBase.getPose().heading()));
        telemetry.update();
    }

}


