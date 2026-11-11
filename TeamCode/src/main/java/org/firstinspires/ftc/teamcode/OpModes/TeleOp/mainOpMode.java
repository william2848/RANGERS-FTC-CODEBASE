package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

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

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        operator = new GamepadEx(gamepad2);
        driver = new GamepadEx(gamepad1);
        driveBase = new DriveBase(hardwareMap);
        driveBase.setDefaultCommand(new RunCommand(()->driveBase.manual(driver.getLeftY(), driver.getLeftX(), driver.getRightX()),driveBase));

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(() -> shooter.shoot()))
//                .whileHeld(new RunCommand(() ->
//                      intake.setTransitionOnly(shooter.isAtSpeed() ? -1 : 0)))
                .whenReleased(new InstantCommand(() -> {
                    shooter.stop();
                    intake.stop();
                }));

        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(() -> intake.setIntakeAndTransition(0.8, -1)))
                .whenReleased(new InstantCommand(() -> intake.stop()));

        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(() -> intake.setTransitionOnly(1)))
                .whenReleased(new InstantCommand(() -> intake.stop()));
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
        telemetry.addData("Target RPM", shooter.getTargetRPM());
        telemetry.addData("Right RPM", shooter.getRightRPM());
        telemetry.addData("Left RPM", shooter.getLeftRPM());
        telemetry.addData("At Speed", shooter.isAtSpeed());
        telemetry.addData("kP", RobotConstants.shooterConstants.kP);
        telemetry.addData("kI",RobotConstants.shooterConstants.kI);
        telemetry.addData("kD",RobotConstants.shooterConstants.kD);
        telemetry.addData("kV", RobotConstants.shooterConstants.kV);
        telemetry.addData("Target RPM cfg", RobotConstants.shooterConstants.TARGET_RPM);
        telemetry.update();
    }
}
