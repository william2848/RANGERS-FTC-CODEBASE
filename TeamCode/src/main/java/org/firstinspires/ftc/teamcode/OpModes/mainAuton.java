package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
    @Autonomous(name = "AutoRed")
    public class mainAuton extends CommandOpMode {

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


