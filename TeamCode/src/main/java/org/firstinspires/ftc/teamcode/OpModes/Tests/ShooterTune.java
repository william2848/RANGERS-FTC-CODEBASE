package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.subsytems.RobotConstants;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@TeleOp(name = "ShooterTune")
public class ShooterTune extends CommandOpMode {

    private Shooter shooter;

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        shooter = new Shooter(hardwareMap,telemetry);
    }

    @Override
    public void run() {
        super.run();

        shooter.updateCoefficiants();   // picks up Dashboard edits
        if (gamepad1.a) {
            shooter.shootMax();            // re-reads TARGET_RPM
        } else {
            shooter.stop();
        }

        telemetry.addData("Velocity",shooter.getVelocity());
        telemetry.addData("kP live", RobotConstants.shooterConstants.kP);
        telemetry.addData("kV live", RobotConstants.shooterConstants.kV);
        telemetry.update();
    }
}