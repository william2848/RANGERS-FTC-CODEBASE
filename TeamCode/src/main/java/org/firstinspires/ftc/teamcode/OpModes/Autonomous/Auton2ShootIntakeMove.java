package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@Autonomous(name = "ShootIntakeMove")
public class Auton2ShootIntakeMove extends CommandOpMode {

    private DriveBase driveBase;
    private Shooter shooter;
    private Intake intake;



    public void initialize(){

        super.reset();
        driveBase = new DriveBase(hardwareMap);
        driveBase.setStartingPose(new Pose(0,0,0));
        shooter = new Shooter(hardwareMap);
        // shooter.register(); add if not working
        driveBase.register();



        schedule(new SequentialCommandGroup(
                new InstantCommand(shooter::shoot),
                new WaitUntilCommand(shooter::isAtSpeed).withTimeout(3000),
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