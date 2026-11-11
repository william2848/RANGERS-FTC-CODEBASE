package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsytems.Intake;
import org.firstinspires.ftc.teamcode.subsytems.RobotConstants;
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;


@Autonomous(name = "MainAuto2")
public class MainAuto2 extends OpMode {

    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(50,9,90);

    private final Pose park = p.of(10.24,117,270);
    private final Pose parkControlPose = p.of(26.31,34.22,90);

    private final Pose intakePose = p.of(7.91,8.39,180);
    private final Pose turn1 = p.of(50,9,180);
    private final Pose toNextPose1 = p.of(33.78,8.34,180);
    private final Pose toNextPose2 = p.of(35.11,122.61,180);
    private final Pose toNextPose3 = p.of(59.11,122.52,180);
    private final Pose turn2 = p.of(59.11,122.52,270);
    private final Pose toShootPose = p.of(59.11,132.48,270);
    private final Pose toNextPose4 = p.of(59.12,117.25,270);

    private Path toNext4(){
        return line(toShootPose,toNextPose4).linear(toShootPose,toNextPose4);
    }

    private Path toShoot(){
        return line(turn2,toShootPose).linear(turn2,toShootPose);
    }

    private Path turn2(){
        return line(toNextPose3,turn2).linear(toNextPose3,turn2);
    }


    private Path toNext3(){
        return line(toNextPose2,toNextPose3).linear(toNextPose2,toNextPose3);
    }

    private Path toNext2(){
        return line(toNextPose1,toNextPose2).linear(toNextPose1,toNextPose2);
    }

    private Path toNext(){
        return line(intakePose,toNextPose1).linear(intakePose,toNextPose1);
    }

    private Path turn1(){
        return line(startPose,turn1).linear(startPose,turn1);
    }
    private Path toIntake(){
        return line(turn1,intakePose).linear(turn1,intakePose);
    }

    private Path park(){
        return line(toNextPose4,park).linear(toNextPose4,park);
    }


    private Command shootStationary(){
        return sequential(
                instant(() -> shooter.shoot()),
                waitMs(3000),

                instant(() ->intake.setTransitionOnly(0.8)),
                waitMs(3000),
                instant(() -> shooter.stop()),
                instant(() -> intake.stop())

        );
    }

    private Command autoRoutine(){
        return sequential(
                shootStationary(),
                follow(follower,turn1()),
                parallel(
                follow(follower,toIntake()),
                instant(() -> intake.setIntakeAndTransition(RobotConstants.intakeConstants.intakePower,RobotConstants.intakeConstants.transitionPower))
                ),
                waitMs(500),
                instant(() -> intake.stop()),
               toNextShooterPosition(),
                shootStationary(),
                follow(follower,toNext4()),
                follow(follower,park())
        );

    }

    private Command toNextShooterPosition(){
                return sequential(
                follow(follower,toNext()),
                follow(follower,toNext2()),
                follow(follower,toNext3()),
                follow(follower,turn2()),
                follow(follower,toShoot()));
    }

    @Override
    public void init(){
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start(){
        schedule(autoRoutine());
    }

    @Override
    public void loop(){
        follower.update();
        Scheduler.execute();
        shooter.periodic(); // Ivy does not run SolversLib subsystems, so apply the shooter setpoint here
        //getting poses and stuff
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.addData("Current Shooter Velocity(t/s):", shooter.getVelocity());
        telemetry.update();

    }



}
