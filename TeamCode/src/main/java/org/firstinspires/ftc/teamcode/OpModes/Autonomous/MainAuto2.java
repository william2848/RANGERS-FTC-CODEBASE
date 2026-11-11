package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.RobotConstants;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

@Autonomous(name = "MainAuto2")
public class MainAuto2 extends OpMode {

    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private final PoseFactory p = PoseFactory.degrees();

    // ---------------- Poses ----------------
    private final Pose startPose   = p.of(50, 9, 90);
    private final Pose intakePose  = p.of(7.91, 8.39, 180);
    private final Pose toNextPose1 = p.of(33.78, 8.34, 180);
    private final Pose toNextPose2 = p.of(35.11, 122.61, 180);
    private final Pose toNextPose3 = p.of(59.11, 122.52, 180);
    private final Pose toShootPose = p.of(59.11, 132.48, 270);
    private final Pose toNextPose4 = p.of(59.12, 117.25, 270);
    private final Pose park = p.of(10.24, 117, 270);


    private Path toIntake() {
        return line(startPose, intakePose).linear(startPose, intakePose);
    }

    private Path toNext() {
        return line(intakePose, toNextPose1).linear(intakePose, toNextPose1);
    }

    private Path toNext2() {
        return line(toNextPose1, toNextPose2).linear(toNextPose1, toNextPose2);
    }

    private Path toNext3() {
        return line(toNextPose2, toNextPose3).linear(toNextPose2, toNextPose3);
    }

    // heading 180 -> 270 while driving to the shoot position
    private Path toShoot() {
        return line(toNextPose3, toShootPose).linear(toNextPose3, toShootPose);
    }

    private Path toNext4() {
        return line(toShootPose, toNextPose4).linear(toShootPose, toNextPose4);
    }

    private Path park() {
        return line(toNextPose4, park).linear(toNextPose4, park);
    }

    // ---------------- Commands ----------------
    private Command shootStationary() {
        return sequential(
                instant(() -> shooter.shoot()),
                waitMs(3000),
                instant(() -> intake.setTransitionOnly(-1)),
                waitMs(3000),
                instant(() -> shooter.stop()),
                instant(() -> intake.stop())
        );
    }

    private Command toNextShooterPosition() {
        return sequential(
                follow(follower, toNext()),
                follow(follower, toNext2()),
                follow(follower, toNext3()),
                follow(follower, toShoot())
        );
    }

    private Command intakeAndMove(){
       return parallel(
                follow(follower, toIntake()),
                instant(() -> intake.setIntakeAndTransition(
                        RobotConstants.intakeConstants.intakePower,
                        RobotConstants.intakeConstants.transitionPower))
        );
    }
    private Command autoRoutine() {
        return sequential(
                shootStationary(),
               intakeAndMove(),
                waitMs(500),
                instant(() -> intake.stop()),
                toNextShooterPosition(),
                shootStationary(),
                follow(follower, toNext4()),
                follow(follower, park())
        );
    }

    @Override
    public void init() {
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        shooter.periodic(); // Ivy does not run SolversLib subsystems, so apply the shooter setpoint here

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.addData("Current Shooter RPM", shooter.getRPM());
        telemetry.update();
    }
}