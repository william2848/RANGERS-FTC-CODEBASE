package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.math.Pose;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import static org.firstinspires.ftc.teamcode.OpModes.Holder.*;

import org.firstinspires.ftc.teamcode.automatedFunct.AutoScript;
import org.firstinspires.ftc.teamcode.subsytems.DriveBase;

public class mainOpMode extends CommandOpMode {

    private DriveBase driveBase = new DriveBase();
    private GamepadEx driver = new GamepadEx(gamepad1);
    private AutoScript autoScript = new AutoScript();
    private GamepadEx operator = new GamepadEx(gamepad2);
    public void initialize(){
        driveBase.setDefaultCommand(new RunCommand(()->driveBase.manual(
                driver.getLeftY(),
                driver.getLeftX(),
                driver.getRightX()
        )));

        operator.getGamepadButton(GamepadKeys.Button.B).whenPressed(new RunCommand(()->autoScript.initialize()));
        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(driveBase.driveTo(new Pose(0,0,0)));
        while(opModeInInit()){
            telemetry.addData("Press b for blue and y for red",null);
            if(gamepad1.b){
                Holder.setAlliance(Alliance.BLUE);
            }
            if(gamepad1.y){
                Holder.setAlliance(Alliance.RED);
            }
        }
    }
}
