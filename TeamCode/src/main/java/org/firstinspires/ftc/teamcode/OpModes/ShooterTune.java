package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.subsytems.ShooterSubsystem;

@TeleOp(name = "ShooterTune")
public class ShooterTune extends CommandOpMode {

    private ShooterSubsystem shooter;

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        shooter = new ShooterSubsystem(hardwareMap);
    }

    @Override
    public void run() {
        super.run();

        shooter.updateCoefficiants();   // picks up Dashboard edits
        if (gamepad1.a) {
            shooter.shoot();            // re-reads TARGET_RPM
        } else {
            shooter.stop();
        }

        telemetry.addData("target (ticks/s)", shooter.getTargetTicksPerSec());
        telemetry.addData("left velocity", shooter.getLeftVelocity());
        telemetry.addData("right velocity", shooter.getRightVelocity());
        telemetry.update();
    }
}