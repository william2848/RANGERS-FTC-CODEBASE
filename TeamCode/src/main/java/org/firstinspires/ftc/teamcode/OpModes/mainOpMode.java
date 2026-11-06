package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsytems.DriveBase;
import org.firstinspires.ftc.teamcode.subsytems.IntakeTransitionSubsystem;
//import org.firstinspires.ftc.teamcode.subsytems.ServoSubsystem;
import org.firstinspires.ftc.teamcode.subsytems.ShooterSubsystem;

@TeleOp(name = "mainZ")
public class mainOpMode extends CommandOpMode {

    private DriveBase driveBase;
    private GamepadEx driver;
    private GamepadEx operator;
    private ShooterSubsystem shooterSubsystem;
    private IntakeTransitionSubsystem intakeTransitionSubsystem;
    //private ServoSubsystem servoSubsystem;

    @Override
    public void initialize() {
        // Send telemetry to both the Driver Station and the FTC Dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        driveBase = new DriveBase(hardwareMap);
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        intakeTransitionSubsystem = new IntakeTransitionSubsystem(hardwareMap);
        //servoSubsystem = new ServoSubsystem(hardwareMap);

        driveBase.setDefaultCommand(new RunCommand(() -> driveBase.manual(
                driver.getLeftY(),
                driver.getLeftX(),
                driver.getRightX()
        ), driveBase));

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(() -> shooterSubsystem.shoot()))
                .whenReleased(new InstantCommand(() -> shooterSubsystem.stop()));

        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(() -> intakeTransitionSubsystem.setIntakeAndTransition(0.8, 0.8)))
                .whenReleased(new InstantCommand(() -> intakeTransitionSubsystem.stop()));

        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(() -> intakeTransitionSubsystem.setTransitionOnly(0.8)))
                .whenReleased(new InstantCommand(() -> intakeTransitionSubsystem.stop()));

        // Add Button B -> just transition, also make a sequential servo command (.atSpeed)
        // for now, operator.getGamepadButton(GamepadKeys.Button.B).whenPressed(new RunCommand(()->autoScript.initialize()));
        // for now driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(driveBase.driveTo(new Pose(0,0,0)));
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

        // Telemetry (also graphed on the Dashboard)
        telemetry.addData("Target (ticks/Sec):", shooterSubsystem.getTargetTicksPerSec());
        telemetry.addData("Right (Ticks/Sec):", shooterSubsystem.getRightVelocity());
        telemetry.addData("Left (Ticks/Sec):", shooterSubsystem.getLeftVelocity());
        telemetry.update();
    }
}