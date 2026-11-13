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
import org.firstinspires.ftc.teamcode.subsytems.Shooter;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;


@Autonomous(name = "ShootThenMoveBlue")
public class ShootThenMoveBlue extends OpMode {

    private Follower follower;
    private Intake intake;
    private Shooter shooter;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(82.5,129.5,270);

    private final Pose park = p.of(130.09,47.27,270);
    private final Pose parkControlPose = p.of(126.17,116.93,270);




    private Path park(){
        return curve(startPose,parkControlPose,park).linear(startPose,park);
    }


    private Command shootStationary(){
        return sequential(
                instant(() -> shooter.shoot(0)),
                waitMs(5000),
                instant(() ->intake.setTransitionOnly(1)),
                waitMs(1000),
                instant(() -> intake.setIntakeAndTransition(1,1)),
                waitMs(2000),
                instant(() -> shooter.stopWithoutIdle()),
                instant(() -> shooter.stopIdle(false)),
                instant(() -> intake.stop()));
    }

    private Command autoRoutine(){
        return sequential(
                shootStationary(),
                follow(follower,park())
        );

    }

    @Override
    public void init(){
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, telemetry);
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
        shooter.periodic();
        //getting poses and stuff
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.addData("Current Shooter RPM", shooter.getRPM());
        telemetry.update();

    }



}
