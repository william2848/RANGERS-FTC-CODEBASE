package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.shootSequence;
import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.RobotConstants;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@TeleOp(name="main")
public class mainOpMode extends CommandOpMode {

    private DriveBase driveBase;
    private GamepadEx driver;
    private GamepadEx operator;
    private Shooter shooter;
    private Intake intake;

    @Override
    public void initialize() {
        // Send telemetry to both the Driver Station and the FTC Dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        operator = new GamepadEx(gamepad2);
        driver = new GamepadEx(gamepad1);
        driveBase = new DriveBase(hardwareMap);
        driveBase.setDefaultCommand(new RunCommand(()->driveBase.manual(driver.getLeftY(), driver.getLeftX(), driver.getRightX()),driveBase));
        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(driveBase.driveTo(new Pose(10,5,Math.toRadians(Math.PI))));
        driver.getGamepadButton(GamepadKeys.Button.B).whenPressed(new shootSequence(shooter,intake));
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).toggleWhenPressed(intake.runIntake(RobotConstants.intakeConstants.intakePower, RobotConstants.intakeConstants.transitionPower),intake.stopIntake());
    }

    @Override
    public void waitForStart() {
        while (!isStarted() && !isStopRequested()) {
            driver.readButtons();

            if (driver.getButton(GamepadKeys.Button.B)) {
                Holder.setAlliance(Holder.Alliance.BLUE);
            }
            if (driver.getButton(GamepadKeys.Button.Y)) {
                Holder.setAlliance(Holder.Alliance.RED);
            }

            telemetry.addLine("Press B for Blue\n press Y for Red");
            telemetry.update();
        }
    }

    @Override
    public void run() {
        driver.readButtons();
        super.run();

        telemetry.addData("PosX", driveBase.getPose().x());
        telemetry.addData("PosY", driveBase.getPose().y());
        telemetry.addData("Rotation",driveBase.getPose().heading());
        // Telemetry (also graphed on the Dashboard)
        telemetry.addData("Target (ticks/Sec):", shooter.getTargetTicksPerSec());
        telemetry.addData("Right (Ticks/Sec):", shooter.getRightVelocity());
        telemetry.addData("Left (Ticks/Sec):", shooter.getLeftVelocity());
        telemetry.update();
    }
}
