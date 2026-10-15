package org.firstinspires.ftc.teamcode.automatedFunct;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsytems.DriveBase;

@Autonomous(name = "AutoScript")
public class AutoScript extends CommandOpMode {

    private DriveBase driveBase = new DriveBase();
    private GamepadEx operatorPad = new GamepadEx(gamepad2);


    public void initialize(){


        schedule(new SequentialCommandGroup(
                driveBase.driveTo(new Pose(0,0,0)),
                driveBase.driveTo(new Pose(20, 30, Math.toRadians(90))),
                driveBase.driveTo(new Pose( 45, 20, Math.toRadians(45))),
                driveBase.driveTo(new Pose(86,30,Math.toRadians(20)))
                ));
    }

}
