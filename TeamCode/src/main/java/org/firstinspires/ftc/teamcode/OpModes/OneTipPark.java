package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.ParallelDeadlineGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.pedro.AutoPaths;
import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@Autonomous(name = "One tip",preselectTeleOp = "main")
public class OneTipPark extends LinearOpMode {

    private PoseFactory poseFactory;

    private AutoPaths paths = new AutoPaths();

    private boolean holdAuto = true;

    private Intake intake;
    private Shooter shooter;
    private DriveBase driveBase;

    public void initialize(){










    }

    public void runOpMode(){
        CommandScheduler.getInstance().reset();
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        driveBase = new DriveBase(hardwareMap);
        while(opModeInInit() && holdAuto){
            telemetry.addData("Press b for blue and y for red",null);
            telemetry.update();
            if(gamepad1.b){
                Holder.setAlliance(Holder.Alliance.BLUE);
                holdAuto = false;
            }
            if(gamepad1.y){
                Holder.setAlliance(Holder.Alliance.RED);
                holdAuto = false;
            }
        }
        AutoPaths.OneTipPark auto =  paths.new OneTipPark(false);
        driveBase.setPosition(auto.start);
        waitForStart();
        RobotLog.dd("STARTED", "True");
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(

                        shooter.shoot().withTimeout(3000),
                        intake.runIntake(),
                        new WaitCommand(3000),
                        intake.stopIntake(),
                        shooter.idle(),
                        driveBase.help()

                )

        );

        //CommandScheduler.getInstance().schedule(shooter.shoot());
        while(opModeIsActive()){
            CommandScheduler.getInstance().run();
            telemetry.addData("RAN",false);
            telemetry.update();
        }
    }

}
