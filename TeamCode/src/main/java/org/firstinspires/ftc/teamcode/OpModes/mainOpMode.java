package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import static org.firstinspires.ftc.teamcode.OpModes.Holder.*;
import org.firstinspires.ftc.teamcode.Commands.shootSequence;
import org.firstinspires.ftc.teamcode.subsytems.Constants;
import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@TeleOp(name="main")
public class mainOpMode extends CommandOpMode {

    private DriveBase driveBase;
    private GamepadEx driver;
    private GamepadEx operator;
    private Shooter shooter;
    private Intake intake;
    public void initialize(){
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        operator = new GamepadEx(gamepad2);
        driver = new GamepadEx(gamepad1);
        driveBase = new DriveBase(hardwareMap);
        driveBase.setDefaultCommand(new RunCommand(()->driveBase.manual(driver.getLeftY(), driver.getLeftX(), driver.getRightX()),driveBase));
        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(driveBase.driveTo(new Pose(0,0,0)));
        driver.getGamepadButton(GamepadKeys.Button.B).whenPressed(new shootSequence(shooter,intake));
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).toggleWhenPressed(intake.runIntake(Constants.intakeConstants.mainIntakeVel, Constants.intakeConstants.transferVel),intake.stopIntake());
        while(opModeInInit()){
            telemetry.addData("Press b for blue and y for red",null);

            if(gamepad1.b) Holder.setAlliance(Alliance.BLUE);
            if(gamepad1.y) Holder.setAlliance(Alliance.RED);
            telemetry.addData("PosX", driveBase.getPose().x());
            telemetry.addData("PosY", driveBase.getPose().y());
            telemetry.addData("Rotation",driveBase.getPose().heading());
        }
    }
}
