package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.Commands;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import static org.firstinspires.ftc.teamcode.OpModes.Holder.*;

import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@TeleOp(name = "main")
public class mainOpMode extends CommandOpMode {

    private DriveBase driveBase;
    private Intake intake;

    private Shooter shooter;

    private Trigger shootTrigger;
    private Trigger outtakeTrigger;
    private GamepadEx driver;
    //private GamepadEx operator = new GamepadEx(gamepad2);
    public void initialize(){

        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        driveBase = new DriveBase(hardwareMap);

        driver = new GamepadEx(gamepad1);

//        driver.getGamepadButton(GamepadKeys.Button.A)
//                .whenPressed(driveBase.driveTo(new Pose(0,0,0)));
            driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                    .whenPressed(intake.runIntake())
                    .whenReleased(intake.stopIntake());


            shootTrigger = new Trigger(()->driver.getButton(GamepadKeys.Button.RIGHT_BUMPER)
            && shooter.readyToShoot()
            && shooter.getIsOpen())
                    .whenActive(new WaitCommand(750).andThen(intake.runIntake()));

            outtakeTrigger = new Trigger(()->gamepad1.left_trigger_pressed)
                    .whenActive(()->schedule(intake.reverseIntake()))
                            .whenInactive(()->schedule(intake.stopIntake()));

            driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                    .whenPressed(shooter.shoot())
                    .whenReleased(shooter.idle())
                    .whenReleased(intake.stopIntake());
            driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                            .whenPressed(()->driveBase.setHeading(0));


        driveBase.setDefaultCommand(new RunCommand(()->driveBase.manual(
                driver.getLeftY(),
                -driver.getLeftX(),
                -driver.getRightX()/2
        ),driveBase));

        while(opModeInInit()){
            telemetry.addData("Press b for blue and y for red",null);
            telemetry.update();
            if(gamepad1.b){
                Holder.setAlliance(Alliance.BLUE);
            }
            if(gamepad1.y){
                Holder.setAlliance(Alliance.RED);
            }
        }

        if (Holder.getAlliance() == Alliance.BLUE){
            driveBase.setHeading(90);
        } else{
            driveBase.setHeading(270);
        }
    }

    public void run(){
        super.run();
    }
}
